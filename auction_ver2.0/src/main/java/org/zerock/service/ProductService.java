package org.zerock.service;

import java.util.Date;
import java.util.List;


import org.zerock.domain.Criteria;
import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Bid_historyVO;
import org.zerock.domain.GPSVO;

import org.zerock.domain.ProductPicVO;
import org.zerock.domain.ProductVO;
import org.zerock.domain.TradeVO;

public interface ProductService {
	
	public List<ProductPicVO> piclistRead(int product_id);
	public int picCountRead(int product_id);
	public ProductVO productRead(int product_id);
	public void currentPriceUpdate(int product_id, int current_price);
	public int currentPriceRead(int product_id);
	public void currentPriceInsert(int product_id, int current_price,String user_id);
	public String currentPriceUserRead(int product_id);
	public void productRegist(ProductVO product);
	public void productGPSRegist(GPSVO gpsVo);
	public String TitleRead(int product_id);
	public void productDelete(int product_id);
	public Date regDateRead(int product_id);
	public List<ProductVO> startPriceSortSearchList(Criteria cri);
	public List<ProductVO> startPriceSortList();
	public List<ProductVO> dateSortSearchList(Criteria cri);
	public List<ProductVO> dateSortList();	
	
	public List<ProductVO> startPriceSortSearchList_desc(Criteria cri);
	public List<ProductVO> startPriceSortList_desc();
	public List<ProductVO> dateSortSearchList_desc(Criteria cri);
	public List<ProductVO> dateSortList_desc();	

	public ProductVO yesDistance(int product_id);
	
	// 아래 동길

	public List<ProductVO> getList();
	public List<ProductVO> searchList(Criteria cri);
	public List<ProductVO> price_desc();
	public List<ProductVO> pronew();
	public List<ProductVO> distance();
	
	// 호준

	public void updateDeadline(ProductVO pVo);
	public String BuyerIsWho(int product_id);
	public int IsExist(String user_id);
	public TradeVO selectTrade(String user_id);
	public void priceSale(ProductVO pVo);
	public List<Bid_historyVO> readBidList(String user_id);
	public GPSVO selectGPS(int product_id);
	public List<ProductVO> price_asc();
	public List<ProductVO> pronew_asc();
	
}
