package com.smis.pptvr.pptvrmobileapp.pojo;

/**
 * Created by Vikas Kumar on 01-02-2017.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scene {

    @SerializedName("videos")
    @Expose
    private List<Object> videos = null;
    @SerializedName("popups")
    @Expose
    private List<Object> popups = null;
    @SerializedName("bgimage")
    @Expose
    private String bgimage;
    @SerializedName("sceneid")
    @Expose
    private String sceneid;
    @SerializedName("switchbuttons")
    @Expose
    private List<Object> switchbuttons = null;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("images")
    @Expose
    private List<Object> images = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public List<Object> getVideos() {
        return videos;
    }

    public void setVideos(List<Object> videos) {
        this.videos = videos;
    }

    public List<Object> getPopups() {
        return popups;
    }

    public void setPopups(List<Object> popups) {
        this.popups = popups;
    }

    public String getBgimage() {
        return bgimage;
    }

    public void setBgimage(String bgimage) {
        this.bgimage = bgimage;
    }

    public String getSceneid() {
        return sceneid;
    }

    public void setSceneid(String sceneid) {
        this.sceneid = sceneid;
    }

    public List<Object> getSwitchbuttons() {
        return switchbuttons;
    }

    public void setSwitchbuttons(List<Object> switchbuttons) {
        this.switchbuttons = switchbuttons;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
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

}

