package com.purutal.dateconverteractivity.JsonUtils;

import com.purutal.dateconverteractivity.DateConverter.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class JsonConverter {
    static DecimalFormat format = new DecimalFormat("00");

    public static String toJSon(List<Model> models) {
        try {
            // Here we convert Java Object to JSON
            JSONObject mainObj = new JSONObject();

            JSONArray jsonArr = new JSONArray();

            for (Model model : models) {
                String date = model.getYear() + "-" + format.format(model.getMonth()) + "-" + format.format(model.getDay());

                JSONObject enObj = new JSONObject();
                JSONObject pnObj = new JSONObject();

                pnObj.put("year", model.getYear());
                pnObj.put("month", model.getMonth());
                pnObj.put("dayOfMonth", model.getDay());
                pnObj.put("dayOfWeek", model.getDayOfWeek());
                pnObj.put("nepDate", date);
                pnObj.put("engDate", model.getEngDate());

                enObj.put(model.getEngDate(), pnObj);
                jsonArr.put(enObj);
            }

            mainObj.put(String.valueOf(models.get(0).getYear()), jsonArr);

            return mainObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;

    }
}
