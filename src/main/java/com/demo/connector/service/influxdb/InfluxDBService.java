package com.demo.connector.service.influxdb;

import com.demo.connector.influxdb.entity.ApplicationEntity;
import com.demo.connector.influxdb.entity.BranchEntity;

import java.util.List;

public interface InfluxDBService {
    public List<ApplicationEntity> getAppEntity();
    public List<BranchEntity> getBranchEntity(String appName);

}
