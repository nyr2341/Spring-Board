package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.UserVO;
import lombok.Setter;
import service.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Setter(onMethod_= {@Autowired})
	UserService service;
	
	@GetMapping("/customLogin")
	public void doLogin() { }

	@PostMapping("/customLogout")
	public void doLogout() { }
	
	@GetMapping("/joinUser")
	public void join() { }
	
	@PostMapping("/join")
	public String join(UserVO userVO, RedirectAttributes rttr) {
		service.join(userVO);
		return "redirect:/board/list";	
	}
}
