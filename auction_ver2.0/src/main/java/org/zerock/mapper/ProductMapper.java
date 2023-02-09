package org.zerock.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.zerock.domain.Criteria;

import org.zerock.domain.Bid_historyVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.GPSVO;

import org.zerock.domain.ProductPicVO;
import org.zerock.domain.ProductVO;
import org.zerock.domain.TradeVO;
@Mapper
public interface ProductMapper {
	public void registProductPic (ProductPicVO productpic);
	public void registProductSK(ProductVO product);	
	public void registProduct (ProductVO product);
	public void registProductGPS (GPSVO gpsVo);
	public void registProductNeighborhoodSK (ProductVO product);
	public List<ProductPicVO> piclist(int product_id);
	public int picCount(int product_id);
	public ProductVO readProduct(int product_id);
	public void updateCP(@Param("product_id")int product_id,@Param("current_price")int current_price);
	public int readCP(int product_id);
	public void insertCP(@Param("product_id")int product_id,@Param("current_price")int current_price,@Param("user_id")String user_id);
	public String readCPU(int product_id);
	public String readTitle(int product_id);
	public List<ProductVO> readProductlist(@Param("user_id")String user_id,@Param("cri")Criteria cri);
	public int readProductlistCount(String user_id);
	public void deleteProduct(int product_id);
	public Date readRegDate(int product_id);
	public List<ProductVO> searchListSortStartPrice(@Param("cri")Criteria cri);
	public List<ProductVO> listSortStartPrice();
	public List<ProductVO> searchListSortDate(@Param("cri")Criteria cri);
	public List<ProductVO> listSortDate();
	
	public List<ProductVO> searchListSortStartPrice_desc(@Param("cri")Criteria cri);
	public List<ProductVO> listSortStartPrice_desc();
	public List<ProductVO> searchListSortDate_desc(@Param("cri")Criteria cri);
	public List<ProductVO> listSortDate_desc();
	
	public ProductVO distanceYES(int product_id);
	
	// 아래 동길
	public List<ProductVO> getList();
	public int delete(int product_id);
	public int update(ProductVO product);
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
