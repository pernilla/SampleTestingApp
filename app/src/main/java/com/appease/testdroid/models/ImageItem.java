package com.appease.testdroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class ImageItem {

    @SerializedName("title")
    private String title;

    @SerializedName("link")
    private String link;

    @SerializedName("media")
    private HashMap<String, String> media;

    @SerializedName("date_taken")
    private String dateTaken;

    @SerializedName("description")
    private String description;

    @SerializedName("published")
    private String published;

    @SerializedName("author")
    private String author;

    @SerializedName("author_id")
    private String author_id;

    @SerializedName("tags")
    private String tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public HashMap<String, String> getMedia() {
        return media;
    }

    public void setMedia(HashMap<String, String> media) {
        this.media = media;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String date_taken) {
        this.dateTaken = date_taken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
