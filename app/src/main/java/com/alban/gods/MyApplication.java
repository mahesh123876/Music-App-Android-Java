package com.alban.gods;

import android.app.Application;

import java.util.ArrayList;

import Model.GetSongs;

public class MyApplication extends Application {

    private String activeSongId = "";
    private String songscate;
    private String SongUrl ;
    private String genrenotify;
    private boolean SongCircle ;
    public static MyApplication application;
    private String Songmainurl ;
    private String Songtrendingurl ;
    private String Songgenereurl ;

    private GetSongs songs;
    private String storeprofileurl;

    private String notify;



    private String trendingnotify;

    private String trendingsongscate;

    private  boolean enablegenere;
    private  boolean enabletrend;
    private  boolean enablealbum;
    private  boolean enablefav;
    private  boolean enablerecent;
    private  boolean enableplaylist;
    private boolean enablesearchsong;

    private  String generecate;
    private int calbum;
    private int cgenere;
    private int cart;
    private int ctrend;
    private int lyrics;

    private boolean commanurlenable;
    private String Arturl;
    private String Arturlmain;

    private  boolean enblecolor;


    private String artnotify;
    private  boolean enableart;
    private boolean enabledarkmode;
    private boolean enabledarkmodeforplaylist;

    private boolean notificationenble;
    private  String ss;
    private ArrayList<Dogs> dogsArrayList;



    private boolean search;
    private String gmail;
    private String username;
    private  String password;
    private String phonenumber;
    private String searchtxt;





    @Override
    public void onCreate() {
        super.onCreate();
        PrefStorage.initialize(this);
        application=this;
    }

    public String getSearchtxt() {
        return searchtxt;
    }

    public void setSearchtxt(String searchtxt) {
        this.searchtxt = searchtxt;
    }

    public boolean isEnablesearchsong() {
        return enablesearchsong;
    }

    public void setEnablesearchsong(boolean enablesearchsong) {
        this.enablesearchsong = enablesearchsong;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public ArrayList<Dogs> getDogsArrayList() {
        return dogsArrayList;
    }

    public void setDogsArrayList(ArrayList<Dogs> dogsArrayList) {
        this.dogsArrayList = dogsArrayList;
    }

    public int getCalbum() {
        return calbum;
    }

    public void setCalbum(int calbum) {
        this.calbum = calbum;
    }

    public int getCgenere() {
        return cgenere;
    }

    public void setCgenere(int cgenere) {
        this.cgenere = cgenere;
    }

    public int getCart() {
        return cart;
    }

    public void setCart(int cart) {
        this.cart = cart;
    }

    public int getCtrend() {
        return ctrend;
    }

    public void setCtrend(int ctrend) {
        this.ctrend = ctrend;
    }

    public int getLyrics() {
        return lyrics;
    }

    public void setLyrics(int lyrics) {
        this.lyrics = lyrics;
    }

    public String getSs() {
        return ss;
    }

    public boolean isEnabledarkmodeforplaylist() {
        return enabledarkmodeforplaylist;
    }

    public void setEnabledarkmodeforplaylist(boolean enabledarkmodeforplaylist) {
        this.enabledarkmodeforplaylist = enabledarkmodeforplaylist;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public boolean isNotificationenble() {
        return notificationenble;
    }

    public void setNotificationenble(boolean notificationenble) {
        this.notificationenble = notificationenble;
    }

    public boolean isEnabledarkmode() {
        return enabledarkmode;
    }

    public void setEnabledarkmode(boolean enabledarkmode) {
        this.enabledarkmode = enabledarkmode;
    }

    public boolean isEnableart() {
        return enableart;
    }

    public void setEnableart(boolean enableart) {
        this.enableart = enableart;
    }

    public String getArtnotify() {
        return artnotify;
    }

    public void setArtnotify(String artnotify) {
        this.artnotify = artnotify;
    }

    public boolean isEnblecolor() {
        return enblecolor;
    }

    public void setEnblecolor(boolean enblecolor) {
        this.enblecolor = enblecolor;
    }

    public String getArturlmain() {
        return Arturlmain;
    }

    public void setArturlmain(String arturlmain) {
        Arturlmain = arturlmain;
    }

    public boolean isCommanurlenable() {
        return commanurlenable;
    }

    public void setCommanurlenable(boolean commanurlenable) {
        this.commanurlenable = commanurlenable;
    }

    public String getArturl() {
        return Arturl;
    }

    public void setArturl(String arturl) {
        Arturl = arturl;
    }

    public boolean isEnablefav() {
        return enablefav;
    }

    public void setEnablefav(boolean enablefav) {
        this.enablefav = enablefav;
    }

    public boolean isEnablerecent() {
        return enablerecent;
    }

    public void setEnablerecent(boolean enablerecent) {
        this.enablerecent = enablerecent;
    }

    public boolean isEnableplaylist() {
        return enableplaylist;
    }

    public void setEnableplaylist(boolean enableplaylist) {
        this.enableplaylist = enableplaylist;
    }

    public boolean isEnablealbum() {
        return enablealbum;
    }


    public void setEnablealbum(boolean enablealbum) {
        this.enablealbum = enablealbum;
    }

    public boolean isEnabletrend() {
        return enabletrend;
    }

    public void setEnabletrend(boolean enabletrend) {
        this.enabletrend = enabletrend;
    }

    public String getGenerecate() {
        return generecate;
    }

    public void setGenerecate(String generecate) {
        this.generecate = generecate;
    }

    public boolean isEnablegenere() {
        return enablegenere;
    }

    public void setEnablegenere(boolean enablegenere) {
        this.enablegenere = enablegenere;
    }

    public String getSongscate() {
        return songscate;
    }

    public void setSongscate(String songscate) {
        this.songscate = songscate;
    }

    public String getTrendingsongscate() {
        return trendingsongscate;
    }

    public void setTrendingsongscate(String trendingsongscate) {
        this.trendingsongscate = trendingsongscate;
    }

    public String getTrendingnotify() {
        return trendingnotify;
    }

    public void setTrendingnotify(String trendingnotify) {
        this.trendingnotify = trendingnotify;
    }

    public String getGenrenotify() {
        return genrenotify;
    }

    public void setGenrenotify(String genrenotify) {
        this.genrenotify = genrenotify;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getStoreprofileurl() {
        return storeprofileurl;
    }

    public void setStoreprofileurl(String storeprofileurl) {
        this.storeprofileurl = storeprofileurl;
    }

    public GetSongs getSongs() {
        return songs;
    }

    public void setSongs(GetSongs songs) {
        this.songs = songs;
    }



    public String getSonggenereurl() {
        return Songgenereurl;
    }

    public void setSonggenereurl(String songgenereurl) {
        Songgenereurl = songgenereurl;
    }

    public String getSongtrendingurl() {
        return Songtrendingurl;
    }

    public void setSongtrendingurl(String songtrendingurl) {
        Songtrendingurl = songtrendingurl;
    }

    public String getSongmainurl() {
        return Songmainurl;
    }

    public void setSongmainurl(String songmainurl) {
        Songmainurl = songmainurl;
    }

    public String getActiveSongId() {
        return activeSongId;
    }

    public void setActiveSongId(String id) {
        activeSongId = id;
    }

    public String getSongUrl() {
        return SongUrl;
    }

    public void setSongUrl(String songUrl) {
        SongUrl = songUrl;
    }



    public boolean getSongCircle() {
        return SongCircle;
    }

    public void setSongCircle(boolean songCircle) {
        SongCircle = songCircle;
    }



}
