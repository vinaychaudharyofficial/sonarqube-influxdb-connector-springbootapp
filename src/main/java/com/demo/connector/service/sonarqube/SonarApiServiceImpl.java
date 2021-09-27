package com.demo.connector.service.sonarqube;

import com.demo.connector.influxdb.entity.ApplicationEntity;
import com.demo.connector.influxdb.entity.BranchEntity;
import com.demo.connector.service.influxdb.InfluxDBService;
import com.demo.connector.service.sonarqube.dto.Issue;
import com.demo.connector.service.sonarqube.dto.Measure;
import com.demo.connector.service.sonarqube.dto.SonarQubeResponse;
import io.micrometer.core.instrument.util.StringUtils;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.connector.influxdb.InfluxDao;
import com.demo.connector.utility.SonarConstant;

import java.util.List;

@Service
public class SonarApiServiceImpl implements SonarApiService {
	Logger logger = LoggerFactory.getLogger(SonarApiServiceImpl.class);

	@Autowired
	private InfluxDao influxdao;
	@Autowired
	private InfluxDBService influxDBService;

	@Value("${connector.sonar.api.endpoint}")
	private String sonarSearchAPI;
	@Value("${connector.sonar.api.search.types}")
	private String sonarSearchApiTypes;
	@Value("${connector.sonar.api.search.statuses}")
	private String sonarSearchApiStatuses;
	@Value("${connector.sonar.api.endpoint.authToken}")
	private String sonarSearchApiToken;


	@Value("${connector.sonar.api.measurement.component.endpoint}")
	private String sonarMeasurementAPI;
	@Value("${connector.sonar.api.measurement.component.metricKeys}")
	private String sonarMeasurementAPIMetrics;

	@Override
	public void getSonarMeasurementBasedOnProject() {

		RestTemplate restTemplate = new RestTemplate();


		for (ApplicationEntity appEntity: influxDBService.getAppEntity()) {
				UriComponentsBuilder builder = UriComponentsBuilder
						.fromUriString(sonarMeasurementAPI)
						//Add query parameter
						.queryParam(SonarConstant.SonarMeasurementAPIParam_Component, appEntity.getAppName())
						.queryParam(SonarConstant.SonarAPIQueryParam_Branch, appEntity.getBranchName())
						.queryParam(SonarConstant.SonarAPIQueryParam_AdditionalFields, "metrics")
						.queryParam(SonarConstant.SonarMeasurementAPIParam_MetricKeys, sonarMeasurementAPIMetrics);

				logger.info("url : " + builder.toUriString());
				ResponseEntity<SonarQubeResponse> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHttpEntityWithHeaders(), SonarQubeResponse.class);
				logger.info("Sonar API Response code: " + responseEntity.getStatusCode());

				//Save API Response to Influx DB
				if (responseEntity.getStatusCode() == HttpStatus.ACCEPTED || responseEntity.getStatusCode() == HttpStatus.OK) {
					Point.Builder builder1 = Point.measurement(SonarConstant.SonarMeasurementAPI_Measurement)
							.addField(SonarConstant.SonarAPIResponse_Project, appEntity.getProjectName())
							.addField(SonarConstant.SonarAPIResponse_Application, responseEntity.getBody().getComponent().getKey())
							.addField(SonarConstant.SonarAPIResponse_Branch, StringUtils.isBlank(responseEntity.getBody().getComponent().getBranch())?
									appEntity.getBranchName():responseEntity.getBody().getComponent().getBranch());
					for (Measure measure : responseEntity.getBody().getComponent().getMeasures()) {
						builder1.addField(measure.getMetric(), measure.getPeriod().getValue());
					}
					Point point = builder1.build();
					influxdao.savePoints(point);
				}
		}
		influxdao.close();
	}


	@Override
	public void getSonarIssuesBasedOnProject() {

		RestTemplate restTemplate = new RestTemplate();

		for (ApplicationEntity appEntity : influxDBService.getAppEntity()) {
				UriComponentsBuilder builder = UriComponentsBuilder
						.fromUriString(sonarSearchAPI)
						//Add query parameter
						.queryParam(SonarConstant.SonarAPIQueryParam_ComponentKeys, appEntity.getAppName())
						.queryParam(SonarConstant.SonarAPIQueryParam_Branch,  appEntity.getBranchName())
						.queryParam(SonarConstant.SonarAPIQueryParam_AdditionalFields, "_all")
						.queryParam(SonarConstant.SonarAPIQueryParam_Types, sonarSearchApiTypes)
						.queryParam(SonarConstant.SonarAPIQueryParam_Statuses, sonarSearchApiStatuses);

				logger.info("url : " + builder.toUriString());
				ResponseEntity<SonarQubeResponse> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHttpEntityWithHeaders(), SonarQubeResponse.class);
				logger.info("Sonar API Response code: " + responseEntity.getStatusCode());
				//Save API Response to Influx DB
				if (responseEntity.getStatusCode() == HttpStatus.ACCEPTED || responseEntity.getStatusCode() == HttpStatus.OK) {
					int i = 0;
					for (Issue issue : responseEntity.getBody().getIssues()) {
						i += 10;
						Point point = Point.measurement(issue.getType())
								.addField(SonarConstant.SonarAPIResponse_Project, appEntity.getProjectName())
								.addField(SonarConstant.SonarAPIResponse_Application, issue.getProject())
								.addField(SonarConstant.SonarAPIResponse_Branch,  appEntity.getBranchName())
								.addField(SonarConstant.SonarAPIResponse_Component, issue.getComponent())
								.addField(SonarConstant.SonarAPIResponse_Line, StringUtils.isBlank(issue.getLine()) ? "" : issue.getLine())
								.addField(SonarConstant.SonarAPIResponse_Severity, issue.getSeverity())
								.addField(SonarConstant.SonarAPIResponse_UpdateDate, issue.getUpdateDate())
								.addField(SonarConstant.SonarAPIResponse_Author, issue.getAuthor())
								.addField(SonarConstant.SonarAPIResponse_Message, issue.getMessage())
								.build();
						influxdao.savePoints(point);
					}
				}
		}
		influxdao.close();
	}

	private  HttpEntity getHttpEntityWithHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.getType());
		headers.set(HttpHeaders.AUTHORIZATION, sonarSearchApiToken);
		return new HttpEntity<>(headers);
	}
}
