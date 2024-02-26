package no.fortedigital.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikimediaSourceConnector extends SourceConnector {
  /*
    Your connector should never use System.out for logging. All of your classes should use slf4j
    for logging
 */
  private static Logger log = LoggerFactory.getLogger(WikimediaSourceConnector.class);
  private WikimediaSourceConnectorConfig config;

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  @Override
  public void start(Map<String, String> map) {
    config = new WikimediaSourceConnectorConfig(map);
    //TODO: Add things you need to do to setup your connector.
  }

  @Override
  public Class<? extends Task> taskClass() {
    //TODO: Return your task implementation.
    return WikimediaSourceTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int i) {
    // Define the individual task configurations that will be executed.
    ArrayList<Map<String, String>> configs = new ArrayList<>(1);
    configs.add(config.originalsStrings());
    return configs;
  }

  @Override
  public void stop() {
    // Do things that are necessary to stop your connector.
    // nothing is necessary to stop for this connector
  }

  @Override
  public ConfigDef config() {
    return WikimediaSourceConnectorConfig.config();
  }
}
