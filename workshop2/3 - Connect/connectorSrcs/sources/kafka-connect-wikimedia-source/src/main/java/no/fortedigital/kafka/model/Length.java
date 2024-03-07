package no.fortedigital.kafka.model;

import org.json.JSONObject;

public class Length {
    public long old;
    public long new_;

    public static Length fromJSON(JSONObject lengthJsonObject) {
        Length length = new Length();
        length.setOld(lengthJsonObject.getLong("old"));
        length.setNew_(lengthJsonObject.getLong("new"));
        return length;

    }

    public long getOld() {
        return old;
    }

    public void setOld(long old) {
        this.old = old;
    }

    public long getNew_() {
        return new_;
    }

    public void setNew_(long new_) {
        this.new_ = new_;
    }
}

