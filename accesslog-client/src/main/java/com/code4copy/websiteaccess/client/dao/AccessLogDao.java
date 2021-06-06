package com.code4copy.websiteaccess.client.dao;

import com.code4copy.websiteaccess.client.entity.AccessLog;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;

import java.util.UUID;

@Dao
public interface AccessLogDao {
    @Select
    AccessLog findById(UUID id);

    @Insert
    void save(AccessLog log);

    @Delete
    void delete(AccessLog log);
}
