package no.fortedigital.kafka.model;


import org.json.JSONObject;

import static no.fortedigital.kafka.WikimediaSchemas.*;

public class Event {
    public Meta meta;
    public long id;
    public String type;
    public int namespace;
    public String title;
    public String title_url;
    public String comment;
    public int timestamp;
    public String user;
    public boolean bot;
    public String server_url;
    public String server_name;
    public String server_script_path;
    public String notify_url;
    public String wiki;
    public String parsedcomment;

    // Edit
    public boolean minor;
    public boolean patrolled;
    public Length length;
    public Revision revision;

    // Log
    public long log_id;
    public String log_type;
    public String log_action;
    public LogParams log_params;

    public Event() {
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public String getServer_script_path() {
        return server_script_path;
    }

    public void setServer_script_path(String server_script_path) {
        this.server_script_path = server_script_path;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public boolean isMinor() {
        return minor;
    }

    public void setMinor(boolean minor) {
        this.minor = minor;
    }

    public boolean isPatrolled() {
        return patrolled;
    }

    public void setPatrolled(boolean patrolled) {
        this.patrolled = patrolled;
    }

    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }

    public Revision getRevision() {
        return revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public String getLog_type() {
        return log_type;
    }

    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    public String getLog_action() {
        return log_action;
    }

    public void setLog_action(String log_action) {
        this.log_action = log_action;
    }

    public LogParams getLog_params() {
        return log_params;
    }

    public void setLog_params(LogParams log_params) {
        this.log_params = log_params;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta event) {
        this.meta = event;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNamespace() {
        return namespace;
    }

    public void setNamespace(int namespace) {
        this.namespace = namespace;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_url() {
        return title_url;
    }

    public void setTitle_url(String title_url) {
        this.title_url = title_url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public String getServer_url() {
        return server_url;
    }

    public void setServer_url(String server_url) {
        this.server_url = server_url;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getParsedcomment() {
        return parsedcomment;
    }

    public void setParsedcomment(String parsedcomment) {
        this.parsedcomment = parsedcomment;
    }


    public static Event fromJSON(JSONObject jsonObject) {
        JSONObject metaJsonObject = jsonObject.getJSONObject("meta");
        Meta meta = Meta.fromJSON(metaJsonObject);

        Event event = new Event();
        if (jsonObject.has(ID_FIELD)) {
            event.setId(jsonObject.getLong(ID_FIELD));
        }
        String type = jsonObject.getString(TYPE_FIELD);
        event.setType(type);

        if (type.equals("edit")) {
            JSONObject lengthJsonObject = jsonObject.getJSONObject("length");
            Length length = Length.fromJSON(lengthJsonObject);
            event.setLength(length);

            JSONObject revisionJsonObject = jsonObject.getJSONObject("revision");
            Revision revision = Revision.fromJSON(revisionJsonObject);
            event.setRevision(revision);
        } else if (type.equals("log")) {
            event.setLog_id(jsonObject.getLong(LOG_ID_FIELD));
            event.setLog_type(jsonObject.getString(LOG_TYPE_FIELD));
            event.setLog_action(jsonObject.getString(LOG_ACTION_FIELD));
            if (jsonObject.has(LOG_PARAMS_FIELD)) {
                Object logParamsJsonObject = jsonObject.get(LOG_PARAMS_FIELD);
                if (logParamsJsonObject instanceof JSONObject) {
                    LogParams logParams = LogParams.fromJSON((JSONObject) logParamsJsonObject);
                    event.setLog_params(logParams);

                }
            }
        }

        event.setNamespace(jsonObject.getInt(NAMESPACE_FIELD));
        event.setTitle(jsonObject.getString(TITLE_FIELD));
        event.setTitle_url(jsonObject.getString(TITLE_URL_FIELD));
        event.setComment(jsonObject.getString(COMMENT_FIELD));
        event.setTimestamp(jsonObject.getInt(TIMESTAMP_FIELD));
        event.setUser(jsonObject.getString(USER_FILED));
        event.setServer_url(jsonObject.getString(SERVER_URL_FIELD));
        event.setWiki(jsonObject.getString(WIKI_FIELD));
        event.setParsedcomment(jsonObject.getString(PARSEDCOMMENT_FIELD));
        event.setBot(jsonObject.getBoolean(BOT_FIELD));
        event.setMeta(meta);
        return event;
    }
}
