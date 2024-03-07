# Kafka Console Consumer Steps

This README provides a step-by-step guide for using the `kafka-console-consumer.sh` script to consume messages from Kafka topics. Replace "kafka-console-consumer.sh" with "kafka-console-consumer" or "kafka-console-consumer.bat" based on your system.

## Step 1: Create a Topic with 3 Partitions

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3
```

Creates a Kafka topic named `third_topic` with 3 partitions.

## Step 2: Start One Consumer

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic third_topic --group my-first-application
```

Starts one consumer in the group `my-first-application` to consume messages from the `third_topic`.

## Step 3: Start One Producer and Begin Producing

```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --producer-property partitioner.class=org.apache.kafka.clients.producer.RoundRobinPartitioner --topic third_topic
```

Starts a producer to produce messages to the `third_topic`. The producer is configured with a custom partitioner class (`org.apache.kafka.clients.producer.RoundRobinPartitioner`).

## Step 4: Start Another Consumer in the Same Group

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic third_topic --group my-first-application
```

Starts another consumer in the same group (`my-first-application`). Observe how messages are distributed among the consumers in the same consumer group.

## Step 5: Start Another Consumer in a Different Group from Beginning

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic third_topic --group my-second-application --from-beginning
```

Starts another consumer in a different group (`my-second-application`) and consumes messages from the beginning of the `third_topic`. This is useful when you want to consume all messages from the start.

Ensure that you replace "kafka-console-consumer.sh" with the appropriate script for your system (`kafka-console-consumer` or `kafka-console-consumer.bat`).
