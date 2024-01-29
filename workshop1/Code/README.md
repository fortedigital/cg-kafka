
# Workshop 1: Getting started with Kafka 

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

### Step 2.

Start a second version of the consumer, if you are also running the CLI you now have 3 different consumers running. What do you notice about the messages now?

Can you add a third version of your consumer?

---
<br>

# Time left? Try the following.

## Custom object

Create a class *Purchase* with a price, name, id, and quantity.

Replace the items with instances of the Purchase class and add a random quantity. Instead of sending a single string message, try to add a specified Serializer to the kafka message, and send an instance of the Purchase class.


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