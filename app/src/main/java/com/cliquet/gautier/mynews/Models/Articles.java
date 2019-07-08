package com.cliquet.gautier.mynews.Models;

public class Articles {

    private String mTitle;
    private String mSection;
    private String mSubsection;
    private String mDate;
    private String mUrlArticle;
    private String mUrlImage;

    public Articles(String title, String section, String subsection, String date, String urlArticle, String urlImage) {
        this.mTitle = title;
        this.mSection = section;
        this.mSubsection = subsection;
        this.mDate = date;
        this.mUrlArticle = urlArticle;
        this.mUrlImage = urlImage;
    }

    public String getTitle() { return mTitle; }

    public String getSection() { return  mSection; }

    public String getSubsection() { return mSubsection; }

    public String getDate() { return mDate; }

    public String getUrlArticle() { return mUrlArticle; }

    public String getUrlImage() { return mUrlImage; }
}
