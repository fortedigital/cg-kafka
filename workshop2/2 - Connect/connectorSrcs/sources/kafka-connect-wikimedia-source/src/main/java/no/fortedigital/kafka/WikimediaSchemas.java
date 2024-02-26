package no.fortedigital.kafka;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class WikimediaSchemas {

    // Event
    public static final String ID_FIELD = "id";
    public static final String TYPE_FIELD = "type";
    public static final String TITLE_FIELD = "title";
    public static final String TITLE_URL_FIELD = "title_url";
    public static final String COMMENT_FIELD = "comment";
    public static final String TIMESTAMP_FIELD = "timestamp";
    public static final String USER_FILED = "user";
    public static final String BOT_FIELD = "bot";
    public static final String SERVER_URL_FIELD = "server_url";
    public static final String WIKI_FIELD = "wiki";
    public static final String PARSEDCOMMENT_FIELD = "parsedcomment";
    public static final String REQUEST_ID_FIELD = "request_id";
    public static final String DOMAIN_FIELD = "domain";
    public static final String TOPIC_FIELD = "topic";
    public static final String PARTITION_FIELD = "partition";
    public static final String OFFSET_FIELD = "offset";
    // Schema names
    public static final String SCHEMA_KEY = "no.fortedigital.kafka.connect.wikimedia.EventKey";
    public static final String SCHEMA_VALUE = "no.fortedigital.kafka.connect.wikimedia.EventValue";

    // Key Schema
    public static final Schema KEY_SCHEMA = SchemaBuilder.struct().name(SCHEMA_KEY)
            .version(1)
            .field(ID_FIELD, Schema.STRING_SCHEMA)
            .build();

    // Value Schema
    public static final Schema VALUE_SCHEMA = SchemaBuilder.struct().name(SCHEMA_VALUE)
            .version(1)
            .field(ID_FIELD, Schema.STRING_SCHEMA)
            .field(TYPE_FIELD, Schema.STRING_SCHEMA)
            .field(BOT_FIELD, Schema.BOOLEAN_SCHEMA)
            .field(TITLE_FIELD, Schema.STRING_SCHEMA)
            .field(TITLE_URL_FIELD, Schema.STRING_SCHEMA)
            .field(COMMENT_FIELD, Schema.STRING_SCHEMA)
            .field(TIMESTAMP_FIELD, Schema.INT32_SCHEMA)
            .field(USER_FILED, Schema.STRING_SCHEMA)
            .field(SERVER_URL_FIELD, Schema.STRING_SCHEMA)
            .field(WIKI_FIELD, Schema.STRING_SCHEMA)
            .field(PARSEDCOMMENT_FIELD, Schema.STRING_SCHEMA)
            .field(REQUEST_ID_FIELD, Schema.STRING_SCHEMA)
            .field(DOMAIN_FIELD, Schema.STRING_SCHEMA)
            .field(TOPIC_FIELD, Schema.STRING_SCHEMA)
            .field(PARTITION_FIELD, Schema.INT32_SCHEMA)
            .field(OFFSET_FIELD, Schema.INT32_SCHEMA)
            .build();
}
