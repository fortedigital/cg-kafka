package no.fortedigital.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerWithKey {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        String topic = "my-topic";
        String key = "id_1";
        String value = "hello world!";

        // Create producer properties
        Properties properties = new Properties();

        // set producer properties
        properties.setProperty("bootstrap.servers", "localhost:19092");
        properties.setProperty("security.protocol", "PLAINTEXT");
        properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create producer record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);

        // send data
        producer.send(producerRecord, (recordMetadata, exception) -> {
            if (exception == null) {
                logger.info("Successfully received new metadata \n" +
                        "Topic: " + recordMetadata.topic() + "\n" +
                        "Partition: " + recordMetadata.partition() + "\n" +
                        "Offset: " + recordMetadata.offset() + "\n" +
                        "Timestamp: " + recordMetadata.timestamp());
            }

            else {
                logger.error("Can't produce,getting error", exception);
            }
        });

        // flush and close producer
        producer.flush();
    }
}



