package com.android.rftutelage.Profile;

public class photo_model {
    private String photo_name;
    private int imgid;

    public photo_model(String photo_name, int imgid) {
        this.photo_name = photo_name;
        this.imgid = imgid;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
