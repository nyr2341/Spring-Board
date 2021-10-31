package secutiry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import domain.UserVO;
import lombok.Setter;
import mapper.UserMapper;
import security.domain.CustomUser;

public class CustomUserDetailsService implements UserDetailsService {
	Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Setter(onMethod_= {@Autowired})
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserVO vo = userMapper.read(userId);
		return vo == null? null : new CustomUser(vo);
	}
	
	
}
