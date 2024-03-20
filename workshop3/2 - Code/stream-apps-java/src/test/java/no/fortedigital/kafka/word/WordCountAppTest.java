package no.fortedigital.kafka.word;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.TestRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class WordCountAppTest {
    TopologyTestDriver testDriver;
    TestInputTopic<String, String> inputTopic;
    TestOutputTopic<String, Long> outputTopic;


    @Before
    public void setupTopologyTestDriver() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-junit");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        WordCountApp wordCountApp = new WordCountApp();
        testDriver = new TopologyTestDriver(wordCountApp.createTopology(), config);
        inputTopic = testDriver.createInputTopic("word-count-input", new StringSerializer(), new StringSerializer());
        outputTopic = testDriver.createOutputTopic("word-count-output", new StringDeserializer(), new LongDeserializer());
    }

    @After
    public void closeTestDriver() {
        testDriver.close();
    }

    @Test
    public void makeSureCountsAreCorrect() {
        pushNewInputRecord("testing Kafka streams");
        TestRecord<String, Long> firstOutputRecord = readOutputRecord();
        Assert.assertEquals("testing", firstOutputRecord.getKey());
        Assert.assertEquals(Long.valueOf(1), firstOutputRecord.getValue());

        TestRecord<String, Long> secondOutputRecord = readOutputRecord();
        Assert.assertEquals("kafka", secondOutputRecord.getKey());
        Assert.assertEquals(Long.valueOf(1), secondOutputRecord.getValue());

        TestRecord<String, Long> thirdOutputRecord = readOutputRecord();
        Assert.assertEquals("streams", thirdOutputRecord.getKey());
        Assert.assertEquals(Long.valueOf(1), thirdOutputRecord.getValue());

        String secondInput = "testing Kafka again";
        pushNewInputRecord(secondInput);

        TestRecord<String, Long> fourthOutputRecord = readOutputRecord();
        Assert.assertEquals("testing", fourthOutputRecord.getKey());
        Assert.assertEquals(Long.valueOf(2), fourthOutputRecord.getValue());

        TestRecord<String, Long> fifthOutputRecord = readOutputRecord();
        Assert.assertEquals("kafka", fifthOutputRecord.getKey());
        Assert.assertEquals(Long.valueOf(2), fifthOutputRecord.getValue());

        TestRecord<String, Long> sixthOutputRecord = readOutputRecord();
        Assert.assertEquals("again", sixthOutputRecord.getKey());
        Assert.assertEquals(Long.valueOf(1), sixthOutputRecord.getValue());
    }

    @Test
    public void makeSureWordsBecomeLowercase() {
        pushNewInputRecord("KAFKA kafka Kafka");

        TestRecord<String, Long> firstOutputRecord = readOutputRecord();

        Assert.assertEquals("kafka", firstOutputRecord.getKey());
        Assert.assertEquals(Long.valueOf(3), firstOutputRecord.getValue());
    }

    public void pushNewInputRecord(String value) {
        inputTopic.pipeInput("word-count-input", value);
    }

    public TestRecord<String, Long> readOutputRecord() {
        return outputTopic.readRecord();
    }
}
