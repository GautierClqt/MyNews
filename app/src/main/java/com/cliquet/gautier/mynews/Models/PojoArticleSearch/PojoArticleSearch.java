package com.cliquet.gautier.mynews.Models.PojoArticleSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PojoArticleSearch {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
