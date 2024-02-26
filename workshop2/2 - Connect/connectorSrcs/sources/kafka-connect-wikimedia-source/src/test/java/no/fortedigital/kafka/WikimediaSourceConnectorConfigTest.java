package no.fortedigital.kafka;

import org.apache.kafka.common.config.ConfigDef;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static no.fortedigital.kafka.WikimediaSourceConnectorConfig.TOPIC_CONFIG;
import static org.junit.Assert.assertTrue;

public class WikimediaSourceConnectorConfigTest {

    private ConfigDef configDef = WikimediaSourceConnectorConfig.config();
    private Map<String, String> config;

    @Before
    public void setUpInitialConfig() {
        config = new HashMap<>();
        config.put(TOPIC_CONFIG, "wikimedia-events");

    }

    @Test
    public void doc() {
        System.out.println(WikimediaSourceConnectorConfig.config().toRst());
    }

    @Test
    public void initialConfigIsValid() {
        assertTrue(configDef.validate(config)
                .stream()
                .allMatch(configValue -> configValue.errorMessages().isEmpty()));
    }

    @Test
    public void canReadConfigCorrectly() {
        WikimediaSourceConnectorConfig config = new WikimediaSourceConnectorConfig(this.config);
        config.getTopic();

    }

}
