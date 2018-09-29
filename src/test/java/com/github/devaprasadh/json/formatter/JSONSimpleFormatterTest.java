package com.github.devaprasadh.json.formatter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.devaprasadh.json.formatter.testhelpers.TestUtil;

/**
 * Unit test for {@code com.github.devaprasadh.json.formatter.JSONFormatter}
 * with json-simple
 * 
 * @author Devaprasadh Xavier
 *
 */
public class JSONSimpleFormatterTest {
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
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void testSimpleMessage() throws ParseException, IOException {
		Logger logger = Logger.getLogger(JSONSimpleFormatterTest.class.getName());

		String message = "First message";
		logger.log(Level.INFO, message);

		String logLine = OUTPUT_STREAM.toString("UTF-8");
		String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
		Map<String, Object> jsonMap = (Map<String, Object>) new JSONParser().parse(actualLogLine);
		TestUtil.verifyJson(jsonMap, logger.getName(), message, Level.INFO.toString());

		PREVIOUS_LOG_LINES.append(actualLogLine);
	}

	/**
	 * Tests logging a message with new line character
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void testMessageWithNewLine() throws ParseException, IOException {
		Logger logger = Logger.getLogger(JSONSimpleFormatterTest.class.getName());

		String message = "Next message" + System.getProperty("line.separator");
		logger.log(Level.INFO, message);

		String logLine = OUTPUT_STREAM.toString("UTF-8");
		String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
		Map<String, Object> jsonMap = (Map<String, Object>) new JSONParser().parse(actualLogLine);
		TestUtil.verifyJson(jsonMap, logger.getName(), message, Level.INFO.toString());

		PREVIOUS_LOG_LINES.append(actualLogLine);
	}
}
