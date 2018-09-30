package com.devaprasadh.json.formatter.helpers;

import java.util.Map;

/**
 * Custom {@code JsonConverter} that forms a JSON using {@code StringBuilder}
 */
public class CustomJsonConverter implements JsonConverter {
	/**
	 * Double quote
	 */
	private static final String DOUBLE_QUOTE = "\"";

	/**
	 * Colon
	 */
	private static final String COLON = ":";

	/**
	 * Comma
	 */
	private static final String COMMA = ",";

	private static final String OBJ_START = "{";

	private static final String OBJ_END = "}";

	/**
	 * Array of characters to escape in the JSON
	 */
	private static final char[] CHARS_TO_ESCAPE = { '\n', '\r', '"' };
	/**
	 * Array of strings to escape within the JSON
	 */
	private static final String[] ESCAPE_CHARS = { "\\n", "\\r", "\\\"" };

	/**
	 * New line character
	 */
	private static final String LINE_SEP = System.getProperty("line.separator");

	@Override
	public String convertToJson(Map<String, Object> map) {
		StringBuilder log = new StringBuilder();
		log.append(OBJ_START);

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			appendJsonObject(entry.getKey(), entry.getValue(), log);
			log.append(COMMA);
		}

		// Replace last comma before the closing brace
		log.replace(log.length() - 1, log.length(), "");

		log.append(OBJ_END);
		log.append(LINE_SEP);

		return log.toString();
	}

	/**
	 * Appends the given key and value to the log
	 * 
	 * @param key
	 * @param value
	 * @param log
	 */
	private void appendJson(Object key, Object value, StringBuilder log) {
		log.append(DOUBLE_QUOTE);
		log.append(key);
		log.append(DOUBLE_QUOTE);
		log.append(COLON);

		if (value instanceof String) {
			log.append(DOUBLE_QUOTE);
			log.append(escapeJson((String) value));
			log.append(DOUBLE_QUOTE);
		} else {
			log.append(value);
		}
	}

	private void appendJsonObject(Object key, Object value, StringBuilder log) {
		if (value instanceof Map) {
			log.append(DOUBLE_QUOTE);
			log.append(key);
			log.append(DOUBLE_QUOTE);
			log.append(COLON);
			log.append(OBJ_START);

			Map<Object, Object> map = (Map<Object, Object>) value;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				appendJsonObject(entry.getKey(), entry.getValue(), log);
				log.append(COMMA);
			}

			// Replace last comma before the closing brace
			log.replace(log.length() - 1, log.length(), "");
			log.append(OBJ_END);
		} else {
			appendJson(key, value, log);
		}
	}

	/**
	 * Escapes specific characters in the message text
	 * 
	 * @param message
	 * @return
	 */
	private static String escapeJson(String message) {
		StringBuilder escapedText = new StringBuilder(message.length());
		boolean notEscaped = true;
		char currentChar;

		for (int index = 0; index < message.length(); index++) {
			notEscaped = true;
			currentChar = message.charAt(index);
			for (int escapeIndex = 0; escapeIndex < CHARS_TO_ESCAPE.length; escapeIndex++) {
				if (currentChar == CHARS_TO_ESCAPE[escapeIndex]) {
					escapedText.append(ESCAPE_CHARS[escapeIndex]);
					notEscaped = false;
					break;
				}
			}
			if (notEscaped) {
				if ((int) currentChar > 31) {
					escapedText.append(currentChar);
				}
				// Replace control characters with an empty space
				else {
					escapedText.append(' ');
				}
			}
		}
		return escapedText.toString();
	}

}