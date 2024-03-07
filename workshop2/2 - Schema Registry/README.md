# Workshop 2: Working with Kafka Schema Registry

In this workshop, we'll build upon the concepts introduced in the previous workshop and integrate Kafka Schema Registry for managing schema evolution and compatibility between producers and consumers.

## Requirements

To follow this tutorial, you need to have a basic understanding of programming with C#, Java, or Python. You can use your preferred code editor for the implementation.

Make sure Kafka is running. If not, you can quickly set it up by running the Docker Kafka compose file found [here (docker-compose.yml)](../../docker-compose.yml).

## Kafka Schema Registry Setup

Before proceeding, ensure Kafka Schema Registry is set up and running alongside Kafka. You can refer to the Kafka documentation for installation and configuration instructions.

## Producer

### Step 1.

Generate a new topic, "purchases-with-schema", with 3 partitions and a replication factor of 2. Use either the CLI or Kafka UI.

### Step 2.

Create a producer that sends messages to this topic, utilizing the Kafka Schema Registry for schema management. Each message should conform to a defined schema.

#### Step 2.1.
Defining properties value.serializer/value.deserializer and schema.registry.url will automatically create schema on message creation
#### Step 2.2.
Confirm schema creation after sending a message

### Step 3.

Modify the producer to send 15 messages, each containing data corresponding to the defined schema. Use randomly selected users and items from predefined lists.

```c#
string[] users = { "Daud", "Thomas", "Ola", "Erol", "Ludvig" };
string[] items = { "Book", "Alarm clock", "T-shirts", "Gift card", "Batteries", "Soda", "Coffee mug" };
```

## Consumer

### Step 1.

Create a consumer to consume messages from the "purchases-with-schema" topic. Ensure the consumer can handle graceful termination from an infinite loop.

#### Step 2
Defining properties value.deserializer and schema.registry.url will automatically fetch schema and deserialize message when reading from topic


## Conclusion

In this workshop, we extended our understanding of Kafka by integrating Kafka Schema Registry for managing schemas between producers and consumers. This enables better schema evolution and compatibility handling in a Kafka ecosystem.
