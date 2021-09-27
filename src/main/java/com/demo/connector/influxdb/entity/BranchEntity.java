package com.demo.connector.influxdb.entity;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "PROJECT_APP_BRANCH")
public class BranchEntity {
    @Column(name = "time")
    private Instant time;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "BRANCH_VALUE")
    private String branchValue;

    @Column(name = "APP_NAME")
    private String appName;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchValue() {
        return branchValue;
    }

    public void setBranchValue(String branchValue) {
        this.branchValue = branchValue;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
