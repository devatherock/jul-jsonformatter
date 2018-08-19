package com.github.devaprasadh.json.formatter;

import static com.github.devaprasadh.json.formatter.helpers.Constants.KEY_LOGGER_CLASS;
import static com.github.devaprasadh.json.formatter.helpers.Constants.KEY_LOGGER_METHOD;
import static com.github.devaprasadh.json.formatter.helpers.Constants.KEY_LOGGER_NAME;
import static com.github.devaprasadh.json.formatter.helpers.Constants.KEY_LOG_LEVEL;
import static com.github.devaprasadh.json.formatter.helpers.Constants.KEY_MESSAGE;
import static com.github.devaprasadh.json.formatter.helpers.Constants.KEY_THREAD_NAME;
import static com.github.devaprasadh.json.formatter.helpers.Constants.KEY_TIMESTAMP;
import static com.github.devaprasadh.json.formatter.helpers.Constants.THREAD_NAME_CACHE_SIZE;
import static com.github.devaprasadh.json.formatter.helpers.Constants.TIME_FORMAT;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.github.devaprasadh.json.formatter.helpers.Constants.ExceptionKeys;
import com.github.devaprasadh.json.formatter.helpers.GsonJsonConverter;
import com.github.devaprasadh.json.formatter.helpers.JacksonJsonConverter;
import com.github.devaprasadh.json.formatter.helpers.JsonConverter;
import com.github.devaprasadh.json.formatter.helpers.SimpleJsonConverter;

/**
 * A JSONFormatter for java.util.logging logs
 */
public class JSONFormatter extends Formatter {
	/**
	 * JSON converter
	 */
	private static final JsonConverter CONVERTER = createJsonConverter();

	private static final ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();

	/**
	 * Cache of thread names
	 */
	private static final Map<Integer, String> THREAD_NAME_CACHE = new LinkedHashMap<Integer, String>() {

		private static final long serialVersionUID = 1L;

		@Override
		protected boolean removeEldestEntry(Entry<Integer, String> eldest) {
			return (size() > THREAD_NAME_CACHE_SIZE);
		}
	};

	@Override
	public String format(LogRecord record) {
		Map<String, Object> object = new LinkedHashMap<>();
		object.put(KEY_TIMESTAMP, TIME_FORMAT.format(record.getMillis()));
		object.put(KEY_LOGGER_NAME, record.getLoggerName());
		object.put(KEY_LOG_LEVEL, record.getLevel().getName());
		object.put(KEY_THREAD_NAME, getThreadName(record.getThreadID()));

		if (null != record.getSourceClassName()) {
			object.put(KEY_LOGGER_CLASS, record.getSourceClassName());
		}

		if (null != record.getSourceMethodName()) {
			object.put(KEY_LOGGER_METHOD, record.getSourceMethodName());
		}
		object.put(KEY_MESSAGE, formatMessage(record));

		// Used an enum map for lighter memory consumption
		if (null != record.getThrown()) {
			Map<ExceptionKeys, Object> exceptionInfo = new EnumMap<>(ExceptionKeys.class);
			exceptionInfo.put(ExceptionKeys.exception_class, record.getThrown().getClass().getName());

			if (record.getThrown().getMessage() != null) {
				exceptionInfo.put(ExceptionKeys.exception_message, record.getThrown().getMessage());
			}

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			record.getThrown().printStackTrace(pw);
			pw.close();
			exceptionInfo.put(ExceptionKeys.stacktrace, sw);
			object.put("exception", exceptionInfo);
		}

		return CONVERTER.convertToJson(object);
	}

	/**
	 * Gets the thread name from the threadId present in the logRecord.
	 * 
	 * @param logRecordThreadId
	 * @return thread name
	 */
	private static String getThreadName(int logRecordThreadId) {
		String result = THREAD_NAME_CACHE.get(logRecordThreadId);

		if (result != null) {
			return result;
		}

		if (logRecordThreadId > Integer.MAX_VALUE / 2) {
			result = String.valueOf(logRecordThreadId);
		} else {
			ThreadInfo threadInfo = THREAD_MX_BEAN.getThreadInfo(logRecordThreadId);
			if (threadInfo == null) {
				return String.valueOf(logRecordThreadId);
			}
			result = threadInfo.getThreadName();
		}

		synchronized (THREAD_NAME_CACHE) {
			THREAD_NAME_CACHE.put(logRecordThreadId, result);
		}

		return result;
	}

	private static JsonConverter createJsonConverter() {
		JsonConverter jsonConverter = null;

		try {
			Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
			jsonConverter = new JacksonJsonConverter();
		} catch (ClassNotFoundException e) {
			try {
				Class.forName("com.google.gson.Gson");
				jsonConverter = new GsonJsonConverter();
			} catch (ClassNotFoundException e1) {
				try {
					Class.forName("org.json.simple.JSONObject");
					jsonConverter = new SimpleJsonConverter();
				} catch (ClassNotFoundException e2) {
					Logger.getAnonymousLogger().log(Level.WARNING,
							"None of GSON/Jackson/json-simple found in classpath");
				}
			}
		}

		return jsonConverter;
	}
}
