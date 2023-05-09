package io.github.devatherock.json.formatter.helpers;

import java.util.Map;

import com.google.gson.Gson;

/**
 * A JsonConverter that uses GSON library for creating the JSON
 */
public class GsonJsonConverter implements JsonConverter {
    /**
     * JSON converter
     */
    private static final Gson GSON = new Gson();

    @Override
    public String convertToJson(Map<String, Object> map) {
        return GSON.toJson(map) + System.lineSeparator();
    }

}
