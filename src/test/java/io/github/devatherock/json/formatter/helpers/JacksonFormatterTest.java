package io.github.devatherock.json.formatter.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.devatherock.json.formatter.testhelpers.TestUtil;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test for {@code JSONFormatter} with jackson
 * 
 * @author Devaprasadh Xavier
 *
 */
public class JacksonFormatterTest {
    private static final PrintStream OLD_STREAM = System.err;
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final StringBuilder PREVIOUS_LOG_LINES = new StringBuilder();
    private static final TypeReference<Map<String, Object>> TYPE_REF_MAP_STRING_OBJ = new TypeReference<Map<String, Object>>() {
    };

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
        Logger logger = Logger.getLogger(JacksonFormatterTest.class.getName());

        String message = null;
        logger.log(Level.INFO, message);

        String logLine = OUTPUT_STREAM.toString("UTF-8");
        String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
        Map<String, Object> jsonMap = new ObjectMapper().readValue(actualLogLine, TYPE_REF_MAP_STRING_OBJ);
        TestUtil.verifyJson(jsonMap, logger.getName(), message, Level.INFO.toString());

        PREVIOUS_LOG_LINES.append(actualLogLine);
    }

    /**
     * Tests logging a simple message
     * 
     * @throws IOException
     */
    @Test
    public void testSimpleMessage() throws IOException {
        Logger logger = Logger.getLogger(JacksonFormatterTest.class.getName());

        String message = "First message";
        logger.log(Level.INFO, message);

        String logLine = OUTPUT_STREAM.toString("UTF-8");
        String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
        Map<String, Object> jsonMap = new ObjectMapper().readValue(actualLogLine, TYPE_REF_MAP_STRING_OBJ);
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
        Logger logger = Logger.getLogger(JacksonFormatterTest.class.getName());

        String message = "Next message" + System.getProperty("line.separator");
        logger.log(Level.INFO, message);

        String logLine = OUTPUT_STREAM.toString("UTF-8");
        String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
        Map<String, Object> jsonMap = new ObjectMapper().readValue(actualLogLine, TYPE_REF_MAP_STRING_OBJ);
        TestUtil.verifyJson(jsonMap, logger.getName(), message, Level.INFO.toString());

        PREVIOUS_LOG_LINES.append(actualLogLine);
    }

    /**
     * Tests logging a message with exception stack trace for an exception created
     * without a message
     * 
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @Test
    public void testMessageWithExceptionWithoutExceptionMessage()
            throws JsonParseException, JsonMappingException, IOException {
        Logger logger = Logger.getLogger(JacksonFormatterTest.class.getName());
        Exception exception = new RuntimeException();

        String message = "Test message with exception";
        logger.log(Level.SEVERE, message, exception);

        String logLine = OUTPUT_STREAM.toString("UTF-8");
        String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
        Map<String, Object> jsonMap = new ObjectMapper().readValue(actualLogLine, TYPE_REF_MAP_STRING_OBJ);
        TestUtil.verifyJson(jsonMap, logger.getName(), message, Level.SEVERE.toString());
        TestUtil.verifyExceptionWithoutMessage(exception, jsonMap);

        PREVIOUS_LOG_LINES.append(actualLogLine);
    }

    /**
     * Tests logging a message with exception stack trace for an exception created
     * with a message
     * 
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @Test
    public void testMessageWithExceptionWithExceptionMessage()
            throws JsonParseException, JsonMappingException, IOException {
        Logger logger = Logger.getLogger(JacksonFormatterTest.class.getName());
        Exception exception = new RuntimeException("test exception");

        String message = "Test message with exception";
        logger.log(Level.SEVERE, message, exception);

        String logLine = OUTPUT_STREAM.toString("UTF-8");
        String actualLogLine = logLine.replace(PREVIOUS_LOG_LINES.toString(), "");
        Map<String, Object> jsonMap = new ObjectMapper().readValue(actualLogLine, TYPE_REF_MAP_STRING_OBJ);
        TestUtil.verifyJson(jsonMap, logger.getName(), message, Level.SEVERE.toString());
        TestUtil.verifyExceptionWithMessage(exception, jsonMap);

        PREVIOUS_LOG_LINES.append(actualLogLine);
    }
}
