package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.BoardVO;
import domain.Criteria;
import domain.PageDTO;
import lombok.Setter;
import service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Setter(onMethod_= {@Autowired})
	private BoardService service;
	
	@GetMapping("/list")
	public void list(Criteria cri,Model model) {
		int total = service.getTotal(cri);
		model.addAttribute("list", service.getList(cri));
		model.addAttribute("pageMaker", new PageDTO(cri,total));
	}
	
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("board",service.get(bno));
	}
	
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void register() {}
	
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		service.register(board);
		rttr.addFlashAttribute("result",board.getBno()); // 새롭게 등록된 개시물의 번호를 같이 전달하기 위함, 일회성으로 데이터 전달 ( 단 한번만 사용할수 있게 데이터가 보관됨 )
		return "redirect:/board/list";					// redirect: 접두어는 스프링 MVC가 내부적으로 reponse.sendRedirect() 를 처리해준다.  
	}
	
	@PostMapping("/modify")
	@PreAuthorize("principal.username==#board.writer")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		if(service.modify(board)) 
			rttr.addFlashAttribute("result","success"); // 성공한 후에만 RedirectAttributes 에 추가
		/* UriComponentsBuilder 사용
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("amount",cri.getKeyword());
		rttr.addAttribute("amount",cri.getType());
		*/
		return "redirect:/board/list"+cri.getListLink();
	}
	
	@PostMapping("/remove")
	@PreAuthorize("principal.username==#writer")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, String writer) throws Exception{
		if(service.remove(bno))
			rttr.addFlashAttribute("result","success");
		/* UriComponentsBuilder 사용
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("amount",cri.getKeyword());
		rttr.addAttribute("amount",cri.getType());
		*/
		
		return "redirect:/board/list"+cri.getListLink();
	}
	

}
