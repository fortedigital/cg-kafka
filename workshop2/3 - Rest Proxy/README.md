# Kafka Connect REST API & Kafka REST Proxy Usage Guide

This guide provides examples of using Kafka REST Proxy for managing connectors.

## Prerequisites

Install jq to pretty print json

Linux:
```bash
sudo apt-get install jq
```
mac:
```bash
brew install jq
```
windows:
```bash
choco install jq
```
# Kafka Connect REST API


## 1. Get Worker Information
```bash
curl -s 127.0.0.1:8083/ | jq
```

## 2. List Connectors Available on a Worker
```bash
curl -s 127.0.0.1:8083/connector-plugins | jq
```

## 3. Check Active Connectors
```bash
curl -s 127.0.0.1:8083/connectors | jq
```

## 4. Get Connector Tasks and Config Information
```bash
curl -s 127.0.0.1:8083/connectors/WikimediaSourceConnector/tasks | jq
```

## 5. Get Connector Status
```bash
curl -s 127.0.0.1:8083/connectors/WikimediaSourceConnector/status | jq
```

## 6. Pause / Resume a Connector
```bash
# Pause
curl -s -X PUT 127.0.0.1:8083/connectors/WikimediaSourceConnector/pause

# Resume
curl -s -X PUT 127.0.0.1:8083/connectors/WikimediaSourceConnector/resume
```

## 7. Get Connector Configuration
```bash
curl -s 127.0.0.1:8083/connectors/WikimediaSourceConnector | jq
```

## 8. Delete a Connector
```bash
curl -s -X DELETE 127.0.0.1:8083/connectors/WikimediaSourceConnector
```

## 9. Create a New Connector
```bash
curl -s -X POST -H "Content-Type: application/json" --data '{"name": "file-stream-demo-distributed", "config":{"connector.class":"org.apache.kafka.connect.file.FileStreamSourceConnector","key.converter.schemas.enable":"true","file":"demo-file.txt","tasks.max":"1","value.converter.schemas.enable":"true","name":"file-stream-demo-distributed","topic":"demo-2-distributed","value.converter":"org.apache.kafka.connect.json.JsonConverter","key.converter":"org.apache.kafka.connect.json.JsonConverter"}}' http://127.0.0.1:8083/connectors | jq
```

## 10. Update Connector Configuration
```bash
curl -s -X PUT -H "Content-Type: application/json" --data '{"connector.class":"org.apache.kafka.connect.file.FileStreamSourceConnector","key.converter.schemas.enable":"true","file":"demo-file.txt","tasks.max":"2","value.converter.schemas.enable":"true","name":"file-stream-demo-distributed","topic":"demo-2-distributed","value.converter":"org.apache.kafka.connect.json.JsonConverter","key.converter":"org.apache.kafka.connect.json.JsonConverter"}' 127.0.0.1:8083/connectors/file-stream-demo-distributed/config | jq
```

---

# Kafka REST Proxy

Go to the [Kafka REST Proxy](https://docs.confluent.io/platform/current/kafka-rest/api.html#crest-api-v2) documentation for more information.
Here you will find all the documentation you need to call the Kafka REST Proxy API.
Access the Kafka REST Proxy API by using the following URL: `http://localhost:8082/topics` to get started



Feel free to customize these commands as per your environment and requirements.
