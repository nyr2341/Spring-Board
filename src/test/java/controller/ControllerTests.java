package controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import config.RootConfig;
import config.ServletConfig;
import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // Test for Controller
@ContextConfiguration(classes= {RootConfig.class,ServletConfig.class})
public class ControllerTests {
	Logger logger = LoggerFactory.getLogger(ControllerTests.class);

	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;

	private MockMvc mockMvc; // 가짜 mvc

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	//@Test
	public void testBoardListWithPaging() throws Exception{
		logger.info(""+mockMvc.perform(MockMvcRequestBuilders.get("/board/list").param("pageNum", "2").param("amount", "50"))
		.andReturn().getModelAndView().getModelMap());
	}

	//@Test
	public void testBoardRegister() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "test for test")
				.param("content", "test")
				.param("writer", "test"))
				.andReturn().getModelAndView().getViewName();

		logger.info(""+resultPage);
	}
	
	//@Test
	public void testBoardGet() throws Exception{
		logger.info(""+mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
				.param("bno", "2")).andReturn().getModelAndView().getModelMap());
	}
	
	//@Test
	public void testBoardModify() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("bno", "2")
				.param("title", "test for test")
				.param("content", "test")
				.param("writer", "test")).andReturn().getModelAndView().getViewName();
		logger.info(""+resultPage);
	}
	
	//@Test
	public void testBoardRemove() throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "2")).andReturn().getModelAndView().getViewName();
		logger.info(""+resultPage);
	}
}
