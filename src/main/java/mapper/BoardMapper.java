package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import domain.BoardVO;
import domain.Criteria;

public interface BoardMapper {
	public List<BoardVO> getListWithPaging(Criteria cri);
	public BoardVO read(Long bno);
	public void insert(BoardVO board);
	public void insertSelectKey(BoardVO board);
	public boolean delete(Long bno);
	public boolean update(BoardVO board);
	public int getTotalCount(Criteria cri);
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
