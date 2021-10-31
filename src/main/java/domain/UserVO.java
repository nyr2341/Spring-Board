package domain;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO {
	private String userName;
	private String userPw;
	private Date regDate;
	private boolean enabled;
	private List<AuthorityVO> authList;
}
