package service;

import java.util.List;

import domain.BoardVO;
import domain.Criteria;

public interface BoardService {
	public void register(BoardVO boardVO);
	public boolean modify(BoardVO boardVO);
	public boolean remove(Long bno);
	public BoardVO get(Long bno);
	public List<BoardVO> getList(Criteria cri);
	public int getTotal(Criteria cri);
}
