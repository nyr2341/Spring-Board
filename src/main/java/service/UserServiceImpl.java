package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import domain.UserVO;
import lombok.Setter;
import mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{

	@Setter(onMethod_= {@Autowired})
	private UserMapper userMapper;
	
	@Setter(onMethod_= {@Autowired})
	private PasswordEncoder pwEncoder;
	
	@Override
	public void join(UserVO userVO) {
		userVO.setUserPw(pwEncoder.encode(userVO.getUserPw()));
		userMapper.insert(userVO);
		userMapper.authorization(userVO.getUserName());
	}

	@Override
	public void read(String userName) {
		userMapper.read(userName);
	}
}
