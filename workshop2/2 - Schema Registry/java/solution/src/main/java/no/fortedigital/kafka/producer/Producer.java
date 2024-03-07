package no.fortedigital.kafka.producer;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);


    public static void main(String[] args) {
        String topic = "test-schema-json";

        // Create producer properties
        Properties properties = new Properties();

        // set producer properties
        properties.setProperty("bootstrap.servers", "localhost:19092");
        properties.setProperty("security.protocol", "PLAINTEXT");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaJsonSchemaSerializer.class.getName());
        properties.setProperty(KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        // Create the producer
        KafkaProducer<String, Value> producer = new KafkaProducer<>(properties);

        Value value = new Value();
        value.setF1("hello world!!");
        //value.setF2(42);
        value.setF3(true);

        for (int i = 0; i < 10; i++) {
            // create producer record
            ProducerRecord<String, Value> producerRecord = new ProducerRecord<>(topic, value);

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
        }



        // flush and close producer
        producer.flush();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Detected a shutdown, let's exit by calling consumer.wakeup()");
            producer.close();
        }));

    }
}
