package com.demo.connector.service.influxdb;

import com.demo.connector.influxdb.InfluxDao;
import com.demo.connector.influxdb.entity.ApplicationEntity;
import com.demo.connector.influxdb.entity.BranchEntity;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfluxDBServiceImpl implements  InfluxDBService{

    @Autowired
    private InfluxDao influxDao;

    private static final String appQuery = "SELECT * FROM PROJECT_APP_BRANCH";
    private static final String appBranchQuery = "SELECT * FROM PROJECT_APP_BRANCH WHERE \"APP_NAME\" = '";


    @Override
    public List<ApplicationEntity> getAppEntity() {

        QueryResult queryResult = influxDao.executeQuery(appQuery);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<ApplicationEntity> applicationEntities = resultMapper
                .toPOJO(queryResult, ApplicationEntity.class);

        return applicationEntities;
    }

    @Override
    public List<BranchEntity> getBranchEntity(String appName) {
       /* QueryResult queryResult = influxDao.executeQuery(appBranchQuery+appName+"'");
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<BranchEntity> branchEntities = resultMapper
                .toPOJO(queryResult, BranchEntity.class);

        return branchEntities;*/
        return null;
    }
}
