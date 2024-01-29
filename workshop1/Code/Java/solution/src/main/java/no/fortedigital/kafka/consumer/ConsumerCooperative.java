package no.fortedigital.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumerCooperative {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerWithShutdown.class);

    public static void main(String[] args) {

        String topic = "my-topic";

        Properties properties = new Properties();

        // set producer properties
        properties.setProperty("bootstrap.servers", "localhost:19092");
        properties.setProperty("security.protocol", "PLAINTEXT");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", "my-group-id");
        properties.setProperty("auto.offset.reset", "earliest");
        //properties.setProperty("partition.assignment.strategy", CooperativeStickyAssignor.class.getName());

        // Create the consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(List.of(topic));

        // Set up shutdown hook
        setupShutdownHook(consumer);

        // Start the consumer loop
        consumeMessages(consumer);

        // The main thread will reach here only after the consumer is closed
        logger.info("Exiting");
    }

    private static void setupShutdownHook(KafkaConsumer<String, String> consumer) {
        // get reference to the main thread
        final Thread mainThread = Thread.currentThread();

        // adding the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Detected a shutdown, let's exit by calling consumer.wakeup()");
            consumer.wakeup();

            // join the main thread to allow the execution of the code in the main thread
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private static void consumeMessages(KafkaConsumer<String, String> consumer) {
        try {
            logger.info("Initiating polling...");
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                records.forEach(record -> {
                    logger.info("Key: " + record.key() + ", Value: " + record.value());
                    logger.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                });
            }
        } catch (WakeupException e) {
            logger.info("Received shutdown signal!");
        } catch (Exception e) {
            logger.error("Error while consuming", e);
        } finally {
            consumer.close();
            logger.info("Consumer closed!");
        }
    }
}


