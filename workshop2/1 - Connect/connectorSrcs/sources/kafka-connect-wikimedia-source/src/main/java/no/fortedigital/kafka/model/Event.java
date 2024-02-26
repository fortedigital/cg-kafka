package no.fortedigital.kafka.model;


import org.json.JSONObject;

import static no.fortedigital.kafka.WikimediaSchemas.*;

public class Event {
    public Meta meta;
    public String id;
    public String type;
    public int namespace;
    public String title;
    public String title_url;
    public String comment;
    public int timestamp;
    public String user;
    public boolean bot;
    public String server_url;
    public String wiki;
    public String parsedcomment;

    public Event() {
    }
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta event) {
        this.meta = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        event.setId(metaJsonObject.getString(ID_FIELD));
        event.setType(jsonObject.getString(TYPE_FIELD));

        event.setNamespace(jsonObject.getInt("namespace"));
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
