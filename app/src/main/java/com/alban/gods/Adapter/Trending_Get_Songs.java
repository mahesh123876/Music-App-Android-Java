package com.alban.gods.Adapter;

public class Trending_Get_Songs {
    String songCategory,songTitle,artist,album_art,songDuration,songlink,mKey;

    public Trending_Get_Songs() {

    }

    public Trending_Get_Songs(String songCategory, String songTitle, String artist, String album_art, String songDuration, String songlink, String mKey) {
        if (songTitle.trim().equals("")){
            songTitle="No Title";
        }
        this.songCategory = songCategory;
        this.songTitle = songTitle;
        this.artist = artist;
        this.album_art = album_art;
        this.songDuration = songDuration;
        this.songlink = songlink;
        this.mKey = mKey;
    }

    public String getSongCategory() {
        return songCategory;
    }

    public void setSongCategory(String songCategory) {
        this.songCategory = songCategory;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getSonglink() {
        return songlink;
    }

    public void setSonglink(String songlink) {
        this.songlink = songlink;
    }

    public String getmKey(String key) {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
