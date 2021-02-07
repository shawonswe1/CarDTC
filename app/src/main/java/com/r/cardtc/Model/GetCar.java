package com.r.cardtc.Model;

public class GetCar {
    String name,uid,country;
    String image;

    public GetCar() {
    }

    public GetCar(String name, String uid, String country, String image) {
        this.name = name;
        this.uid = uid;
        this.country = country;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
