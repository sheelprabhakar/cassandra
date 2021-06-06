package com.code4copy.websiteaccess.client.service.imp;

import com.code4copy.websiteaccess.client.dao.AccessLogDao;
import com.code4copy.websiteaccess.client.entity.AccessLog;
import com.code4copy.websiteaccess.client.mapper.AccessLogMapper;
import com.code4copy.websiteaccess.client.mapper.AccessLogMapperBuilder;
import com.code4copy.websiteaccess.client.service.api.AccessLogService;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;

import java.io.IOException;

public class AccessLogCassandraServiceImpl implements AccessLogService {
    private final CqlSession session = CqlSession.builder().build();
    private final AccessLogMapper accessLogMapper = new AccessLogMapperBuilder(session).build();
    private final AccessLogDao dao = accessLogMapper.accessLogDao(CqlIdentifier.fromCql("accesslogdb"));

    public AccessLogCassandraServiceImpl() {
    }

    @Override
    public void save(AccessLog log){
        this.dao.save(log);
    }

    @Override
    public void close() throws IOException {
        this.session.close();
    }
}
