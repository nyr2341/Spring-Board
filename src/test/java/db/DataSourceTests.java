package db;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.RootConfig;
import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class})
public class DataSourceTests {
	Logger logger = LoggerFactory.getLogger(JDBCTests.class);

	@Setter(onMethod_={@Autowired})
	private DataSource dataSource;
	
	@Setter(onMethod_= {@Autowired})
	private SqlSessionFactory sqlSessionFactory;

	@Test
	public void testDataSourceConnection() {
		System.out.println(dataSource);
		try(Connection con = dataSource.getConnection()){
			logger.info("testDataSourceConnection - Get connection success");
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testMybatis() {
		try(SqlSession session = sqlSessionFactory.openSession();
			Connection con = session.getConnection();){
			logger.info("testMybatis - Get connection success");
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
}
