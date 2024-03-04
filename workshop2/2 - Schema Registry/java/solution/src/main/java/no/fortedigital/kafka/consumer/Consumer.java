package no.fortedigital.kafka.consumer;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;
import no.fortedigital.kafka.producer.Value;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {

        String topic = "test-schema-json";

        Properties properties = new Properties();

        // set producer properties
        properties.setProperty("bootstrap.servers", "localhost:19092");
        properties.setProperty("security.protocol", "PLAINTEXT");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", KafkaJsonSchemaDeserializer.class.getName());
        properties.setProperty(KafkaJsonSchemaSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        properties.setProperty("group.id", "my-group-id");
        properties.setProperty("auto.offset.reset", "earliest");

        // Create the consumer
        KafkaConsumer<String, Value> consumer = new KafkaConsumer<>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(List.of(topic));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Detected a shutdown, let's exit by calling consumer.wakeup()");
            consumer.wakeup();
        }));

        while(true) {
            ConsumerRecords<String, Value> records = consumer.poll(Duration.ofSeconds(1));

            records.forEach(record -> {
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition: " + record.partition() + ", Offset: " + record.offset());
            });
        }
    }
}
