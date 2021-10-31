package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.BoardVO;
import domain.Criteria;
import lombok.Setter;
import mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	@Setter(onMethod_=@Autowired)
	private BoardMapper boardMapper;
	
	@Override
	public void register(BoardVO boardVO) {
		boardMapper.insertSelectKey(boardVO);
		//boardMapper.insert(boardVO);
	}

	@Override
	public boolean modify(BoardVO boardVO) {
		return boardMapper.update(boardVO);
	}

	@Override
	public boolean remove(Long bno) {
		return boardMapper.delete(bno);
	}

	@Override
	public BoardVO get(Long bno) {
		return boardMapper.read(bno);
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		return boardMapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return boardMapper.getTotalCount(cri);
	}

}
