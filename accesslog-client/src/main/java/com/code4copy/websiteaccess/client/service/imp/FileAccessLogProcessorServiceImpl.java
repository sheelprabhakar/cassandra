package com.code4copy.websiteaccess.client.service.imp;

import com.code4copy.websiteaccess.client.entity.AccessLog;
import com.code4copy.websiteaccess.client.service.api.AccessLogProcessorService;
import com.code4copy.websiteaccess.client.service.api.AccessLogService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class FileAccessLogProcessorServiceImpl implements AccessLogProcessorService {
    private final ExecutorService executorService;
    private final List<Path> files;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss");

    public FileAccessLogProcessorServiceImpl( List<Path> files, int noOfThreads) {
        this.files = files;
        this.executorService = Executors.newFixedThreadPool(noOfThreads);
    }

    @Override
    public void process() {

        Collection<Future<?>> futures = new LinkedList<>();
        for (Path file : this.files) {
            ProcessTask processTask = new ProcessTask(file, new AccessLogCassandraServiceImpl());
            futures.add(this.executorService.submit(processTask));
        }
        int i =0;
        for (Future<?> future : futures) {
            try {
                future.get();
                System.out.println("file complete "+ i++);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        this.executorService.shutdownNow();
    }

    class ProcessTask implements Callable<Boolean> {
        final Path file;
        final AccessLogService accessLogService;

        ProcessTask(final Path file, final AccessLogService accessLogService) {
            this.file = file;
            this.accessLogService = accessLogService;
        }

        @Override
        public Boolean call() throws Exception {
            File file = this.file.toFile();
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    AccessLog log = new AccessLog();
                    String[] parts = line.split(" - - \\[");
                    if (parts.length == 2) {
                        log.setIp(parts[0]);
                    } else {
                        this.logError(line);
                        continue;
                    }

                    parts = parts[1].split(" \\+0000\\] \"");
                    if (parts.length == 2) {
                        LocalDateTime ldt = LocalDateTime.parse(parts[0], dateTimeFormatter);
                        log.setTime(ldt.toInstant(ZoneOffset.UTC));
                    } else {
                        this.logError(line);
                        continue;
                    }
                    // Parse method
                    parts = parts[1].split(" \\/");
                    if (parts.length == 2) {
                        log.setMethod(parts[0]);
                    } else {
                        this.logError(line);
                        continue;
                    }
                    parts = parts[1].split("\"" );
                    if (parts.length == 5) {
                        log.setUrl("/"+parts[0]);
                        log.setReferral(parts[2]);
                        log.setClient(parts[4]);
                    } else {
                        this.logError(line);
                        continue;
                    }
                    parts = parts[1].trim().split(" ");
                    if(parts.length == 2) {
                        log.setStatus(Integer.parseInt(parts[0]));
                        log.setLength(Long.parseLong(parts[1]));
                    }else{
                        this.logError(line);
                        continue;
                    }
                    this.accessLogService.save(log);
                }

            } finally {
                it.close();
                this.accessLogService.close();
            }
            return true;
        }

        private void logError(String line) {
            System.out.println("Unable to parse line");
            System.out.println(line);
        }
    }
}
