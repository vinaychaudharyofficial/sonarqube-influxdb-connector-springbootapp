package com.demo.connector.service.sonarqube.dto;

import java.util.List;

public class SonarQubeResponse {

	private List<Issue> issues;

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	private Component component;

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}
}
