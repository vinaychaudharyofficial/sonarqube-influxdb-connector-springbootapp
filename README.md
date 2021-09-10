# sonarqube-influxdb-connector-springbootapp
Grafana leveraging SonarQube QoD metrices using Springboot connector application.

Def: Currently this SpringBoot Connector app calls SonarQube APIs for QoD metrics to get all data (specific to Branch name and application name) loads into influxDB. Grafana uses this data and presents into the graph.

::Below Sonar API are used::<br />
```
http://localhost:9000/api/measures/component 
```
(Metric Keys Used: new_coverage,new_uncovered_lines,new_reliability_rating,new_security_rating,new_maintainability_rating)
```
http://localhost:9000/api/issues/search
```
(Metirc Keys Used: CODE_SMELL,VULNERABILITY,BUG)
::Below InfluxDB URL::<br />
```
http://localhost:8086
```

-------------FutureScopse---------------<br /> 
Expose API of Connector app which subscribes SonarQube  QoD matrices.

