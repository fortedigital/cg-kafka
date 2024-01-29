# Workshop 1: Docker Compose

### The `docker-compose.yml` file

This file contains everything you need to run kafka locally.

To spin it up, you will need to have `docker-compose` installed, and do the following:

- in your terminal, `cd` to this directory (ie. where the `docker-compose.yml` file is)
- run the following command: `docker-compose up -d`

You should now have a running kafka cluster on your machine.

#### Breakdown of the file

`services`: this is where we define containers. 

This file has 4 containers: `kafka-0`, `kafka-1`, `kafka-2` and `kafka-ui`

Each container has some properties:

- `image` is the image of this container.
- `container_name` is the container's name, ie. how you'll refer to it in the CLI. If this isn't specified, docker will give it a random name.
- `ports` defines what network ports should be open between the container and the host. We need this in order to let network traffic through. If not specified, then docker will block all traffic by default.
- `environment` here we set the environment variables inside the container. This is typically used to give some extra configuration options for the applications inside the container.
- `volumes` this binds data volumes to the container. E.g. the container `kafka-2` has the following declaration: `kafka_2_data:/bitnami/kafka`, which means: bind the volume by the name of `kafka_2_data` to the directory `/bitnami/kafka` inside the container.

The volumes themselves are defined at the root level with the `volumes` key. We need volumes when we want to persist data in a container, because a container's file-system will reset every time the container is stopped and started. When we define volumes, they will keep data between container restarts.

---

# Workshop 1: Getting started
In this workshop we will get a simple first look at Kafka and how to create messages and consume those messages.

## Requirements

To follow this tutorial you need to have an understanding of programming with c#, java, og python. You can complete the workshop using your editor of choice.

The code assumes you have Kafka running, if you don't you can quickly get started by running the the Docker Kafka compose file found [here(docker-compose.yml)](../../docker-compose.yml).

## Producer

### Step 1.

Generate a new topic, "purchases", with 3 partitions and a replication factor of 2. Use the CLI or the Kafka Ui.
Then using the CLI create a consumer that consumes messages from *purchases*.

### Step 2.

Create a producer that sends a single message to this topic. The **Key** should be empty, and the **Value** should be a string item.

Make sure that you can se the message appear in your CLI Consumer, or from the Kafka UI.

### Step 3.

Add callback, the callback should record the partition, offset

### Step 4.

Create a list of users, and a list of random objects.
Replace the single message with a loop that creates 15 messages, with a random user as a key, and a random item as the message.

```c#
    string[] users = { "Daud", "Thomas", "Ola", "Erol", "Ludvig" };
    string[] items = { "Book", "Alarm clock", "T-shirts", "Gift card", "Batteries", "Soda", "Coffee mug" };
```

Check that the messages appear in the CLI Consumer, and note how the messages are stored on the different partitions.

## Consumer

### Step 1.

Create a consumer that consumes messages from the *purchases*. Run this consumer at the same time as you run the CLI.
A consumer runs in a infinite loop, make sure you can handle exiting this loop gracefully.

## Step 2.

Start a second version of the consumer, if you are also running the CLI you now have 3 different consumers running. What do you notice about the messages now?

Can you add a third version of your consumer?

---

# Time left? Try the following!

## Custom object

Create a new topic with 3 partitions and a replication factor of 2.
Create a class *Purchase* with a price, name, id, and quantity.

Replace the items with instances of the Purchase class and add a random quantity. Instead of sending a single string message, try to add a specified Serializer to the kafka message, and send an instance of the Purchase class.

Make sure your consumer is also registered to consume the class with the same serializer.

## Still have more time?

Notice how the producer and consumers take custom arguments as input? 
How could you simplify how the consumer/producer are created, maybe prepare the program for different environments?

**Hint:**

<span class="blacked-out">Use a configuration, and a settings file.</span>
<style>
.blacked-out {
    background-color: black;
    color: black;
    user-select: none;
}
.blacked-out:hover {
    background-color: gray;
    color: white;
}
</style>