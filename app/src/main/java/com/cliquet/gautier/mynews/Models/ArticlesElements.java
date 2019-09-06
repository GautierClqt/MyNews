package com.cliquet.gautier.mynews.Models;

import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;
import com.cliquet.gautier.mynews.Models.PojoCommon.Results;

import java.util.ArrayList;
import java.util.List;

public class ArticlesElements {

    private int i;
    private int currentPage;
    private boolean stopRequest = false;

    private ArrayList<Articles> articlesSearchList = new ArrayList<>();

    public List<Articles> settingListsPojoTopStories(List<Results> results) {

        List<Articles> articlesList = new ArrayList<>();
        String mUrlImage;

        if(results != null) {
            for (i = 0; i <= results.size() - 1; i++) {
                String mTitle = results.get(i).getTitle();
                String mSection = results.get(i).getSection();
                String mSubsection = results.get(i).getSubsection();
                if (!(mSubsection == null || mSubsection.equals(""))) {
                    mSection = mSection + " > " + mSubsection;
                }
                String mDate = results.get(i).getUpdatedDate();
                String mUrlArticle = results.get(i).getUrl();

                if (results.get(i).getMultimedia().size() != 0) {
                    mUrlImage = results.get(i).getMultimedia().get(0).getUrl();
                } else {
                    mUrlImage = "";
                }

                String mId = mTitle + results.get(i).getCreatedDate();

                Articles articles = new Articles(mTitle, mSection, mDate, mUrlArticle, mUrlImage, mId, 0);
                articlesList.add(articles);
            }
        }

        return articlesList;
    }


    public ArrayList<Articles> settingListsPojoArticleSearch(Response response) {

        String mUrlImage;

        int mHits = Integer.parseInt(response.getMeta().getHits());

        //mHits represents the number of Articles in the response, if mHits is less than 10 then mMaxPage will equals to -1
        int mMaxPage = (mHits / 10);

        //mMaxPage could be less than 0 if there is less than 10 articles in the response
        if(stopRequest || currentPage <= mMaxPage || mHits <= 10) {
            for(i = 0; i <= response.getDocs().size()-1; i++) {
                String mTitle = response.getDocs().get(i).getHeadline().getMain();
                String mSection = response.getDocs().get(i).getSectionName();
                String mSubsection = response.getDocs().get(i).getSubsectionName();
                if(!(mSubsection == null || mSubsection.equals(""))){
                    mSection = mSection + " > " + mSubsection;
                }
                String mDate = response.getDocs().get(i).getPubDate();
                String mUrlArticle = response.getDocs().get(i).getWebUrl();
                if(response.getDocs().get(i).getMultimedia().size() != 0) {
                    mUrlImage = "https://static01.nyt.com/"+response.getDocs().get(i).getMultimedia().get(0).getUrl();
                }
                else {
                    mUrlImage = "";
                }
                String mId = response.getDocs().get(i).getId();

                Articles articles = new Articles(mTitle, mSection, mDate, mUrlArticle, mUrlImage, mId, mMaxPage);
                articlesSearchList.add(articles);
                if(articlesSearchList.size() == mHits-1) {
                    stopRequest = true;
                    int test = 1232135456;
                }
            }
        }
//        else {
//            currentPage = mMaxPage;
//            stopRequest = true;
//            //articlesSearchList.clear();
//        }
//        if(articlesSearchList.size() == mHits) {
//            stopRequest = true;
//            int test = 1232135456;
//        }
        return articlesSearchList;
    }

    public List<Articles> settingListsMostPopular(List<Results> results) {

        String mUrlImage;

        if(results != null) {
            for (i = 0; i <= results.size() - 1; i++) {
                String mTitle = results.get(i).getTitle();
                String mSection = results.get(i).getSection();
                String mSubsection = results.get(i).getSubsection();
                if (!(mSubsection == null || mSubsection.equals(""))) {
                    mSection = mSection + " > " + mSubsection;
                }
                String mDate = results.get(i).getPublished_date();
                String mUrlArticle = results.get(i).getUrl();

                if (results.get(i).getMedia() != null) {
                    mUrlImage = results.get(i).getMedia().get(0).getMedia_metadata().get(0).getUrl();
                } else {
                    mUrlImage = "";
                }
                String mId = results.get(i).getId();

                Articles articles = new Articles(mTitle, mSection, mDate, mUrlArticle, mUrlImage, mId, 0);
                articlesSearchList.add(articles);
            }
        }
        return articlesSearchList;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public boolean getStopRequest() {
        return stopRequest;
    }
}
