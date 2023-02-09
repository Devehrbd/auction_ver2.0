package org.zerock.service;

import java.util.List;

import org.zerock.domain.Bid_historyVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ProductVO;

public interface TotalService {
	public List<Bid_historyVO> TotalBidRead(String user_id, Criteria cri);
	public List<Bid_historyVO> TotalBidReadAll(String user_id);
	public int TotalBidCountRead(String user_id);
	public List<ProductVO> ProductlistRead(String user_id, Criteria cri);
	public int ProductlistCountRead(String user_id);
}
