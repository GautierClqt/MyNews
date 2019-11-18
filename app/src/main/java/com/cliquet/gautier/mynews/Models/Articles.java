package com.cliquet.gautier.mynews.Models;

public class Articles {

    private String mTitle;
    private String mSection;
    private String mDate;
    private String mUrlArticle;
    private String mUrlImage;
    private String mId;
    private int mMaxPage;

    Articles(String title, String section, String date, String urlArticle, String urlImage, String id, int maxPage) {
        this.mTitle = title;
        this.mSection = section;
        this.mDate = date;
        this.mUrlArticle = urlArticle;
        this.mUrlImage = urlImage;
        this.mId = id;
        this.mMaxPage = maxPage;
    }

    public String getTitle() { return mTitle; }

    public String getSection() { return  mSection; }

    public String getDate() { return mDate; }

    public String getUrlArticle() { return mUrlArticle; }

    public String getUrlImage() { return mUrlImage; }

    public String getId() { return mId; }

    public int getMaxPage() { return mMaxPage; }
}
