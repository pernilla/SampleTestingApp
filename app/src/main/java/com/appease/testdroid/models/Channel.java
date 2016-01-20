package com.appease.testdroid.models;

import java.util.ArrayList;
import java.util.List;

public class Channel {

    private String title;
    private String description;

    private List<NewsItem> newsItems;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void addNewsItem(NewsItem newsItem) {
        if(newsItems == null) {
            newsItems = new ArrayList<NewsItem>();
        }

        newsItems.add(newsItem);
    }
}
