package com.cliquet.gautier.mynews.Models.PojoArticleSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("docs")
    @Expose
    private List<Docs> docs = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Docs> getDocs() {
        return docs;
    }

    public Meta getMeta() { return meta; }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
