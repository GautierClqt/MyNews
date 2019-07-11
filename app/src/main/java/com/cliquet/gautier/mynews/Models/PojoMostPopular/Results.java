package com.cliquet.gautier.mynews.Models.PojoMostPopular;

import java.util.List;

public class Results {

    private String section;

    private String subsection;

    private List<Media> media;

    private String title;

    private String url;

    private String id;

    private String byline;

    private String published_date;

    private String views;

    public String getSection ()
    {
        return section;
    }

    public void setSection (String section)
    {
        this.section = section;
    }

    public List<Media> getMedia ()
    {
        return media;
    }

    public void setMedia (List<Media> media)
    {
        this.media = media;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

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

    public String getSubsection() { return subsection; }

    public void setSubsection(String subsection) { this.subsection = subsection; }
}
