# Kafka Consumer Groups Steps

This README provides a step-by-step guide for using the `kafka-consumer-groups.sh` script to manage Kafka consumer groups. Replace "kafka-consumer-groups.sh" with "kafka-consumer-groups" or "kafka-consumer-groups.bat" based on your system.

## Step 1: List Consumer Groups

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```

Lists all consumer groups in the Kafka cluster.

## Step 2: Describe One Specific Group

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-second-application
```

Describes the details of the consumer group named `my-second-application`.

## Step 3: Describe Another Group

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-application
```

Describes the details of another consumer group named `my-first-application`.

## Step 4: Start a Consumer

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --group my-first-application
```

Starts a consumer to consume messages from the `first_topic` within the consumer group `my-first-application`.

## Step 5: Describe the Group Again

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-application
```

Describes the details of the consumer group `my-first-application` after starting a consumer.

## Step 6: Describe a Console Consumer Group

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group console-consumer-10592
```

Describes the details of a console consumer group (change the end number accordingly).

## Step 7: Start a Console Consumer

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --group my-first-application
```

Starts a console consumer to consume messages from the `first_topic` within the consumer group `my-first-application`.

## Step 8: Describe the Group Again

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-application
```

Describes the details of the consumer group `my-first-application` after starting a console consumer.

Ensure that you replace "kafka-consumer-groups.sh" with the appropriate script for your system (`kafka-consumer-groups` or `kafka-consumer-groups.bat`).
