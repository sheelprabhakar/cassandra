package com.code4copy.websiteaccess.client.entity;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import java.time.Instant;
import java.util.UUID;

@Entity
public class AccessLog {
    @PartitionKey
    private UUID id;

    private String ip;
    private Instant time;
    private String method;
    private String url;
    private int status;
    private long length;
    private String referral;
    private String client;

    public AccessLog() {
        this.id = UUID.randomUUID();
    }

    public AccessLog(UUID id, String ip, Instant time, String method, String url, int status, long length, String referral, String client) {
        this.id = id;
        this.ip = ip;
        this.time = time;
        this.method = method;
        this.url = url;
        this.status = status;
        this.length = length;
        this.referral = referral;
        this.client = client;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
