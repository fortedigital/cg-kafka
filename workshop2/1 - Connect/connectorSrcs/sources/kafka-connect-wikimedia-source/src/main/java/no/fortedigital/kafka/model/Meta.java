package no.fortedigital.kafka.model;

import org.json.JSONObject;

import static no.fortedigital.kafka.WikimediaSchemas.*;

public class Meta {
    public String request_id;
    public String domain;
    public String topic;
    public int partition;
    public int offset;

    public Meta() {
    }
    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public static Meta fromJSON(JSONObject jsonObject) {
        Meta meta = new Meta();
        meta.setRequest_id(jsonObject.getString(REQUEST_ID_FIELD));
        meta.setPartition(jsonObject.getInt(PARTITION_FIELD));
        meta.setOffset(jsonObject.getInt(OFFSET_FIELD));
        meta.setTopic(jsonObject.getString(TOPIC_FIELD));
        meta.setDomain(jsonObject.getString(DOMAIN_FIELD));
        return meta;
    }
}
