package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.Bid_historyVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ProductVO;
import org.zerock.mapper.ProductMapper;
import org.zerock.mapper.TotalMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ToTalServiceImpl implements TotalService {

	private ProductMapper pMapper;
	private TotalMapper tMapper;
	
	@Override
	public List<Bid_historyVO> TotalBidRead(String user_id, Criteria cri) {
		
		return tMapper.readTotalBid(user_id,cri);
		
		
	}
	
	@Override
	public List<Bid_historyVO> TotalBidReadAll(String user_id) {
		// TODO Auto-generated method stub
		return tMapper.readAllTotalBid(user_id);
	}
	
	@Override
	public int TotalBidCountRead(String user_id) {
		
		return tMapper.readTotalBidCount(user_id);
	}
	
	@Override
	public List<ProductVO> ProductlistRead(String user_id, Criteria cri) {
		
		return pMapper.readProductlist(user_id,cri);
	}
	
	@Override
	public int ProductlistCountRead(String user_id) {
		// TODO Auto-generated method stub
		return pMapper.readProductlistCount(user_id);
	}
}
