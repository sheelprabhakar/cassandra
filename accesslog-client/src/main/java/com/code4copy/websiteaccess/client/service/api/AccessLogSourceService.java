package com.code4copy.websiteaccess.client.service.api;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface AccessLogSourceService {
    @CheckForNull
    List<Path> getFiles() throws IOException;
}
