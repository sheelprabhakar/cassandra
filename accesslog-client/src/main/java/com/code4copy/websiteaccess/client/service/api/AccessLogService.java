package com.code4copy.websiteaccess.client.service.api;

import com.code4copy.websiteaccess.client.entity.AccessLog;

import java.io.Closeable;

public interface AccessLogService extends Closeable {
    void save(AccessLog log);
}
