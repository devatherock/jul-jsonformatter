package org.json.formatter.simple;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.json.formatter.testhelpers.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * Unit test for org.json.formatter.simple.JSONFormatter
 * 
 * @author Devaprasadh Xavier
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class JSONSimpleFormatterTest {
	static {
		try (InputStream inputStream = JSONSimpleFormatterTest.class.getClassLoader()
				.getResourceAsStream("logging-simple.properties");) {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (IOException e) {
			Logger.getAnonymousLogger().severe("Could not load logging-simple.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
	}

	/**
	 * Test log messages
	 * 
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@Test
	public void testFormat() throws InterruptedException, ExecutionException {
		Logger logger = Logger.getLogger(JSONSimpleFormatterTest.class.getName());

		TestUtil.logMessages(logger);
	}
}
