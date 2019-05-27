package com.cliquet.gautier.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedium> multimedia = null;


    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }
    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }
    public void setMultimedia(List<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }
}

