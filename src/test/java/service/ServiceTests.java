package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.RootConfig;
import domain.BoardVO;
import domain.Criteria;
import lombok.Setter;
import mapper.MapperTests;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class})
public class ServiceTests {
	Logger logger = LoggerFactory.getLogger(MapperTests.class);

	@Setter(onMethod_= {@Autowired})
	private BoardService boardService;

	@Test 
	public void testRegister() {
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("서비스에서 새로 작성하는 글");
		boardVO.setContent("서비스에서 새로 작성하는 내용");
		boardVO.setWriter("작성자");
		
		boardService.register(boardVO);
	}
	
	//@Test
	public void testModify() {
		BoardVO boardVO = new BoardVO();
		boardVO.setBno(2L);
		boardVO.setTitle("서비스에서 수정하는 글");
		boardVO.setContent("서비스에서 수정하는 내용");
		boardVO.setWriter("작성자");
		logger.info(""+boardService.modify(boardVO));
	}
	
	//@Test
	public void testRemove() {
		logger.info(""+boardService.remove(3L));
	}

	//@Test
	public void testGet() {
		logger.info(""+boardService.get(1L));
	}
	
	//@Test
	public void testGetList() {
		boardService.getList(new Criteria(2,10)).forEach(board->logger.info(""+board));
	}
}
