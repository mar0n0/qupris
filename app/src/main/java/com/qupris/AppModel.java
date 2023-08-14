package com.qupris;

import java.util.ArrayList;

public class AppModel {
    String name;
    String package_name;
    String piis;
    int score;
    int image;

    public AppModel(String name, String package_name, int score, int image, String piis) {
        this.piis = piis;
        this.name = name;
        this.package_name = package_name;
        this.score = score;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public int getScore() {
        return score;
    }

    public int getImage() {
        return image;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPiis() {
        return piis;
    }

    public void setPiis(String piis) {
        this.piis = piis;
    }
}
