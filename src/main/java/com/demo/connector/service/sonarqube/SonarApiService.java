package com.demo.connector.service.sonarqube;

public interface SonarApiService {
	
	public void getSonarIssuesBasedOnProject();
	public void getSonarMeasurementBasedOnProject();

}
