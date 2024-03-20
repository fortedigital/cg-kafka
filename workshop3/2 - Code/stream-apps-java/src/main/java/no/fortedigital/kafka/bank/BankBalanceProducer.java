package no.fortedigital.kafka.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Properties;

public class BankBalanceProducer {
    private static final Logger logger = LoggerFactory.getLogger(BankBalanceProducer.class);


    private static final List<String> users = List.of("Mark", "Alice", "Tom", "Fred", "Rebecca", "Kim");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException, InterruptedException {
        String topic = "bank-transactions";

        // Create producer config
        Properties config = new Properties();

        // set producer config
        config.setProperty("bootstrap.servers", "localhost:19092");
        config.setProperty("security.protocol", "PLAINTEXT");
        config.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Create the producer
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(config)) {
            while (true) {
                for (int i = 0; i < 100; i++) {
                    String name = users.get(i % users.size());
                    String key = name;
                    Transaction transaction = new Transaction();
                    transaction.name = name;
                    transaction.amount = (i * 10) % 100;
                    transaction.time = Instant.now().toString();

                    String value = objectMapper.writeValueAsString(transaction);

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
                        } else {
                            logger.error("Can't produce,getting error", exception);
                        }
                    });
                }
                Thread.sleep(1000);
                producer.flush();
            }
        }
    }
}
