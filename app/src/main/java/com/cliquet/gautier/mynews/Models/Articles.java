package com.cliquet.gautier.mynews.Models;

public class Articles {

    private String mTitle;
    private String mSection;
    private String mDate;
    private String mUrlArticle;
    private String mUrlImage;
    private String mId;

    public Articles(String title, String section, String date, String urlArticle, String urlImage, String id) {
        this.mTitle = title;
        this.mSection = section;
        this.mDate = date;
        this.mUrlArticle = urlArticle;
        this.mUrlImage = urlImage;
        this.mId = id;
    }

    public String getTitle() { return mTitle; }

    public String getSection() { return  mSection; }

    public String getDate() { return mDate; }

    public String getUrlArticle() { return mUrlArticle; }

    public String getUrlImage() { return mUrlImage; }

    public String getId() { return mId; }
}
