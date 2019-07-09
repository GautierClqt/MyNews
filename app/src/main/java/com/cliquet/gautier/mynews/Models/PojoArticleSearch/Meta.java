package com.cliquet.gautier.mynews.Models.PojoArticleSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("hits")
    @Expose
    private String hits;

    public String getHits() { return hits; }

    public void getHits(String hits) { this.hits = hits; }

}
