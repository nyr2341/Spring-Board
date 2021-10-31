package mapper;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.RootConfig;
import config.SecurityConfig;
import domain.BoardVO;
import domain.Criteria;
import domain.ReplyVO;
import domain.UserVO;
import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class,SecurityConfig.class})
public class MapperTests {
	Logger logger = LoggerFactory.getLogger(MapperTests.class);
	
	@Setter(onMethod_= {@Autowired})
	private BoardMapper boardMapper;
	
	@Setter(onMethod_= {@Autowired})
	private ReplyMapper replyMapper;
	
	@Setter(onMethod_= {@Autowired})
	private UserMapper userMapper;
	
	@Setter(onMethod_= {@Autowired})
	private PasswordEncoder pwEncoder;
	
	//@Test
	public void testBoardGetListWithPaging() {
		Criteria cri = new Criteria();
		cri.setPageNum(2);
		cri.setAmount(10);
		
		List<BoardVO> list = boardMapper.getListWithPaging(cri);
		list.forEach(board -> logger.info(""+board));
	}
	
	//@Test
	public void testBoardInsert() {
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("새로 작성하는 글");
		boardVO.setContent("새로 작성하는 내용");
		boardVO.setWriter("작성자");
		boardMapper.insert(boardVO);
		System.out.println("===============================");
		logger.info(""+boardVO);
	}
	
	//@Test
		public void testBoardInsertSelectKey() {
			BoardVO boardVO = new BoardVO();
			boardVO.setTitle("새로 작성하는 글");
			boardVO.setContent("새로 작성하는 내용");
			boardVO.setWriter("작성자");
			boardMapper.insertSelectKey(boardVO);
			System.out.println("===============================");
			logger.info(""+boardVO);
		}
	
	//@Test
	public void testBoardRead() {
		BoardVO boardVO = boardMapper.read(7L);
		logger.info(""+boardVO);
	}
	
	//@Test
	public void testBoardDelete() {
		logger.info(""+boardMapper.delete(8L));
	}
	
	//@Test
	public void testBoardUpdate() {
		BoardVO boardVO = new BoardVO();
		boardVO.setBno(3L);
		boardVO.setTitle("수정하는 글");
		boardVO.setContent("수정하는 내용");
		boardVO.setWriter("수정하는 사람");
		logger.info(""+boardMapper.update(boardVO));
	}
	
	//@Test
	public void testBoardSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("키워드");
		cri.setType("TC");
		
		List<BoardVO> list = boardMapper.getListWithPaging(cri);
		list.forEach(board-> logger.info(""+board));
	}
	
	//@Test
	public void testReplyMapper() {
		logger.info(""+replyMapper);
	}
	
	private Long[] bnoArr = { 20L, 19L };
	
	//@Test
	public void testReplyCreate() {
		IntStream.rangeClosed(1,10).forEach(i->{
			ReplyVO vo = new ReplyVO();
			vo.setBno(bnoArr[i%2]);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("replyer" + i);
			
			replyMapper.insert(vo);
		});
	}
	
	//@Test
	public void testReplyRead() {
		Long targetRno = 5L;
		ReplyVO vo = replyMapper.read(targetRno);
		logger.info(""+vo);
	}
	
	//@Test
	public void testReplyDelete() {
		Long targetRno = 5L;
		replyMapper.delete(targetRno);
	}
	
	//@Test
	public void testReplyUpdate() {
		Long targetRno = 4L;
		ReplyVO vo = replyMapper.read(targetRno);
		vo.setReply("update 테스트 !!");
		
		replyMapper.update(vo);
	}
	
	//@Test
	public void testReplyList() {
		Criteria cri = new Criteria(1,10);
		List<ReplyVO> replies = replyMapper.getListWithPaging(cri, 20L);
		replies.forEach(reply->logger.info(""+reply));
	}
	
	@Test
	public void testUserInsert() {
		UserVO userVO = new UserVO();
		userVO.setUserName("admin");
		userVO.setUserPw(pwEncoder.encode("admin"));
		
		userMapper.insert(userVO);
	}
}
