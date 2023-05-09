package io.github.devatherock.json.formatter.helpers;

import java.util.Map;

import org.json.simple.JSONObject;

/**
 * JsonConverter that uses json-simple library for creating the JSON
 */
public class SimpleJsonConverter implements JsonConverter {
    @Override
    public String convertToJson(Map<String, Object> map) {
        return JSONObject.toJSONString(map) + System.lineSeparator();
    }

}
