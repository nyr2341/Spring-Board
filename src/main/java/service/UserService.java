package service;

import domain.UserVO;

public interface UserService {
	public void join(UserVO userVO);
	public void read(String userName);
}
