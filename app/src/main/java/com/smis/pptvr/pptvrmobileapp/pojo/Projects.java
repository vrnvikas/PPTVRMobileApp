package com.smis.pptvr.pptvrmobileapp.pojo;

/**
 * Created by Vikas Kumar on 01-02-2017.
 */


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Projects {

    @SerializedName("__v")
    @Expose
    private int v;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("projectname")
    @Expose
    private String projectname;
    @SerializedName("stars")
    @Expose
    private int stars;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("views")
    @Expose
    private int views;
    @SerializedName("assetBgImages")
    @Expose
    private List<String> assetBgImages = null;
    @SerializedName("assetVideos")
    @Expose
    private List<Object> assetVideos = null;
    @SerializedName("assetImages")
    @Expose
    private List<Object> assetImages = null;
    @SerializedName("starredby")
    @Expose
    private List<String> starredby = null;
    @SerializedName("scenes")
    @Expose
    private List<Scene> scenes = null;

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<String> getAssetBgImages() {
        return assetBgImages;
    }

    public void setAssetBgImages(List<String> assetBgImages) {
        this.assetBgImages = assetBgImages;
    }

    public List<Object> getAssetVideos() {
        return assetVideos;
    }

    public void setAssetVideos(List<Object> assetVideos) {
        this.assetVideos = assetVideos;
    }

    public List<Object> getAssetImages() {
        return assetImages;
    }

    public void setAssetImages(List<Object> assetImages) {
        this.assetImages = assetImages;
    }

    public List<String> getStarredby() {
        return starredby;
    }

    public void setStarredby(List<String> starredby) {
        this.starredby = starredby;
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }

}