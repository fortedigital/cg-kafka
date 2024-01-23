# Initial README for Kafka Commands

This README provides an overview of Kafka commands used to manage topics, and all commands are assumed to be executed within the context of the `kafka0` Docker container.

## Docker Container Setup

Ensure that you have the Kafka Docker container named `kafka0` up and running before executing the following commands. If you haven't set up the container, please use the following Docker command to run a basic Kafka cluster in the workshop directory:

```bash
docker compose up -d
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

