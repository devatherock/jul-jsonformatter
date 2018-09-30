package com.github.devaprasadh.json.formatter;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.devaprasadh.json.formatter.helpers.Constants;
import com.github.devaprasadh.json.formatter.testhelpers.TestUtil;

/**
 * Unit test for {@code com.github.devaprasadh.json.formatter.JSONFormatter}
 * with custom JSON converter
 * 
 * @author Devaprasadh Xavier
 *
 */
public class CustomJsonConverterTest {
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
		Logger logger = Logger.getLogger(CustomJsonConverterTest.class.getName());

		String message = "First message";
		logger.log(Level.INFO, message);

		String logLine = OUTPUT_STREAM.toString("UTF-8");
		String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
		assertTrue(actualLogLine.contains(Constants.KEY_TIMESTAMP));
		assertTrue(actualLogLine.contains(Constants.KEY_LOGGER_NAME));
		assertTrue(actualLogLine.contains(Constants.KEY_LOG_LEVEL));
		assertTrue(actualLogLine.contains(Constants.KEY_THREAD_NAME));
		assertTrue(actualLogLine.contains(Constants.KEY_LOGGER_CLASS));
		assertTrue(actualLogLine.contains(Constants.KEY_LOGGER_METHOD));
		assertTrue(actualLogLine.contains(Constants.KEY_MESSAGE));
		assertTrue(actualLogLine.contains("INFO"));
		assertTrue(actualLogLine.contains("com.github.devaprasadh.json.formatter.CustomJsonConverterTest"));
		assertTrue(actualLogLine.contains("testSimpleMessage"));
		assertTrue(actualLogLine.contains(message));

		PREVIOUS_LOG_LINES.append(actualLogLine);
	}

	/**
	 * Tests logging a message with new line character
	 * 
	 * @throws IOException
	 */
	@Test
	public void testMessageWithNewLine() throws IOException {
		Logger logger = Logger.getLogger(CustomJsonConverterTest.class.getName());

		String message = "Next message" + System.getProperty("line.separator");
		logger.log(Level.INFO, message);

		String logLine = OUTPUT_STREAM.toString("UTF-8");
		String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
		assertTrue(actualLogLine.contains(Constants.KEY_TIMESTAMP));
		assertTrue(actualLogLine.contains(Constants.KEY_LOGGER_NAME));
		assertTrue(actualLogLine.contains(Constants.KEY_LOG_LEVEL));
		assertTrue(actualLogLine.contains(Constants.KEY_THREAD_NAME));
		assertTrue(actualLogLine.contains(Constants.KEY_LOGGER_CLASS));
		assertTrue(actualLogLine.contains(Constants.KEY_LOGGER_METHOD));
		assertTrue(actualLogLine.contains(Constants.KEY_MESSAGE));
		assertTrue(actualLogLine.contains("INFO"));
		assertTrue(actualLogLine.contains("com.github.devaprasadh.json.formatter.CustomJsonConverterTest"));
		assertTrue(actualLogLine.contains("testMessageWithNewLine"));
		assertTrue(actualLogLine.contains("Next message\\n"));

		PREVIOUS_LOG_LINES.append(actualLogLine);
	}
}
