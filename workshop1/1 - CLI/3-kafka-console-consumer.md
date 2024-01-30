# Kafka Console Consumer Steps

This README provides a step-by-step guide for using the `kafka-console-consumer.sh` script to consume messages from Kafka topics. Replace "kafka-console-consumer.sh" with "kafka-console-consumer" or "kafka-console-consumer.bat" based on your system.

## Step 1: Create a Topic with 3 Partitions

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --create --partitions 3
```

Creates a Kafka topic named `second_topic` with 3 partitions.

## Step 2: Consume Messages

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic second_topic
```

Starts consuming messages from the `second_topic`.

## Step 3: Produce Messages in Another Terminal

```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --producer-property partitioner.class=org.apache.kafka.clients.producer.RoundRobinPartitioner --topic second_topic
```

In another terminal, start producing messages to the `second_topic` using the console producer. Note the use of a custom partitioner class.

## Step 4: Consume from Beginning

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic second_topic --from-beginning
```

Starts consuming messages from the beginning of the `second_topic`.

## Step 5: Display Key, Values, and Timestamp in Consumer

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic second_topic --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --property print.partition=true --from-beginning
```

Configures the consumer to display key, values, timestamp, and partition information for messages from the `second_topic`. This is achieved by specifying formatter options and setting additional properties.

Ensure that you replace "kafka-console-consumer.sh" with the appropriate script for your system (`kafka-console-consumer` or `kafka-console-consumer.bat`).
