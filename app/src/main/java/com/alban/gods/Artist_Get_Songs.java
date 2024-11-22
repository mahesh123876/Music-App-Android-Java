package com.alban.gods;

public class Artist_Get_Songs {
    String GsongCategory,GsongTitle,Gartist,Galbum_art,GsongDuration,Gsonglink,GmKey;

    public Artist_Get_Songs() {

    }

    public Artist_Get_Songs(String gsongCategory, String gsongTitle, String gartist, String galbum_art, String gsongDuration, String gsonglink, String gmKey) {
        if (gsongTitle.trim().equals("")){
            gsongTitle="No Title";
        }
        GsongCategory = gsongCategory;
        GsongTitle = gsongTitle;
        Gartist = gartist;
        Galbum_art = galbum_art;
        GsongDuration = gsongDuration;
        Gsonglink = gsonglink;
        GmKey = gmKey;
    }

    public String getGsongCategory() {
        return GsongCategory;
    }

    public void setGsongCategory(String gsongCategory) {
        GsongCategory = gsongCategory;
    }

    public String getGsongTitle() {
        return GsongTitle;
    }

    public void setGsongTitle(String gsongTitle) {
        GsongTitle = gsongTitle;
    }

    public String getGartist() {
        return Gartist;
    }

    public void setGartist(String gartist) {
        Gartist = gartist;
    }

    public String getGalbum_art() {
        return Galbum_art;
    }

    public void setGalbum_art(String galbum_art) {
        Galbum_art = galbum_art;
    }

    public String getGsongDuration() {
        return GsongDuration;
    }

    public void setGsongDuration(String gsongDuration) {
        GsongDuration = gsongDuration;
    }

    public String getGsonglink() {
        return Gsonglink;
    }

    public void setGsonglink(String gsonglink) {
        Gsonglink = gsonglink;
    }

    public String getGmKey(String key) {
        return GmKey;
    }

    public void setGmKey(String gmKey) {
        GmKey = gmKey;
    }
}
