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
- Go to folder 3 - Connect and follow the instructions there.


### Step 4. Kafka Rest Proxy
- Go to folder 4 - Rest Proxy and follow the instructions there.

