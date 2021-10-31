package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import domain.Criteria;
import domain.ReplyVO;

public interface ReplyMapper {
	public ReplyVO read(Long rno);
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	public int getCountByBno(Long bno);
	public boolean insert(ReplyVO vo);
	public boolean delete(Long rno);
	public boolean update(ReplyVO vo);
}
