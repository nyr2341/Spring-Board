package db;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCTests {
	Logger logger = LoggerFactory.getLogger(JDBCTests.class);
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConnection() {
		try(Connection con = 
				DriverManager.getConnection(
						"jdbc:oracle:thin:@localhost:1521:XE",
						"c##board_manager",
						"1234")){
			logger.info("testConnection - Get connection success!");
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
}
