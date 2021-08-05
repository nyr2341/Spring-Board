package mapper;
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
@ContextConfiguration(classes= {RootConfig.class})
public class MapperTests {
	Logger logger = LoggerFactory.getLogger(MapperTests.class);
	
	@Setter(onMethod_= {@Autowired})
	private TestMapper testMapper;
	
	@Test
	public void testMapper() {
		logger.info(testMapper.getTime2());
		logger.info(testMapper.getTime());
	}
}
