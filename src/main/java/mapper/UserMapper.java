package mapper;

import domain.UserVO;

public interface UserMapper {
	public UserVO read(String userName);
	public void insert(UserVO uservo);
	public void authorization(String userName);
}
