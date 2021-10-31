package security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {config.RootConfig.class,config.SecurityConfig.class})
public class PasswordEncoderTests {
	Logger logger = LoggerFactory.getLogger(PasswordEncoderTests.class);
	
	@Setter(onMethod_= {@Autowired})
	private PasswordEncoder pwEncoder;
	
	@Test
	public void testEncoder() {
		String str = "admin";
		String enStr = pwEncoder.encode(str);
		logger.info(enStr);
	}
}
