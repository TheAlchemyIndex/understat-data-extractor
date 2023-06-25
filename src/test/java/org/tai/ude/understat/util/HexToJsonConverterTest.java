package org.tai.ude.understat.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HexToJsonConverterTest {

    @Test
    public void givenValidHexString_toJsonObject_thenReturnValidJsonObject() {
        String hexString = "\\x7B\\x22key\\x22\\x3A\\x22value\\x22\\x7D";
        String expectedJsonString = "{\"key\":\"value\"}";

        JSONObject jsonObject = HexToJsonConverter.toJsonObject(hexString);
        assertEquals(expectedJsonString, jsonObject.toString());
    }

    @Test
    public void givenValidHexString_toJsonArray_thenReturnValidJsonArray() {
        String hexString = "\\x5B\\x22value1\\x22\\x2C\\x22value2\\x22\\x5D";
        String expectedJsonString = "[\"value1\",\"value2\"]";

        JSONArray jsonArray = HexToJsonConverter.toJsonArray(hexString);
        assertEquals(expectedJsonString, jsonArray.toString());
    }
}
