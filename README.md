+++
categories = ["recipes"]
date = "2016-03-29T11:19:10-04:00"
glyph = "fa-file-text-o"
summary = "Simple and Detailed Monitoring of MySQL"
tags = ["MySQL","monitoring"]
title = "Monitoring MySQL"

+++

# Monitoring interface for MySQL with PCF

# Foreword
This document details a strategy for ensuring a MySQL cluster in _cloud foundry_ is running properly.
  - To use the RESTful interface for monitoring a PCF MySQL installation, you must download and install the PCF MySQL Admin API Proxy.
  - One instance of the Admin API will monitor one MySQL Cluster.


# Overview
This recipe covers two steps for monitoring and discovering the state of your PCF MySQL installation. The first step is a simple "is it running" API which simply PINGs the MySQL Cluster to verify it is responding. The second section details more complex monitoring which can be used for performance analysis, scaling and statistical analysis.

# Deploy the admin API
Download the application jar file and the manifest.

  * Using the cf CLI, perform the following:
    * cf push mysql-api -f manifest.yml -p target/mysql-monitor-api-0.0.2-SNAPSHOT.jar
    * cf bind-service mysql-api mySqlServiceName

Your API will be reachable at https://mysql-api.your.cfinstallation.com

# Is my MySQL Cluster Running

The simplest approach to verifying a PCF MySQL cluster is _happy_ is to execute the ping API

| HTTP Method | API Path | Description |
|----|----|----|
| GET | /admin/ping |Intended for use by monitoring tools. If everything is working correctly, will return HTTP status 200 with a JSON response similar to:  **{"resultCode": "200","resultMessage": "Connection Successful","resultData": {"Ping": "PONG"}}**|

# Detailed Performance Monitoring

The MySQL Admin APIs expose many useful features for performance tuning and application monitoring. The sections available to monitor are:

  * Server -- Information about the Server including version, os and uptime.
  * Clients -- The client connections. Useful for finding (and killing) zombie or harmful client connections.
  * Memory -- Memory information including currently used and peak utilization
  * User -- Event summary by user
  * History -- Historical data including total connections, operations per second and other info by digest.


| HTTP Method | API Path | Description |
|----|----|----|
| GET | /admin/ping | Sends a PING request to the MySQL Cluster. {"resultCode":"200","resultMessage":"Connection Successful","resultData":{"Ping":"PONG"}} |
| GET | /admin/info | A complete list of all sections above |
| GET | /admin/info/{section}| The details about the section of data. The section name is case sensitive, use the details above. |


# API Output
##. API Result from /admin/ping

```javascript
{
  "resultCode": "200",
  "resultMessage": "Connection Successful",
  "resultData": {
    "Ping": "PONG"
  }
}
```

## API Result from /admin/info/History

```javascript
{  
   "resultCode":"200",
   "resultMessage":"Connection Successful",
   "resultData":{  
      "Server":{  
         "SCHEMA_NAME":"customers",
         "DIGEST_TEXT":"SELECT @ @ max_allowed_packet",
         "COUNT_STAR":"36254",
         "SUM_TIMER_WAIT":"2683789829000",
         "MIN_TIMER_WAIT":"45079000",
         "AVG_TIMER_WAIT":"74027000",
         "MAX_TIMER_WAIT":"1445326000",
         "SUM_LOCK_TIME":"0",
         "SUM_ERRORS":"0",
         "SUM_WARNINGS":"0",
         "SUM_ROWS_AFFECTED":"0",
         "SUM_ROWS_SENT":"36254",
         "SUM_ROWS_EXAMINED":"0",
         "SUM_CREATED_TMP_DISK_TABLES":"0",
         "SUM_CREATED_TMP_TABLES":"0",
         "SUM_SELECT_FULL_JOIN":"0",
         "SUM_SELECT_FULL_RANGE_JOIN":"0",
         "SUM_SELECT_RANGE":"0",
         "SUM_SELECT_RANGE_CHECK":"0",
         "SUM_SELECT_SCAN":"0",
         "SUM_SORT_MERGE_PASSES":"0",
         "SUM_SORT_RANGE":"0",
         "SUM_SORT_ROWS":"0",
         "SUM_SORT_SCAN":"0",
         "SUM_NO_INDEX_USED":"0",
         "SUM_NO_GOOD_INDEX_USED":"0",
         "FIRST_SEEN":"2014-09-12 16:04:38",
         "LAST_SEEN":"2014-10-31 08:26:07"
      }
   }
}
```
