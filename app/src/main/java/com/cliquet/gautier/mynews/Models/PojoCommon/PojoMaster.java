package com.cliquet.gautier.mynews.Models.PojoCommon;

import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PojoMaster {
    
    @SerializedName("results")
    @Expose
    private List<Results> results = null;
    @SerializedName("response")
    @Expose
    private Response response;

    public List<Results> getResults() {
        return results;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
