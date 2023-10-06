package com.qupris;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class AppModel {
    String name;
    String package_name;
    int version;
    String piis;
    int score;
    Drawable image;
    boolean isAnalyzed;
    boolean isSystemApp;

    public AppModel(String name, String package_name, int version, int score, Drawable image, String piis, boolean isAnalyzed, boolean isSystemApp) {
        this.piis = piis;
        this.name = name;
        this.package_name = package_name;
        this.version = version;
        this.score = score;
        this.image = image;
        this.isAnalyzed = isAnalyzed;
        this.isSystemApp = isSystemApp;
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

    public Drawable getImage() {
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

    public int getVersion() {
        return version;
    }

    public boolean isAnalyzed() {
        return isAnalyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        isAnalyzed = analyzed;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }
}
