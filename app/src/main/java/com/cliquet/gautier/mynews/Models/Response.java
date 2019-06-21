package com.cliquet.gautier.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("docs")
    @Expose
    private List<Docs> docs = null;

    public List<Docs> getDocs() {
        return docs;
    }

    public void setDocs(List<Docs> docs) {
        this.docs = docs;
    }

}
