package org.tai.ude.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    private final String jsonString;

    public JsonParser(String jsonString) {
        this.jsonString = jsonString;
    }

    public JSONObject parseJsonObject() throws JSONException {
        if (this.jsonString == null) {
            throw new JSONException("Trying to parse null value to JSONObject");
        } else {
            return new JSONObject(this.jsonString);
        }
    }

    public JSONArray parseJsonArray() throws JSONException {
        if (this.jsonString == null) {
            throw new JSONException("Trying to parse null value to JSONArray");
        } else {
            return new JSONArray(this.jsonString);
        }
    }
}
