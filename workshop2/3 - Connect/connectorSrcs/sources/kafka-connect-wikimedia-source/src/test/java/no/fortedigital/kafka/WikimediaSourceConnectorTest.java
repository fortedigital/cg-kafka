package no.fortedigital.kafka;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static no.fortedigital.kafka.WikimediaSourceConnectorConfig.TOPIC_CONFIG;
import static org.junit.Assert.assertEquals;

public class WikimediaSourceConnectorTest {

  private Map<String, String> initialConfig() {
    Map<String, String> baseProps = new HashMap<>();
    baseProps.put(TOPIC_CONFIG, "wikimedia-events");
    return (baseProps);
  }

  @Test
  public void taskConfigsShouldReturnOneTaskConfig() {
      WikimediaSourceConnector wikimediaSourceConnector = new WikimediaSourceConnector();
      wikimediaSourceConnector.start(initialConfig());
      assertEquals(wikimediaSourceConnector.taskConfigs(1).size(),1);
      assertEquals(wikimediaSourceConnector.taskConfigs(10).size(),1);
  }
}
