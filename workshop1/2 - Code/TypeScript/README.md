# Kafka .net Producer and Consumer

In this workshop we will get a simple first look at Kafka and how to create messages and consume those messages.

## Requirements

To follow this tutorial you need to have an understanding of programming with JavaScript or TypeScript. You can complete the workshop using Visual Studio Code, or the editor of your choice.

The code assumes you have Kafka running, if you don't you can quickly get started by running the the Docker Kafka compose file found [here(docker-compose.yml)](../../docker-compose.yml).

## Step-by-step

To get started with Kafka you will create a producer and consumer that connects to a running Kafka service. You will se how the producer create entries, and how the consumers can retrieve them.

The producer will add messages mimicking a purchases log. And the consumer will read the log.

Libraries needed are already provided in the package.json file. To run your typescript code you need to install the dependencies. You can do this by running `npm install` in the root of the project.

To run any code you create you can run:
    
```bash
npx ts-node src/<filename>.ts
```
