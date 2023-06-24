package org.tai.ude.understat.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class HexToJsonConverter {

    public static JSONObject toJsonObject(String hexString) {
        String jsonString = hexString.replaceAll("\\\\x", "%");
        jsonString = java.net.URLDecoder.decode(jsonString, StandardCharsets.UTF_8);
        return new JSONObject(jsonString);
    }

    public static JSONArray toJsonArray(String hexString) {
        String jsonString = hexString.replaceAll("\\\\x", "%");
        jsonString = java.net.URLDecoder.decode(jsonString, StandardCharsets.UTF_8);
        return new JSONArray(jsonString);
    }
}
