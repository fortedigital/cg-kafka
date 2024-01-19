# Kafka Console Producer Steps

This README outlines the step-by-step process for Kafka console producer operations. Make sure to replace "kafka-console-producer.sh" with "kafka-console-producer" or "kafka-console-producer.bat" based on your system.

## Step 1: Create a Topic with 1 Partition

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create --partitions 1
```

Create a Kafka topic named `first_topic` with a single partition.

## Step 2: Produce Messages

### Basic Message Production

```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic
> Hello World
> My name is Conduktor
> I love Kafka
> ^C  (<- Ctrl + C is used to exit the producer)
```

Produce messages to the `first_topic` using the console producer.

### Message Production with Properties

```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --producer-property acks=all
> some message that is acked
> just for fun
> fun learning!
```

Produce messages to the `first_topic` with custom producer properties.

### Producing to a Non-Existing Topic

```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic new_topic
> hello world!
```

Produce messages to a non-existing topic (`new_topic`).

## Step 3: Topic Inspection

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --list
kafka-topics.sh --bootstrap-server localhost:9092 --topic new_topic --describe
```

List all topics and describe the `new_topic` to inspect its details.

## Step 4: Edit Compose.yml for Kafka Brokers

Edit the `compose.yml` file and add `KAFKA_CFG_NUM_PARTITIONS=3` to all Kafka brokers.

## Step 5: Produce to a Modified Topic

### Producing to a Non-Existing Topic (After Modification)

```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic new_topic_2
hello again!
```

Produce messages to a non-existing topic (`new_topic_2`) after modifying the number of partitions.

### Topic Inspection (After Modification)

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --list
kafka-topics.sh --bootstrap-server localhost:9092 --topic new_topic_2 --describe
```

List all topics and describe the `new_topic_2` to inspect its details after modifying the number of partitions.

## Step 6: Reminder - Create Topics with Appropriate Partitions

Ensure to create topics with the appropriate number of partitions before producing messages to them.

## Step 7: Produce with Keys

```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property key.separator=:
> example key:example value
> name:Stephane
```

Produce messages with keys to the `first_topic` using the console producer, specifying key properties.
