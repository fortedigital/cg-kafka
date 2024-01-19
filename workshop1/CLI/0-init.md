# Initial README for Kafka Commands

This README provides an overview of Kafka commands used to manage topics, and all commands are assumed to be executed within the context of the `kafka0` Docker container.

## Docker Container Setup

Ensure that you have the Kafka Docker container named `kafka0` up and running before executing the following commands. If you haven't set up the container, please refer to our Kafka setup documentation or use the following Docker command to run a basic Kafka container:

```bash
docker run -d --name kafka0 -p 9092:9092 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 wurstmeister/kafka:latest
```

## Kafka Topics Commands

### Connect to Kafka Container

```bash
docker exec -it kafka0 sh
```

This command connects to the Kafka container (`kafka0`) using Docker.

### Operations on Localhost

#### List All Topics

```bash
kafka-topics.sh --bootstrap-server localhost:9092 --list
```

Lists all topics in the Kafka cluster with the specified bootstrap server (`localhost:9092`).

... (continue with other readmes)

