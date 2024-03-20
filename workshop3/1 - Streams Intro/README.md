# Kafka Streams Word Count Example

This repository contains instructions to set up and run a Kafka Streams Word Count Example.

## Prerequisites

Before you begin, ensure you have the following installed:

- Docker
- Kafka (ensure it's running)

## Instructions

Follow these steps to run the Kafka Streams Word Count Example:

### 1. Connect to Kafka Docker Container

```bash
docker exec -it kafka0 sh
```
### 2. Create Input Topic

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --topic streams-plaintext-input --create --replication-factor 1 --partitions 1
```

### 3. Create Output Topic

```bash
kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic streams-wordcount-output
```

### 4. Start Kafka Producer

```bash
kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input
```

Enter the following lines and then exit:

```
kafka streams udemy
kafka data processing
kafka streams course
```

### 5. Verify Data

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-plaintext-input --from-beginning
```

### 6. Start Consumer on Output Topic

```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic streams-wordcount-output \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```

### 7. Start the Streams Application

```bash
kafka-run-class.sh org.apache.kafka.streams.examples.wordcount.WordCountDemo
```

### 8. Verify Data in Output Topic

Ensure the data has been written to the output topic.

## Conclusion

You have successfully set up and run the Kafka Streams Word Count Example. For more information and customization, refer to Kafka documentation.
```

Feel free to adjust the instructions according to your specific use case or environment.
