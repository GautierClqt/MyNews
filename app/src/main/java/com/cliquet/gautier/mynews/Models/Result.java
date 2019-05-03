package com.cliquet.gautier.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("material_type_facet")
    @Expose
    private String materialTypeFacet;
    @SerializedName("kicker")
    @Expose
    private String kicker;

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

    public String getAbstract() {
        return _abstract;
    }
    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getByline() {
        return byline;
    }
    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getMaterialTypeFacet() {
        return materialTypeFacet;
    }
    public void setMaterialTypeFacet(String materialTypeFacet) {
        this.materialTypeFacet = materialTypeFacet;
    }

    public String getKicker() {
        return kicker;
    }
    public void setKicker(String kicker) {
        this.kicker = kicker;
    }
}

