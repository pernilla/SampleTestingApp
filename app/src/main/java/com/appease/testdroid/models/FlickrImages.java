package com.appease.testdroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FlickrImages {

    @SerializedName("title")
    private String title;

    @SerializedName("link")
    private String link;

    @SerializedName("description")
    private String description;

    @SerializedName("modified")
    private String modified;

    @SerializedName("generator")
    private String generator;

    @SerializedName("items")
    private List<ImageItem> imageItems;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public boolean hasImageItems() {
        return imageItems != null && imageItems.size() > 0;
    }

    public void addImageItem(ImageItem imageItem) {
        if(imageItem == null)
            imageItems = new ArrayList<ImageItem>();

        imageItems.add(imageItem);
    }

    public List<ImageItem> getImages() {
        return imageItems;
    }
}
