package com.cliquet.gautier.mynews.Models;

import java.util.ArrayList;

public class Articles {

    private ArrayList<ArticlesElements> articlesList = new ArrayList<>();
    private PojoArticleSearch pojoArticleSearch = new PojoArticleSearch();
    private Response response;

    private String mTitle;
    private String mSection;
    private String mSubsection;
    private String mDate;
    private String mUrlArticle;
    private String mUrlImage;



    public Articles(Response respones) {
        this.mTitle = response.getDocs().get(0).getHeadline().getMain();
        int troisdeuxunzero = 3210;
        this.mSection = mSection;
        this.mSubsection = mSubsection;
        this.mDate = mDate;
        this.mUrlArticle = mUrlArticle;
        this.mUrlImage = mUrlImage;
    }



}
