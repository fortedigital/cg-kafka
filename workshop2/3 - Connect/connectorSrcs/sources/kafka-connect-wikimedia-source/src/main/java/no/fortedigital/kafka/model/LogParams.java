package no.fortedigital.kafka.model;

import org.json.JSONObject;

public class LogParams {
    public String duration;
    public boolean sitewide;
    public String flags;
    public String img_timestamp;
    public String img_sha1;

    public static LogParams fromJSON(JSONObject json) {
        LogParams logParams = new LogParams();
        if (json.has("img_timestamp"))
            logParams.setImg_timestamp(json.getString("img_timestamp"));
        if (json.has("img_sha1"))
            logParams.setImg_sha1(json.getString("img_sha1"));
        if (json.has("duration"))
            logParams.setDuration(json.getString("duration"));
        if (json.has("sitewide"))
            logParams.setSitewide(json.getBoolean("sitewide"));
        if (json.has("flags"))
            logParams.setFlags(json.getString("flags"));
        return logParams;
    }

    public String getImg_timestamp() {
        return img_timestamp;
    }

    public void setImg_timestamp(String img_timestamp) {
        this.img_timestamp = img_timestamp;
    }

    public String getImg_sha1() {
        return img_sha1;
    }

    public void setImg_sha1(String img_sha1) {
        this.img_sha1 = img_sha1;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isSitewide() {
        return sitewide;
    }

    public void setSitewide(boolean sitewide) {
        this.sitewide = sitewide;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }
}
