# Kafka Topics Commands Steps

This README outlines the step-by-step process for managing Kafka topics using the `kafka-topics.sh` script. Make sure to replace "kafka-topics.sh" with "kafka-topics" or "kafka-topics.bat" based on your system.

## Step 1: Connect to Kafka Container

```bash
docker exec -it kafka0 sh
```

This command connects to the Kafka container (`kafka0`) using Docker.

## Step 2: List all Topics on cluster 

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --list
```

Lists all topics in the Kafka cluster with the specified bootstrap server (`localhost:9092`).

## Step 3: Create a Topic

### Substep 1: Basic Topic Creation

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create
```

Creates a Kafka topic named `first_topic` with default configurations.

### Substep 2: Topic Creation with Partitions

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --create --partitions 3
```

Creates a Kafka topic named `second_topic` with 3 partitions.

### Substep 3: Topic Creation with Partitions and Replication Factor

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3 --replication-factor 2
```

Creates a Kafka topic named `third_topic` with 3 partitions and a replication factor of 2.

### Substep 4: Topic Creation with Specific Replication Factor

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3 --replication-factor 1
```

Creates a Kafka topic named `third_topic` with 3 partitions and a specific replication factor of 1.

## Step 4: List All Topics Again

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --list
```

Lists all topics in the Kafka cluster after the topic creation operations.

## Step 5: Describe a Topic

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe
```

Describes the configuration and details of the `first_topic`.

## Step 6: Delete a Topic

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --delete
```

Deletes the Kafka topic named `first_topic`. Note that this command works only if `delete.topic.enable=true` in the Kafka configuration.

Ensure that you replace "kafka-topics.sh" with the appropriate script for your system (`kafka-topics` or `kafka-topics.bat`).
