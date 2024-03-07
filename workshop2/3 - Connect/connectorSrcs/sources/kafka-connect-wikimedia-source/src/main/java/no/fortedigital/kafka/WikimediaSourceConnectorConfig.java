package no.fortedigital.kafka;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Importance;
import java.util.Map;

public class WikimediaSourceConnectorConfig extends AbstractConfig {

  public static final String TOPIC_CONFIG = "topic";
  private static final String TOPIC_DOC = "Topic to write to";

  public WikimediaSourceConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
    super(config, parsedConfig);
  }

  public WikimediaSourceConnectorConfig(Map<String, String> parsedConfig) {
    this(config(), parsedConfig);
  }


  public static ConfigDef config() {
    return new ConfigDef()
            .define(TOPIC_CONFIG, Type.STRING, Importance.HIGH, TOPIC_DOC);
  }

  public String getTopic() {
    return this.getString(TOPIC_CONFIG);
  }

}
