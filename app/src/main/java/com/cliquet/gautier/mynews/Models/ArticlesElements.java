package com.cliquet.gautier.mynews.Models;

import android.content.SharedPreferences;

import com.cliquet.gautier.mynews.Models.PojoArticleSearch.Response;
import com.cliquet.gautier.mynews.Models.PojoTopStories.Result;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ArticlesElements {

    private int i;

    private int currentPage;

    private Gson gson = new Gson();
    private ArrayList<Articles> articlesSearchList = new ArrayList<>();

    public List<Articles> settingListsPojoTopStories(List<Result> result) {

        List<Articles> articlesList = new ArrayList<>();
        String mUrlImage;

        for(i = 0; i <= result.size()-1; i++) {
            String mTitle = result.get(i).getTitle();
            String mSection = result.get(i).getSection();
            String mSubsection = result.get(i).getSubsection();
            String mDate = result.get(i).getUpdatedDate();
            String mUrlArticle = result.get(i).getUrl();

            if(result.get(i).getMultimedia().size() != 0) {
                mUrlImage = result.get(i).getMultimedia().get(0).getUrl();
            }
            else {
                mUrlImage = "";
            }
            Articles articles = new Articles(mTitle, mSection, mSubsection, mDate, mUrlArticle, mUrlImage);
            articlesList.add(articles);
        }

        return articlesList;
    }


    public ArrayList<Articles> settingListsPojoArticleSearch(Response response) {

        String mUrlImage;

        int mHits = Integer.parseInt(response.getMeta().getHits());
        int mMaxPage = (mHits / 10) - 1;

        if(currentPage <= mMaxPage) {
            for(i = 0; i <= response.getDocs().size()-1; i++) {
                String mTitle = response.getDocs().get(i).getHeadline().getMain();
                String mSection = response.getDocs().get(i).getSectionName();
                String mSubsection = response.getDocs().get(i).getSubsectionName();
                String mDate = response.getDocs().get(i).getPubDate();
                String mUrlArticle = response.getDocs().get(i).getWebUrl();
                if(response.getDocs().get(i).getMultimedia().size() != 0) {
                    mUrlImage = "https://static01.nyt.com/"+response.getDocs().get(i).getMultimedia().get(0).getUrl();
                }
                else {
                    mUrlImage = "";
                }
                Articles articles = new Articles(mTitle, mSection, mSubsection, mDate, mUrlArticle, mUrlImage);
                articlesSearchList.add(articles);
            }
        }
        else {
            currentPage = mMaxPage;
            articlesSearchList.clear();
        }
        return articlesSearchList;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }


}
