package com.cliquet.gautier.mynews.Models;

import java.util.ArrayList;
import java.util.List;

public class ArticlesElements {

    private PojoArticleSearch pojoArticleSearch = new PojoArticleSearch();
    private List<Docs> docs;

    private int hits;
    private int currentPage = 0;
    private int maxPage;

    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> section = new ArrayList<>();
    private ArrayList<String> subsection = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> urlArticle = new ArrayList<>();
    private ArrayList<String> urlImage = new ArrayList<>();

    private ArrayList<ArrayList<String>> arraylists = new ArrayList<>();

    private void checkingCurrentPage() {
    }

    public void settingListsPojoArticleSearch(Response response) {

        hits = Integer.parseInt(response.getMeta().getHits());
        maxPage = (hits/10) - 1;

        if(currentPage <= maxPage) {
            for(int i = 0; i <= response.getDocs().size()-1; i++) {
                title.add(response.getDocs().get(i).getHeadline().getMain());
                section.add(response.getDocs().get(i).getSectionName());
                subsection.add(response.getDocs().get(i).getSubsectionName());
                date.add(response.getDocs().get(i).getPubDate());
                urlArticle.add(response.getDocs().get(i).getWebUrl());
                if(response.getDocs().get(i).getMultimedia().size() != 0) {
                    urlImage.add(response.getDocs().get(i).getMultimedia().get(0).getUrl());
                }
                else {
                    urlImage.add("");
                }
            }
            creatingArraylists();
        }
    }

    private void creatingArraylists() {
        arraylists.add(title);
        arraylists.add(section);
        arraylists.add(subsection);
        arraylists.add(date);
        arraylists.add(urlArticle);
        arraylists.add(urlImage);

        setArraylists(arraylists);
    }

    private void setArraylists(ArrayList<ArrayList<String>> arraylists) {
        this.arraylists = arraylists;
    }

    public ArrayList<ArrayList<String>> getArraylists() {
        return arraylists;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }


}
