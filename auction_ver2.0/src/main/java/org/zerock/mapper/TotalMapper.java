package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Bid_historyVO;
import org.zerock.domain.Criteria;
@Mapper
public interface TotalMapper {
	public List<Bid_historyVO> readTotalBid(@Param("user_id")String user_id,@Param("cri")Criteria cri);
	public List<Bid_historyVO> readAllTotalBid(String user_id);
	public int readTotalBidCount(String user_id);
}
