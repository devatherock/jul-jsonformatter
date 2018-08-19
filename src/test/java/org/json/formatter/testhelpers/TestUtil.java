package org.json.formatter.testhelpers;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.StopWatch;
import org.json.formatter.helpers.Constants;
import org.junit.Assert;

import com.google.gson.Gson;

/**
 * Helper class for testing log formatting
 * 
 * @author Devaprasadh Xavier
 *
 */
public class TestUtil {
	/**
	 * Logs a set of messages using the logger that is provided as input
	 * 
	 * @param logger
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void logMessages(Logger logger) throws InterruptedException, ExecutionException {
		final PipedOutputStream pipedOut = new PipedOutputStream();
		System.setErr(new PrintStream(pipedOut));

		// Assert the responses in a separate thread
		ExecutorService assertionExecutor = Executors.newSingleThreadExecutor();
		Future<Void> assertionFuture = assertionExecutor.submit(new Callable<Void>() {
			public Void call() throws IOException {
				try (PipedInputStream pipedIn = new PipedInputStream(pipedOut); Scanner scan = new Scanner(pipedIn);) {
					Gson gson = new Gson();
					Pattern timestampPattern = Pattern.compile(
							"[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}-[0-9]{2}:[0-5]{1}[0-9]{1}");

					while (scan.hasNextLine()) {
						String text = scan.nextLine();
						Map<String, Object> jsonMap = gson.fromJson(text, Map.class);
						System.out.println(text);
						Assert.assertNotNull(jsonMap.get(Constants.KEY_TIMESTAMP));
						Assert.assertTrue(
								timestampPattern.matcher(jsonMap.get(Constants.KEY_TIMESTAMP).toString()).matches());
						Assert.assertNotNull(jsonMap.get(Constants.KEY_LOGGER_NAME));
						Assert.assertNotNull(jsonMap.get(Constants.KEY_LOG_LEVEL));
						Assert.assertNotNull(jsonMap.get(Constants.KEY_THREAD_NAME));
						Assert.assertNotNull(jsonMap.get(Constants.KEY_MESSAGE));
					}
					return null;
				}
			}
		});

		// Simple message
		StopWatch watch = new StopWatch();
		watch.start();
		logger.log(Level.INFO, "First message");
		watch.stop();
		logger.log(Level.INFO, String.valueOf(watch.getTime()));

		// Message with new line
		watch.reset();
		watch.start();
		logger.log(Level.INFO, "Next message" + System.getProperty("line.separator"));
		watch.stop();
		logger.log(Level.INFO, String.valueOf(watch.getTime()));

		// Message with exception object without exception message
		watch.reset();
		watch.start();
		logger.log(Level.SEVERE, "Test message with exception", new RuntimeException());
		watch.stop();
		logger.log(Level.INFO, String.valueOf(watch.getTime()));

		// Message with exception object with exception message
		watch.reset();
		watch.start();
		logger.log(Level.SEVERE, "Test message with exception", new RuntimeException("test exception"));
		watch.stop();
		logger.log(Level.INFO, String.valueOf(watch.getTime()));

		logger.log(Level.INFO, "Maximum memory: " + Runtime.getRuntime().maxMemory());
		logger.log(Level.INFO, "Total memory: " + Runtime.getRuntime().totalMemory());
		logger.log(Level.INFO, "Free memory: " + Runtime.getRuntime().freeMemory());

		// To performance test if needed
		// Growable number of maximum threads
		int numberOfThreads = 1; // Number of threads that will write log messages
		ExecutorService testExecutor = Executors.newCachedThreadPool();
		List<Future<?>> loggingResults = new ArrayList<>((int) (1 + (numberOfThreads / 0.75)));

		watch.reset();
		watch.start();
		for (int index = 0; index < numberOfThreads; index++) {
			loggingResults.add(testExecutor.submit(new Runnable() {
				public void run() {
					Logger logger = Logger.getLogger(Thread.currentThread().getName());
					StopWatch watch = new StopWatch();
					watch.start();
					logger.log(Level.INFO, "growable threadpool message");
					watch.stop();
					logger.log(Level.INFO, String.valueOf(watch.getTime()));
					watch.reset();
					watch.start();
					logger.log(Level.SEVERE, "growable threadpool exception message",
							new RuntimeException("growable threadpool exception"));
					watch.stop();
					logger.log(Level.INFO, String.valueOf(watch.getTime()));
				}
			}));
		}

		for (Future<?> task : loggingResults) {
			Assert.assertNull(task.get());
		}
		watch.stop();
		logger.log(Level.INFO, "growable threadpool time: " + String.valueOf(watch.getTime()));
		testExecutor.shutdown();

		// Fixed number of maximum threads
		testExecutor = Executors.newFixedThreadPool(200);
		loggingResults.clear();

		watch.reset();
		watch.start();
		for (int index = 0; index < numberOfThreads; index++) {
			loggingResults.add(testExecutor.submit(new Runnable() {
				public void run() {
					Logger logger = Logger.getLogger(Thread.currentThread().getName());
					StopWatch watch = new StopWatch();
					watch.start();
					logger.log(Level.INFO, "fixed threadpool message");
					watch.stop();
					logger.log(Level.INFO, String.valueOf(watch.getTime()));
					watch.reset();
					watch.start();
					logger.log(Level.SEVERE, "fixed threadpool exception message",
							new RuntimeException("fixed threadpool exception"));
					watch.stop();
					logger.log(Level.INFO, String.valueOf(watch.getTime()));
				}
			}));
		}

		for (Future<?> task : loggingResults) {
			Assert.assertNull(task.get());
		}
		watch.stop();
		logger.log(Level.INFO, "fixed threadpool time: " + String.valueOf(watch.getTime()));
		logger.log(Level.INFO, "Maximum memory: " + Runtime.getRuntime().maxMemory());
		logger.log(Level.INFO, "Total memory: " + Runtime.getRuntime().totalMemory());
		logger.log(Level.INFO, "Free memory: " + Runtime.getRuntime().freeMemory());
		testExecutor.shutdown();
		try {
			pipedOut.close();
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}

		// Check that no exceptions were thrown in the assertion thread
		Assert.assertNull(assertionFuture.get());
		assertionExecutor.shutdown();
	}
}
