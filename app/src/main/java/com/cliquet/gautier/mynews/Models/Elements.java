package com.cliquet.gautier.mynews.Models;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Elements {

    private List<Result> mResults;

    private ArrayList<String> sections = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> subsections = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> url = new ArrayList<>();
    //private ArrayList<URL> images = new ArrayList<>();

    public Elements(List<Result> mResults){
        this.mResults = mResults;
    }

    public Elements() {
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> mResults) {
        this.mResults = mResults;
    }

    public void putElementsInLists(List<Result> mResults) {

        for(int i = 0; i <= mResults.size()-1; i++) {
            sections.add(mResults.get(i).getSection());
            subsections.add(mResults.get(i).getSubsection());
            titles.add(mResults.get(i).getTitle());
            dates.add(mResults.get(i).getUpdatedDate());
            url.add(mResults.get(i).getUrl());
        }
    }
}
