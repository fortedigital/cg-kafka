package no.fortedigital.kafka.model;

import org.json.JSONObject;

public class Revision {
    public long old;
    public long new_;

    public static Revision fromJSON(JSONObject revisionJsonObject) {
        Revision revision = new Revision();
        revision.setOld(revisionJsonObject.getLong("old"));
        revision.setNew_(revisionJsonObject.getLong("new"));
        return revision;
    }

    public long getNew_() {
        return new_;
    }

    public void setNew_(long new_) {
        this.new_ = new_;
    }

    public long getOld() {
        return old;
    }

    public void setOld(long old) {
        this.old = old;
    }
}
