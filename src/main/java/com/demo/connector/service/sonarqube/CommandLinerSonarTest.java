package com.demo.connector.service.sonarqube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.demo.connector.influxdb.InfluxDao;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Component
public class CommandLinerSonarTest implements CommandLineRunner{

	@Autowired
	private SonarApiService sonarApiService;
	@Autowired
	private InfluxDao influxdb;

	@Override
	public void run(String... args) throws Exception {
		influxdb.verifyConnection();
		sonarApiService.getSonarIssuesBasedOnProject();
		sonarApiService.getSonarMeasurementBasedOnProject();
	}
}
