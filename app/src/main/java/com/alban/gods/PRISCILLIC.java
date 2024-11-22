package com.alban.gods;

import android.app.Application;

import Model.GetSongs;

public class PRISCILLIC extends Application {

    private GetSongs activeSong = null;

    @Override
    public void onCreate() {
        super.onCreate();
        PrefStorage.initialize(this);
    }

    public void setActiveSong(GetSongs aSong) {
        activeSong = aSong;
    }

    public GetSongs getActiveSong() {
        return activeSong;
    }

    public String getActiveSongId() {
        return ""+activeSong.getSongTitle().hashCode();
    }
}
