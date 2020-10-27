package io.github.devatherock.json.formatter.helpers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.devatherock.json.formatter.helpers.Constants.ExceptionKeys;
import io.github.devatherock.json.formatter.testhelpers.TestUtil;

/**
 * Unit test for {@code JSONFormatter} with custom JSON converter
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
	 * Tests logging a null message
	 * 
	 * @throws IOException
	 */
	@Test
	public void testNullMessage() throws IOException {
		Logger logger = Logger.getLogger(CustomJsonConverterTest.class.getName());

		String message = null;
		logger.log(Level.INFO, message);

		String logLine = OUTPUT_STREAM.toString("UTF-8");
		String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
		verifyJson(logger, "null", actualLogLine, "testNullMessage", "INFO");

		PREVIOUS_LOG_LINES.append(actualLogLine);
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
		verifyJson(logger, message, actualLogLine, "testSimpleMessage", "INFO");

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
		verifyJson(logger, "Next message\\n", actualLogLine, "testMessageWithNewLine", "INFO");

		PREVIOUS_LOG_LINES.append(actualLogLine);
	}

	/**
	 * Tests logging a message with exception stack trace for an exception created
	 * without a message
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testMessageWithExceptionWithoutExceptionMessage() throws UnsupportedEncodingException {
		Logger logger = Logger.getLogger(CustomJsonConverterTest.class.getName());
		Exception exception = new RuntimeException();

		String message = "Test message with exception";
		logger.log(Level.SEVERE, message, exception);

		String logLine = OUTPUT_STREAM.toString("UTF-8");
		String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
		verifyJson(logger, message, actualLogLine, "testMessageWithExceptionWithoutExceptionMessage", "SEVERE");
		assertTrue(actualLogLine.contains(Constants.KEY_EXCEPTION));
		assertTrue(actualLogLine.contains(ExceptionKeys.exception_class.name()));
		assertFalse(actualLogLine.contains(ExceptionKeys.exception_message.name()));
		assertTrue(actualLogLine.contains(exception.getClass().getName()));
		assertTrue(actualLogLine.contains(ExceptionKeys.stack_trace.name()));

		PREVIOUS_LOG_LINES.append(actualLogLine);
	}

	/**
	 * Tests logging a message with exception stack trace for an exception created
	 * with a message
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testMessageWithExceptionWithExceptionMessage() throws UnsupportedEncodingException {
		Logger logger = Logger.getLogger(CustomJsonConverterTest.class.getName());
		Exception exception = new RuntimeException("test exception");

		String message = "Test message with exception";
		logger.log(Level.SEVERE, message, exception);

		String logLine = OUTPUT_STREAM.toString("UTF-8");
		String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
		verifyJson(logger, message, actualLogLine, "testMessageWithExceptionWithExceptionMessage", "SEVERE");
		assertTrue(actualLogLine.contains(Constants.KEY_EXCEPTION));
		assertTrue(actualLogLine.contains(ExceptionKeys.exception_class.name()));
		assertTrue(actualLogLine.contains(ExceptionKeys.exception_message.name()));
		assertTrue(actualLogLine.contains(exception.getClass().getName()));
		assertTrue(actualLogLine.contains(exception.getMessage()));
		assertTrue(actualLogLine.contains(ExceptionKeys.stack_trace.name()));

		PREVIOUS_LOG_LINES.append(actualLogLine);
	}

	/**
	 * Verifies the logged JSON message
	 * 
	 * @param logger
	 * @param message
	 * @param actualLogLine
	 * @param methodName
	 * @param logLevel
	 */
	private void verifyJson(Logger logger, String message, String actualLogLine, String methodName, String logLevel) {
		assertTrue(actualLogLine.contains(new Constants().KEY_TIMESTAMP));
		assertTrue(actualLogLine.contains(Constants.KEY_LOGGER_NAME));
		assertTrue(actualLogLine.contains(Constants.KEY_LOG_LEVEL));
		assertTrue(actualLogLine.contains(Constants.KEY_THREAD_NAME));
		assertTrue(actualLogLine.contains(Constants.KEY_LOGGER_CLASS));
		assertTrue(actualLogLine.contains(Constants.KEY_LOGGER_METHOD));
		assertTrue(actualLogLine.contains(Constants.KEY_MESSAGE));
		assertTrue(actualLogLine.contains(logLevel));
		assertTrue(actualLogLine.contains(logger.getName()));
		assertTrue(actualLogLine.contains(methodName));
		assertTrue(actualLogLine.contains(message));
	}
}
