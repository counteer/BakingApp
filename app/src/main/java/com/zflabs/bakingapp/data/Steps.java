package com.zflabs.bakingapp.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Steps {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbNailURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbNailURL() {
        return thumbNailURL;
    }

    public void setThumbNailURL(String thumbNailURL) {
        this.thumbNailURL = thumbNailURL;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("shortDescription", shortDescription);
            jsonObject.put("description", description);
            jsonObject.put("videoURL", videoURL);
            jsonObject.put("thumbNailURL", thumbNailURL);
            return jsonObject;
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

}
