package no.fortedigital.kafka;

import no.fortedigital.kafka.model.Event;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static no.fortedigital.kafka.WikimediaSchemas.*;

public class WikimediaSourceTask extends SourceTask {
  private static final Logger log = LoggerFactory.getLogger(WikimediaSourceTask.class);
  public WikimediaSourceConnectorConfig config;

  WikimediaAPIHttpClient wikimediaAPIHttpClient;

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  @Override
  public void start(Map<String, String> map) {
    //Do things here that are required to start your task. This could be open a connection to a database, etc.
    config = new WikimediaSourceConnectorConfig(map);
    wikimediaAPIHttpClient = new WikimediaAPIHttpClient(config);
  }



  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    // fetch data
    final ArrayList<SourceRecord> records = new ArrayList<>();
    JSONArray issues = wikimediaAPIHttpClient.getEvents();
    // we'll count how many results we get with i
    int i = 0;
    for (Object obj : issues) {
      JSONObject jsonObject = (JSONObject) obj;
      Event event = Event.fromJSON((jsonObject));
      SourceRecord sourceRecord = generateSourceRecord(event);
      records.add(sourceRecord);
      i += 1;
    }
    if (i > 0) log.info(String.format("Fetched %s record(s)", i));
    return records;
  }

  private SourceRecord generateSourceRecord(Event event) {
    return new SourceRecord(
            sourcePartition(event),
            sourceOffset(event),
            config.getTopic(),
            null, // partition will be inferred by the framework
            KEY_SCHEMA,
            buildRecordKey(event),
            VALUE_SCHEMA,
            buildRecordValue(event),
            (long) event.getTimestamp());
  }

  @Override
  public void stop() {
    // Do whatever is required to stop your task.
  }

  private Map<String, String> sourcePartition(Event event) {
    Map<String, String> map = new HashMap<>();
    map.put(TOPIC_FIELD, event.getMeta().getTopic());
    map.put(PARTITION_FIELD, String.valueOf(event.getMeta().getPartition()));
    return map;
  }

  private Map<String, String> sourceOffset(Event event) {
    Map<String, String> map = new HashMap<>();
    map.put(OFFSET_FIELD, String.valueOf(event.getMeta().getOffset()));
    return map;
  }

  private Struct buildRecordKey(Event event){
    // Key Schema
      return new Struct(KEY_SCHEMA)
            .put(ID_FIELD, event.getId());
  }

  public Struct buildRecordValue(Event event){
      // meta struct
    Struct meta = new Struct(META_SCHEMA)
            .put(META_ID_FIELD, event.getMeta().getId())
            .put(URI_FIELD, event.getMeta().getUri())
            .put(DT_FIELD, event.getMeta().getDt())
            .put(REQUEST_ID_FIELD, event.getMeta().getRequest_id())
            .put(DOMAIN_FIELD, event.getMeta().getDomain())
            .put(TOPIC_FIELD, event.getMeta().getTopic())
            .put(PARTITION_FIELD, event.getMeta().getPartition())
            .put(OFFSET_FIELD, event.getMeta().getOffset());


    Struct value = new Struct(VALUE_SCHEMA)
            .put(ID_FIELD, event.getId())
            .put(TYPE_FIELD, event.getType())
            .put(TITLE_FIELD, event.getTitle())
            .put(TITLE_URL_FIELD, event.getTitle_url())
            .put(COMMENT_FIELD, event.getComment())
            .put(TIMESTAMP_FIELD, event.getTimestamp())
            .put(USER_FILED, event.getUser())
            .put(SERVER_URL_FIELD, event.getServer_url())
            .put(SERVER_NAME_FIELD, event.getServer_name())
            .put(SERVER_SCRIPT_PATH_FIELD, event.getServer_script_path())
            .put(NAMESPACE_FIELD, event.getNamespace())
            .put(WIKI_FIELD, event.getWiki())
            .put(PARSEDCOMMENT_FIELD, event.getParsedcomment())
            .put(BOT_FIELD, event.isBot());

    if (event.getType().equals("edit")) {
      value.put(LENGTH_FIELD, new Struct(LENGTH_SCHEMA)
              .put(OLD_FIELD, event.getLength().getOld())
              .put(NEW_FIELD, event.getLength().getNew_()))
              .put(REVISION_FIELD, new Struct(REVISION_SCHEMA)
              .put(OLD_FIELD, event.getRevision().getOld())
              .put(NEW_FIELD, event.getRevision().getNew_()))
              .put(MINOR_FIELD, event.isMinor())
              .put(PATROLLED_FIELD, event.isPatrolled());
    } else if (event.getType().equals("log")) {
      value.put(LOG_ID_FIELD, event.getLog_id())
              .put(LOG_TYPE_FIELD, event.getLog_type())
              .put(LOG_ACTION_FIELD, event.getLog_action());
      if (event.getLog_params() != null) {
        value.put(LOG_PARAMS_FIELD, new Struct(LOG_PARAMS_SCHEMA)
                .put(DURATION_FIELD, event.getLog_params().getDuration())
                .put(SITEWIDE_FIELD, event.getLog_params().isSitewide())
                .put(FLAGS_FIELD, event.getLog_params().getFlags())
                .put(IMG_TIMESTAMP_FIELD, event.getLog_params().getImg_timestamp())
                .put(IMG_SHA1_FIELD, event.getLog_params().getImg_sha1()));
      }
    }

    value.put(META_FIELD, meta);

    return value;

  }
}
