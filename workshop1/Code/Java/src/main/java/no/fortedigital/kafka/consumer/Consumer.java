package no.fortedigital.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class Consumer {
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

        // Create the consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(List.of(topic));

        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

            records.forEach(record -> {
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition: " + record.partition() + ", Offset: " + record.offset());
            });
        }
    }
}
