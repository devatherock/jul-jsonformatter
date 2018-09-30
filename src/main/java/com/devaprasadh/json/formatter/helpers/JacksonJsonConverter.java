package com.devaprasadh.json.formatter.helpers;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A JsonConverter that uses Jackson library for creating the JSON
 */
public class JacksonJsonConverter implements JsonConverter {
	/**
	 * JSON converter
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public String convertToJson(Map<String, Object> map) {
		try {
			return MAPPER.writeValueAsString(map) + System.lineSeparator();
		} catch (JsonProcessingException e) {
			return map.toString();
		}
	}

}
