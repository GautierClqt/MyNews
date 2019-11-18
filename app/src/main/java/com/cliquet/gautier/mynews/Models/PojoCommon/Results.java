package com.cliquet.gautier.mynews.Models.PojoCommon;

import com.cliquet.gautier.mynews.Models.PojoMostPopular.Media;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {

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
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedia> multimedia = null;

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

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }
    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    private List<Media> media;

    private String id;

    private String byline;

    private String published_date;

    private String views;


    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getByline ()
    {
        return byline;
    }

    public void setByline (String byline)
    {
        this.byline = byline;
    }

    public String getPublished_date ()
    {
        return published_date;
    }

    public void setPublished_date (String published_date)
    {
        this.published_date = published_date;
    }

    public String getViews ()
    {
        return views;
    }

    public void setViews (String views)
    {
        this.views = views;
    }

    public List<Media> getMedia ()
    {
        return media;
    }

    public void setMedia (List<Media> media)
    {
        this.media = media;
    }

}

