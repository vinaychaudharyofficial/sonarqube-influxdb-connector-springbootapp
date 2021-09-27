package com.demo.connector.influxdb;

import com.demo.connector.influxdb.entity.ApplicationEntity;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class InfluxDaoImpl implements InfluxDao{
	
	Logger logger = LoggerFactory.getLogger(InfluxDaoImpl.class);

	@Value("${spring.influx.url}")
	private String influxdbURL;
	@Value("${spring.influx.retentionPolicy}")
	private String retentionPolicy;
	@Value("${spring.influx.user}")
	private String username;
	@Value("${spring.influx.password}")
	private String userpassword;
	@Value("${spring.influx.database.sonar}")
	private String dbName;
	
	@Autowired
	private InfluxDB influxDB;
	
	@Bean
	public InfluxDB getInfluxDBConnection() {
		influxDB = InfluxDBFactory.connect(influxdbURL, username, userpassword);
		influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
		influxDB.createRetentionPolicy(retentionPolicy, dbName, "30d", 1, true);
		influxDB.setDatabase(dbName);
		influxDB.setRetentionPolicy(retentionPolicy);
		return influxDB;
	}
	
	@Override
	public void verifyConnection() {
		Pong response = this.influxDB.ping();
		if (response.getVersion().equalsIgnoreCase("unknown")) {
			logger.error("Error in InfluxDB connection.");
		    
		} 
		else {
			logger.info("InfluxDB connection successful.");
		}
	}

	@Override
	public void addPointToBatch(BatchPoints batchPoints, Point point) {
		batchPoints.point(point);
	}

	
	public BatchPoints getBatchPoint() {
		return BatchPoints.database(dbName)
				  .retentionPolicy(retentionPolicy)
				  .build();
	}

	@Override
	public void saveBatchPoints(BatchPoints batchPoints) {
		influxDB.write(batchPoints);
		
	}
	@Override
	public void savePoints(Point point) {
		influxDB.write(point);
		
	}

	@Override
	public QueryResult executeQuery(String query) {
		Query query1 = new Query(query,dbName);
		return influxDB.query(query1);

	}

	@Override
	public void close() {
		influxDB.close();
		
	}
	
	
}
