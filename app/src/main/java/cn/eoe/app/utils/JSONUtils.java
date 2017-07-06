package cn.eoe.app.utils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.eoe.app.entity.Databean;



public class JSONUtils {    //对JSON内容解析

    public static Databean databean;
    public static Databean parseJson(String jsonData) {
        try {
            JSONObject object = new JSONObject(jsonData);
            JSONObject object1 = object.getJSONArray("results").getJSONObject(0);
            JSONObject object2= object1.getJSONObject("now");
                 databean =new Databean();
                 databean.setText(object2.getString("text").toString());
                 databean.setCode(object2.getString("code").toString());
                 databean.setTemperature(object2.getString("temperature").toString());
                 databean. setWind_scale(object2.getString("wind_scale").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return databean;
        }
}


