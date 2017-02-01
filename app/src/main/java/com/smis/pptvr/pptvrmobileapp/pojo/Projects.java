package com.smis.pptvr.pptvrmobileapp.pojo;

/**
 * Created by Vikas Kumar on 01-02-2017.
 */


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Projects {

    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("projectname")
    @Expose
    private String projectname;
    @SerializedName("scenes")
    @Expose
    private List<Scene> scenes = null;
    @SerializedName("starredby")
    @Expose
    private List<Object> starredby = null;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("stars")
    @Expose
    private Integer stars;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("belongsto")
    @Expose
    private String belongsto;

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }

    public List<Object> getStarredby() {
        return starredby;
    }

    public void setStarredby(List<Object> starredby) {
        this.starredby = starredby;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBelongsto() {
        return belongsto;
    }

    public void setBelongsto(String belongsto) {
        this.belongsto = belongsto;
    }

}