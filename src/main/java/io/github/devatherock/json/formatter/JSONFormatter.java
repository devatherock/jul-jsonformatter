package io.github.devatherock.json.formatter;

import static io.github.devatherock.json.formatter.helpers.Constants.KEY_LOGGER_CLASS;
import static io.github.devatherock.json.formatter.helpers.Constants.KEY_LOGGER_METHOD;
import static io.github.devatherock.json.formatter.helpers.Constants.KEY_LOGGER_NAME;
import static io.github.devatherock.json.formatter.helpers.Constants.KEY_LOG_LEVEL;
import static io.github.devatherock.json.formatter.helpers.Constants.KEY_MESSAGE;
import static io.github.devatherock.json.formatter.helpers.Constants.KEY_THREAD_NAME;
import static io.github.devatherock.json.formatter.helpers.Constants.KEY_TIMESTAMP;
import static io.github.devatherock.json.formatter.helpers.Constants.THREAD_NAME_CACHE_SIZE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Instant;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import io.github.devatherock.json.formatter.helpers.Constants;
import io.github.devatherock.json.formatter.helpers.CustomJsonConverter;
import io.github.devatherock.json.formatter.helpers.GsonJsonConverter;
import io.github.devatherock.json.formatter.helpers.JacksonJsonConverter;
import io.github.devatherock.json.formatter.helpers.JsonConverter;
import io.github.devatherock.json.formatter.helpers.SimpleJsonConverter;
import io.github.devatherock.json.formatter.helpers.Constants.ExceptionKeys;

/**
 * A {@code java.util.logging.Formatter} for java.util.logging that logs
 * messages as JSON
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

	public JSONFormatter() {
		configure();
	}

	/**
	 * Configure a {@link JSONFormatter} from LogManager properties.
	 */
	private void configure() {
		LogManager manager = LogManager.getLogManager();
		String cname = getClass().getName();
		String value = manager.getProperty(cname + ".key_timestamp");
		if (value != null) {
			Constants.KEY_TIMESTAMP = value;
		}
		value = manager.getProperty(cname + ".key_logger_name");
		if (value != null) {
			Constants.KEY_LOGGER_NAME = value;
		}
		value = manager.getProperty(cname + ".key_log_level");
		if (value != null) {
			Constants.KEY_LOG_LEVEL = value;
		}
		value = manager.getProperty(cname + ".key_thread_name");
		if (value != null) {
			Constants.KEY_THREAD_NAME = value;
		}
		value = manager.getProperty(cname + ".key_logger_class");
		if (value != null) {
			Constants.KEY_LOGGER_CLASS = value;
		}
		value = manager.getProperty(cname + ".key_logger_method");
		if (value != null) {
			Constants.KEY_LOGGER_METHOD = value;
		}
		value = manager.getProperty(cname + ".key_message");
		if (value != null) {
			Constants.KEY_MESSAGE = value;
		}
		value = manager.getProperty(cname + ".key_exception");
		if (value != null) {
			Constants.KEY_EXCEPTION = value;
		}
	}

	@Override
	public String format(LogRecord record) {
		Map<String, Object> object = new LinkedHashMap<>();
		object.put(KEY_TIMESTAMP, Constants.ISO_8601_FORMAT.format(Instant.ofEpochMilli(record.getMillis())));
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
			exceptionInfo.put(ExceptionKeys.stack_trace, sw.toString());
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
					jsonConverter = new CustomJsonConverter();
				}
			}
		}

		return jsonConverter;
	}
}
