package com.code4copy.websiteaccess.client.service.imp;

import com.code4copy.websiteaccess.client.service.api.AccessLogSourceService;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileAccessLogSourceServiceImpl implements AccessLogSourceService {
    private final String logFolder;

    public FileAccessLogSourceServiceImpl(final String folder) {
        this.logFolder = folder;
    }

    @CheckForNull
    @Override
    public List<Path> getFiles() throws IOException {
        if (Files.exists(Paths.get(this.logFolder))) {
            return Files.list(Paths.get(this.logFolder)).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
