package com.code4copy.websiteaccess.client.mapper;

import com.code4copy.websiteaccess.client.dao.AccessLogDao;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface AccessLogMapper {
    @DaoFactory
    AccessLogDao productDao(@DaoKeyspace CqlIdentifier keyspace);
}
