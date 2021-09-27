package com.demo.connector.influxdb;

import com.demo.connector.influxdb.entity.ApplicationEntity;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;


public interface InfluxDao {

	public void verifyConnection();
	public BatchPoints getBatchPoint();
	public void addPointToBatch(BatchPoints batchPoints, Point point);
	public void saveBatchPoints(BatchPoints batchPoints);
	void savePoints(Point point);
	QueryResult executeQuery(String query);
	void close();
}
