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

### Install and SetUP InfluxDB <br/>
* Download InfluxDB from https://portal.influxdata.com/downloads/ as below
```
wget https://dl.influxdata.com/influxdb/releases/influxdb-1.8.9_windows_amd64.zip -UseBasicParsing -OutFile influxdb-1.8.9_windows_amd64.zip Expand-Archive .\influxdb-1.8.9_windows_amd64.zip -DestinationPath 'C:\Program Files\InfluxData\influxdb\'
```
* Unizp influxdb archive into any location in your drive.
* Create meta,data,wal directories under your unziped folder of your influxDB as done in above step.
* Under your unzipped influxdb directory, Edit the file "nfluxdb.conf" and add the location of directories created in the previous step (meta/data/wal) for the similar respective sections.
* Run the following command to install influxDB as a window service, After completion we would see message as “service influx installed successfully”,
```
nssm.exe install influx influxd.ext
```
* Now run below command to up the influx database.
```
influx
```

### Install and SetUP Grafana Dashboard <br/>
* Download Grafana installer and click to install.
```
https://grafana.com/grafana/download?platform=windows
```


#TODO Items : MockUps and Diagram for GrafanDashboard, Process Flow, Some InfluxDB Screenshots.<br/>
-------------FutureScopse---------------<br /> 
Expose API of Connector app which subscribes SonarQube  QoD matrices.
