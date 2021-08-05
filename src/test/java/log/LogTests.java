package log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogTests {
	@Test
	public void logbackTest() {
		Logger logger = LoggerFactory.getLogger(LogTests.class);
		logger.info("Example log from {}", LogTests.class.getSimpleName());
	}
}