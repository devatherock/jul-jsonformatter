package com.github.devaprasadh.json.formatter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.devaprasadh.json.formatter.testhelpers.TestUtil;
import com.google.gson.Gson;

/**
 * Unit test for {@code com.github.devaprasadh.json.formatter.JSONFormatter}
 * with gson
 * 
 * @author Devaprasadh Xavier
 *
 */
public class GSONFormatterTest {
	private static final PrintStream OLD_STREAM = System.err;
	private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
	private static final StringBuilder PREVIOUS_LOG_LINES = new StringBuilder();
	
	static {
		TestUtil.loadLoggingConfig();
	}

	@AfterClass
	public static void tearDown() {
		System.setErr(OLD_STREAM);
	}

	@BeforeClass
	public static void captureConsole() {
		System.setErr(new PrintStream(OUTPUT_STREAM));
	}

	/**
	 * Tests logging a simple message
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSimpleMessage() throws IOException {
		Logger logger = Logger.getLogger(GSONFormatterTest.class.getName());

		String message = "First message";
		logger.log(Level.INFO, message);

		String logLine = OUTPUT_STREAM.toString("UTF-8");
		String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
		Map<String, Object> jsonMap = (Map<String, Object>) new Gson().fromJson(actualLogLine, Map.class);
		TestUtil.verifyJson(jsonMap, logger.getName(), message, Level.INFO.toString());

		PREVIOUS_LOG_LINES.append(actualLogLine);
	}

	/**
	 * Tests logging a message with new line character
	 * 
	 * @throws IOException
	 */
	@Test
	public void testMessageWithNewLine() throws IOException {
		Logger logger = Logger.getLogger(GSONFormatterTest.class.getName());

		String message = "Next message" + System.getProperty("line.separator");
		logger.log(Level.INFO, message);

		String logLine = OUTPUT_STREAM.toString("UTF-8");
		String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
		Map<String, Object> jsonMap = (Map<String, Object>) new Gson().fromJson(actualLogLine, Map.class);
		TestUtil.verifyJson(jsonMap, logger.getName(), message, Level.INFO.toString());

		PREVIOUS_LOG_LINES.append(actualLogLine);
	}
}
