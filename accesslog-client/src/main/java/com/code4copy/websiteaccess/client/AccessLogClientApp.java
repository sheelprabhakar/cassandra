package com.code4copy.websiteaccess.client;

import com.code4copy.websiteaccess.client.service.api.AccessLogProcessorService;
import com.code4copy.websiteaccess.client.service.api.AccessLogService;
import com.code4copy.websiteaccess.client.service.api.AccessLogSourceService;
import com.code4copy.websiteaccess.client.service.imp.AccessLogCassandraServiceImpl;
import com.code4copy.websiteaccess.client.service.imp.FileAccessLogProcessorServiceImpl;
import com.code4copy.websiteaccess.client.service.imp.FileAccessLogSourceServiceImpl;

import java.io.IOException;

public class AccessLogClientApp {

    public static void main(String[] args) throws IOException {
        if(args.length == 2) {
            String folderPath = args[0];
            int noOfThreads = Integer.parseInt(args[1]);
            AccessLogSourceService accessLogSourceService = new FileAccessLogSourceServiceImpl(folderPath);
            AccessLogService accessLogService = new AccessLogCassandraServiceImpl();
            AccessLogProcessorService accessLogParserService =
                    new FileAccessLogProcessorServiceImpl(accessLogService, accessLogSourceService.getFiles(), noOfThreads);
            accessLogParserService.process();
            accessLogService.close();
        }
    }
}
