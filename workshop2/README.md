# Workshop 2 - Kakfa Schema Registry, Streams, Connect

In this workshop we will start creating a more complex Kafka system. This task will run over this and the next workshop, and you will gradually expand your code and system as you learn.

## Requirements

You should have completed the code assignment for workshop 1 found [here](../workshop1/2%20-%20Code/README.md), or at least have some experience with creating Kafka producers and consumers.

## Overview:
In this workshop you will integrate streamed data from the Wikimedia RecentChanges [stream](stream.wikimedia.org/v2/stream/recentchange) into Apache Kafka. Then we 

## Objective:
The primary objective of this task is to demonstrate the end-to-end flow of streaming data from an external source (Wikimedia RecentChanges) to Kafka, and subsequently indexing this data into OpenSearch for search and analysis purposes.

## Steps

### Step 1. Connect to Wikimedia stream.

- Create a new kafka topic to store incoming messages from the Wikimedia stream. Create a new Kafka producer that connect to the stream, parse the JSON and produce text message.

- Create a new kafka consumer that consumes the messages, parse the text as json and visualize the different types of messages.

### Step 2. Schema registry.

- Take down the kafka service you are running, and add a schema registry to the docker compose file.

- Add the schema registry to the UI.

- Create a class that matches the Wikimedia json and create and save the json as a schema in your schema registry.

- Change your producer to produce messages with your schema. And change the consumer to use the schema.

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

### Step 3. Kafka Streams

- Take down your kafka service and implement a Kafka Stream to add the Wikimedia streamed data to your kafka service.

### Step 4. Kafka Connect

- Implement a Kafka connect service.