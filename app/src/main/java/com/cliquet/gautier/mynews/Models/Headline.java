package com.cliquet.gautier.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headline {

    @SerializedName("main")
    @Expose
    private String main;

    public String getMain () {
        return main;
    }

    public void setMain (String main) {
        this.main = main;
    }
}
