package no.fortedigital.kafka.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Properties;

public class BankBalanceApp {
    private static final Logger logger = LoggerFactory.getLogger(BankBalanceApp.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "bank-balance-application");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // we disable the cache to demonstrate all the "steps" involved in the transformation - not recommended in prod
        config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");

        // Exactly once processing!!
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);


        StreamsBuilder builder = new StreamsBuilder();
        // 1 - stream from kafka
        KStream<String, String> bankTransactions =  builder
                .stream("bank-transactions");

        Balance initialBalance = new Balance();
        String initialBalanceNode = objectMapper.writeValueAsString(initialBalance);

        // 2 - GroupByKey to get streams per user
        bankTransactions
                .groupByKey()
                // 3 - aggregate to sum the amounts
                .aggregate(
                        () -> initialBalanceNode,
                        (key, value, balance) -> newBalance(balance, value)
                ).mapValues(value -> {
                    try {
                        return objectMapper.writeValueAsString(value);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                // 4 - write the result back to kafka
                .toStream().to("bank-balances");

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.cleanUp();
        streams.start();
        logger.info("topology {}", streams);
        // shutdown hook to correctly close the stream application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private static String newBalance(String balance, String value) {
        Balance newBalance = new Balance();

        try {
            Transaction transaction = objectMapper.readValue(value, Transaction.class);
            Balance balanceNode = objectMapper.readValue(balance, Balance.class);
            newBalance.setBalance(balanceNode.getBalance() + transaction.getAmount());
            newBalance.setCount(balanceNode.getCount() + 1);

            Long balanceEpoch = Instant.parse(balanceNode.getTime()).toEpochMilli();
            Long transactionEpoch = Instant.parse(transaction.getTime()).toEpochMilli();
            Instant newBalanceInstant = Instant.ofEpochMilli(Math.max(balanceEpoch, transactionEpoch));
            newBalance.setTime(newBalanceInstant.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            return objectMapper.writeValueAsString(newBalance);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
