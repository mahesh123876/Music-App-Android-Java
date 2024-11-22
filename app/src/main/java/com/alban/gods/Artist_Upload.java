package com.alban.gods;

public class Artist_Upload {
    public String name;
    public String url;
    public String songsCategory;


    public Artist_Upload() {

    }

    public Artist_Upload(String name, String url, String songCategory) {
        this.name = name;
        this.url = url;
        this.songsCategory = songCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSongCategory() {
        return songsCategory;
    }

    public void setSongCategory(String songsCategory) {
        this.songsCategory = songsCategory;
    }
}
