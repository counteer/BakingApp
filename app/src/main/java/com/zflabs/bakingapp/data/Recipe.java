package com.zflabs.bakingapp.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Recipe {

    private String name;
    private int id;

    private Steps[] steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Steps[] getSteps() {
        return steps;
    }

    public void setSteps(Steps[] steps) {
        this.steps = steps;
    }

    public String   toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            JSONArray stepsJson = new JSONArray();
            for(int i = 0; i < steps.length; ++i){
                JSONObject actualStep = steps[i].toJSON();
                stepsJson.put(i, actualStep);
            }
            jsonObject.put("steps", stepsJson);
            return jsonObject.toString();
        } catch (JSONException e) {
            return "";
        }
    }

}
