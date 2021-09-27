package com.demo.connector.influxdb.entity;


import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "PROJECT_APP_BRANCH")
public class ApplicationEntity {

    @Column(name = "time")
    private Instant time;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "APP_NAME")
    private String appName;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
