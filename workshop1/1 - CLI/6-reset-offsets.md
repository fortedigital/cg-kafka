# Kafka Consumer Groups Steps

This README provides a step-by-step guide for using the `kafka-consumer-groups` script to manage Kafka consumer groups. Replace "kafka-consumer-groups" with "kafka-consumer-groups.sh" or "kafka-consumer-groups.bat" based on your system.

## Step 1: Describe the Consumer Group

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-application
```

Describes the details of the consumer group named `my-first-application`.

## Step 2: Dry Run: Reset the Offsets to the Beginning of Each Partition

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest --topic third_topic --dry-run
```

Performs a dry run to reset the offsets to the beginning of each partition for the `my-first-application` consumer group in the `third_topic`.

## Step 3: Execute Flag is Needed

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest --topic third_topic --execute
```

Executes the offset reset for the `my-first-application` consumer group in the `third_topic`. Note that the `--execute` flag is needed to perform the actual offset reset.

## Step 4: Describe the Consumer Group Again

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-application
```

Describes the details of the consumer group `my-first-application` after resetting the offsets.

## Step 5: Consume from Where the Offsets Have Been Reset

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic third_topic --group my-first-application
```

Starts a console consumer to consume messages from the `third_topic` within the consumer group `my-first-application` after resetting the offsets.

## Step 6: Describe the Group Again

```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-application
```

Describes the details of the consumer group `my-first-application` after consuming messages from the reset offsets.

Ensure that you replace "kafka-consumer-groups" with the appropriate script for your system (`kafka-consumer-groups.sh` or `kafka-consumer-groups.bat`).
