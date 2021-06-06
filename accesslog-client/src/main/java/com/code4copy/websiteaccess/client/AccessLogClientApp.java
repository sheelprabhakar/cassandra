package com.code4copy.websiteaccess.client;

import com.code4copy.websiteaccess.client.service.api.AccessLogProcessorService;
import com.code4copy.websiteaccess.client.service.api.AccessLogSourceService;
import com.code4copy.websiteaccess.client.service.imp.FileAccessLogProcessorServiceImpl;
import com.code4copy.websiteaccess.client.service.imp.FileAccessLogSourceServiceImpl;

import java.io.IOException;

public class AccessLogClientApp {

    public static void main(String[] args) throws IOException {
        if(args.length == 2) {
            String folderPath = args[0];
            int noOfThreads = Integer.parseInt(args[1]);
            AccessLogSourceService accessLogSourceService = new FileAccessLogSourceServiceImpl(folderPath);

            AccessLogProcessorService accessLogParserService =
                    new FileAccessLogProcessorServiceImpl(accessLogSourceService.getFiles(), noOfThreads);
            accessLogParserService.process();
        }
    }
}
