package com.alban.gods.Adapter;

public class ImageSliderModel {
    String fakename;
    String fakeimage;

    public ImageSliderModel() {

    }

    public ImageSliderModel(String fakename, String fakeimage) {
        this.fakename = fakename;
        this.fakeimage = fakeimage;
    }

    public String getFakename() {
        return fakename;
    }

    public void setFakename(String fakename) {
        this.fakename = fakename;
    }

    public String getFakeimage() {
        return fakeimage;
    }

    public void setFakeimage(String fakeimage) {
        this.fakeimage = fakeimage;
    }
}
