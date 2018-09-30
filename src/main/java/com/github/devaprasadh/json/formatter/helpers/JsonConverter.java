package com.github.devaprasadh.json.formatter.helpers;

import java.util.Map;

/**
 * Interface to convert objects to JSON
 *
 * @author devaprasadh
 *
 */
public interface JsonConverter {
	String convertToJson(Map<String, Object> map);
}
