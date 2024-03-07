package no.fortedigital.kafka;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public class WikimediaSchemas {

    // Event
    public static final String ID_FIELD = "id";
    public static final String TYPE_FIELD = "type";
    public static final String TITLE_FIELD = "title";
    public static final String TITLE_URL_FIELD = "title_url";
    public static final String NAMESPACE_FIELD = "namespace";
    public static final String COMMENT_FIELD = "comment";
    public static final String TIMESTAMP_FIELD = "timestamp";
    public static final String USER_FILED = "user";
    public static final String BOT_FIELD = "bot";
    public static final String SERVER_URL_FIELD = "server_url";

    public static final String SERVER_NAME_FIELD = "server_name";

    public static final String SERVER_SCRIPT_PATH_FIELD = "server_script_path";
    public static final String NOTIFY_URL_FIELD = "notify_url";

    public static final String WIKI_FIELD = "wiki";
    public static final String PARSEDCOMMENT_FIELD = "parsedcomment";
    public static final String META_FIELD = "meta";



    // Edit

    public static final String MINOR_FIELD = "minor";
    public static final String PATROLLED_FIELD = "patrolled";
    public static final String LENGTH_FIELD = "length";
    public static final String REVISION_FIELD = "revision";

    // Log

    public static final String LOG_ID_FIELD = "log_id";
    public static final String LOG_TYPE_FIELD = "log_type";
    public static final String LOG_ACTION_FIELD = "log_action";
    public static final String LOG_PARAMS_FIELD = "log_params";

    // Log Params
    public static final String DURATION_FIELD = "duration";
    public static final String SITEWIDE_FIELD = "sitewide";
    public static final String FLAGS_FIELD = "flags";
    public static final String IMG_TIMESTAMP_FIELD = "img_timestamp";
    public static final String IMG_SHA1_FIELD = "img_sha1";;

    // length & Revison
    public static final String OLD_FIELD = "old";
    public static final String NEW_FIELD = "new";


    // META
    public static final String META_ID_FIELD = "id";
    public static final String URI_FIELD = "uri";
    public static final String DT_FIELD = "dt";
    public static final String REQUEST_ID_FIELD = "request_id";
    public static final String DOMAIN_FIELD = "domain";
    public static final String TOPIC_FIELD = "topic";
    public static final String PARTITION_FIELD = "partition";
    public static final String OFFSET_FIELD = "offset";
    // Schema names
    public static final String SCHEMA_KEY = "no.fortedigital.kafka.connect.wikimedia.EventKey";
    public static final String SCHEMA_VALUE = "no.fortedigital.kafka.connect.wikimedia.EventValue";

    public static final String SCHEMA_META = "no.fortedigital.kafka.connect.wikimedia.Meta";
    public static final String SCHEMA_LENGTH = "no.fortedigital.kafka.connect.wikimedia.Length";
    public static final String SCHEMA_REVISION = "no.fortedigital.kafka.connect.wikimedia.Revision";
    public static final String SCHEMA_LOG_PARAMS = "no.fortedigital.kafka.connect.wikimedia.LogParams";

    // Key Schema
    public static final Schema KEY_SCHEMA = SchemaBuilder.struct().name(SCHEMA_KEY)
            .version(1)
            .field(ID_FIELD, Schema.OPTIONAL_INT64_SCHEMA).optional()
            .build();

    // Log Params schema
    public static final Schema LOG_PARAMS_SCHEMA = SchemaBuilder.struct().name(SCHEMA_LOG_PARAMS)
            .version(1)
            .field(DURATION_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SITEWIDE_FIELD, Schema.OPTIONAL_BOOLEAN_SCHEMA)
            .field(FLAGS_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(IMG_TIMESTAMP_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(IMG_SHA1_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .optional()
            .build();

    // Length schema
    public static final Schema LENGTH_SCHEMA = SchemaBuilder.struct().name(SCHEMA_LENGTH)
            .version(1)
            .field(OLD_FIELD, Schema.OPTIONAL_INT64_SCHEMA)
            .field(NEW_FIELD, Schema.OPTIONAL_INT64_SCHEMA)
            .optional()
            .build();

    // Revision schema
    public static final Schema REVISION_SCHEMA = SchemaBuilder.struct().name(SCHEMA_REVISION)
            .version(1)
            .field(OLD_FIELD, Schema.OPTIONAL_INT64_SCHEMA)
            .field(NEW_FIELD, Schema.OPTIONAL_INT64_SCHEMA)
            .optional()
            .build();

    // Meta schema

    public static final Schema META_SCHEMA = SchemaBuilder.struct().name(SCHEMA_META)
            .version(1)
            .field(META_ID_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(URI_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(DT_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(REQUEST_ID_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(DOMAIN_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TOPIC_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(PARTITION_FIELD, Schema.OPTIONAL_INT32_SCHEMA)
            .field(OFFSET_FIELD, Schema.OPTIONAL_INT32_SCHEMA)
            .build();


    // Value Schema
    public static final Schema VALUE_SCHEMA = SchemaBuilder.struct().name(SCHEMA_VALUE)
            .version(1)
            .field(ID_FIELD, Schema.OPTIONAL_INT64_SCHEMA)
            .field(TYPE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(BOT_FIELD, Schema.OPTIONAL_BOOLEAN_SCHEMA)
            .field(NAMESPACE_FIELD, Schema.OPTIONAL_INT32_SCHEMA)
            .field(TITLE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TITLE_URL_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(COMMENT_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(TIMESTAMP_FIELD, Schema.OPTIONAL_INT32_SCHEMA)
            .field(USER_FILED, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SERVER_URL_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SERVER_NAME_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(SERVER_SCRIPT_PATH_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(WIKI_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(PARSEDCOMMENT_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(NOTIFY_URL_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            // LOG
            .field(LOG_ID_FIELD, Schema.OPTIONAL_INT64_SCHEMA)
            .field(LOG_TYPE_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(LOG_ACTION_FIELD, Schema.OPTIONAL_STRING_SCHEMA)
            .field(LOG_PARAMS_FIELD, LOG_PARAMS_SCHEMA)
            // Edit
            .field(MINOR_FIELD, Schema.OPTIONAL_BOOLEAN_SCHEMA)
            .field(PATROLLED_FIELD, Schema.OPTIONAL_BOOLEAN_SCHEMA)
            .field(LENGTH_FIELD, LENGTH_SCHEMA)
            .field(REVISION_FIELD, REVISION_SCHEMA)
            .field(META_FIELD, META_SCHEMA)
            .build();
}
