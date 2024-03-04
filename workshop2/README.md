# Workshop 2 - Kakfa Schema Registry, Streams, Connect

In this workshop we will start creating a more complex Kafka system. This task will run over this and the next workshop, and you will gradually expand your code and system as you learn.

## Requirements

You should have completed the code assignment for workshop 1 found [here](../workshop1/2%20-%20Code/README.md), or at least have some experience with creating Kafka producers and consumers.

## Overview:
In this workshop you will integrate streamed data from the Wikimedia RecentChanges [stream](stream.wikimedia.org/v2/stream/recentchange) into Apache Kafka. Then you will create a consumer that read the data and can provide some information based on the messages.

Later you will build on this system and use different kafka systems to expand to a larger kafka system.

## Objective:
The primary objective of this task is to demonstrate the end-to-end flow of streaming data from an external source (Wikimedia RecentChanges) to Kafka, and subsequently using this to gain some insight to the data.

## Steps

### Step 1. Connect to Wikimedia stream.

- Create a new kafka topic to store incoming messages from the Wikimedia stream. Create a new Kafka producer that connect to the stream, parse the JSON and produce text message.

### Step 2. Schema registry.
- Go to folder 2 - Schema Registry and follow the instructions there.

- After you finish the tasks in 2 - Schema Registry, you should have a producer and consumer that uses the schema registry to produce and consume messages.
  - Use what you have learned here to create a class that matches the Wikimedia json and create and save the json as a schema in your schema registry.
  - Change your Wikimedia producer and consumer to produce and consume messages with your schema.

.net
```csproj
<PackageReference Include="Confluent.SchemaRegistry" Version="2.3.0" />
<PackageReference Include="Confluent.SchemaRegistry.Serdes.Json" Version="2.3.0" />
```

<details>
<summary> Schema Registry </summary>

### Config

```yml
  schema-registry:
    container_name: schema-registry
    image: bitnami/schema-registry
    ports:
      - "8081:8081"
    depends_on:
      - kafka-0
    environment:
      - SCHEMA_REGISTRY_KAFKA_BROKERS=PLAINTEXT://kafka-0:9092
      - SCHEMA_REGISTRY_HOST_NAME=schema-registry
      - SCHEMA_REGISTRY_LISTENERS=http://schema-registry:8081
    volumes:
      - schema_data:/bitnami/schema
```

</details>

### Step 3. Kafka Connect

#### Step 3.1 - Setup Source Connectors
- Take down you kafka producer and consumer for wikimedia and use Kafka Connect to stream the data to a new topic.
- To do this go to the Kafka Connect UI which you can find at 'localhost:8000', and create a new connector of type WikimediaSourceConnector and with the configuration that you can find [here](/workshop2/2%20-%20Connect/connectorSrcs/properties/wikimedia-source.properties).
- Create the task and check that the data is being streamed to the topic you defined in your configuration (e.g. `wikimedia-events`)
  - Also check the logs of the kafka connect container to see the task is running correctly.
- Create more tasks with different configurations to stream the data to different topics, and experiment with different types of formats for the messages.

#### Step 3.2 - Setup Sink Connectors
- Now do the same for the wikimedia consumer, but use Kafka Connect to consume the data from the topic you defined in the previous step and stream it to opensearch.
- To do this go to the Kafka Connect UI and create a new connector of type OpensearchSinkConnector and with the configuration that you can find [here](/workshop2/2%20-%20Connect/connectorSrcs/properties/opensearch-sink.properties).
- Create the task and check that the data is being streamed to opensearch. You can access opensearch at `localhost:9200` and check that the data is being streamed correctly.
  - Also check the logs of the kafka connect container to see the task is running correctly.
- Create more tasks with different configurations to stream the data to different sinks, and experiment with different types of formats for the messages.
  - Also try using the JDBC sink connector to stream the data to a database. You can use the configuration that you can find [here](/workshop2/2%20-%20Connect/connectorSrcs/properties/postgres-sink.properties).


### Step 4. Kafka Streams

- Take down your kafka service and implement a Kafka Stream to add the Wikimedia streamed data to your kafka service.

