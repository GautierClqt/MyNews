package com.cliquet.gautier.mynews.Models;

import java.util.ArrayList;
import java.util.List;

public class ArticlesElements {

    private String mTitle;
    private String mSection;
    private String mSubsection;
    private String mDate;
    private String mUrlArticle;
    private String mUrlImage;

    private int i;

    private int currentPage = 0;

    private ArrayList<Articles> articlesList = new ArrayList<>();

    public void settingListsPojoTopStories(List<Result> result) {

        for(i = 0; i <= result.size()-1; i++) {
            mTitle = result.get(i).getTitle();
            mSection = result.get(i).getSection();
            mSubsection = result.get(i).getSubsection();
            mDate = result.get(i).getUpdatedDate();
            mUrlArticle = result.get(i).getUrl();

            if(result.get(i).getMultimedia().size() != 0) {
                mUrlImage = result.get(i).getMultimedia().get(0).getUrl();
            }
            else {
                mUrlImage = "";
            }
            Articles articles = new Articles(mTitle, mSection, mSubsection, mDate, mUrlArticle, mUrlImage);
            articlesList.add(articles);
            this.setArticlesList(articlesList);
        }
    }


    public void settingListsPojoArticleSearch(Response response) {

        int mHits = Integer.parseInt(response.getMeta().getHits());
        int mMaxPage = (mHits / 10) - 1;

        if(currentPage <= mMaxPage) {
            for(i = 0; i <= response.getDocs().size()-1; i++) {
                mTitle = response.getDocs().get(i).getHeadline().getMain();
                mSection = response.getDocs().get(i).getSectionName();
                mSubsection = response.getDocs().get(i).getSubsectionName();
                mDate = response.getDocs().get(i).getPubDate();
                mUrlArticle = response.getDocs().get(i).getWebUrl();
                if(response.getDocs().get(i).getMultimedia().size() != 0) {
                    mUrlImage = "https://static01.nyt.com/"+response.getDocs().get(i).getMultimedia().get(0).getUrl();
                }
                else {
                    mUrlImage = "";
                }
                Articles articles = new Articles(mTitle, mSection, mSubsection, mDate, mUrlArticle, mUrlImage);
                articlesList.add(articles);
            }
            this.setCurrentPage(currentPage++);
            this.setArticlesList(articlesList);
        }
    }


    private void setArticlesList(ArrayList<Articles> articlesList) {
        this.articlesList = articlesList;
    }

    public ArrayList<Articles> getArticlesList() { return articlesList; }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }


}
