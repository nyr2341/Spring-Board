package service;

import java.util.List;

import domain.Criteria;
import domain.ReplyPageDTO;
import domain.ReplyVO;

public interface ReplyService {
	public boolean register(ReplyVO vo);
	public boolean modify(ReplyVO vo);
	public boolean remove(Long bno, Long rno);
	public ReplyVO get(Long rno);
	public List<ReplyVO> getList(Criteria cri, Long bno);
	public ReplyPageDTO getListPage(Criteria cri, Long bno);
}
