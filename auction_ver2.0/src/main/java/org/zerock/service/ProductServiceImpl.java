package org.zerock.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import org.zerock.domain.Criteria;

import org.zerock.domain.Bid_historyVO;
import org.zerock.domain.GPSVO;

import org.zerock.domain.ProductPicVO;
import org.zerock.domain.ProductVO;
import org.zerock.domain.TradeVO;
import org.zerock.mapper.GPSMapper;
import org.zerock.mapper.ProductMapper;
import org.zerock.mapper.ProductPicMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private GPSMapper gpsMapper;
	private ProductMapper pMapper;
	private ProductPicMapper pPicMapper;
	
	
	@Override
	public List<ProductVO> ProductlistWhereUserIDRead(String user_id) {
		
		return pMapper.readProductlistWhereUserID(user_id);
	}
	
	@Override
	public List<ProductPicVO> piclistRead(int product_id) {
		return pMapper.piclist(product_id);
		
	}
	@Override
	public int picCountRead(int product_id) {
		
		return pMapper.picCount(product_id);
	}
	
	@Override
	public ProductVO productRead(int product_id) {
		
		return pMapper.readProduct(product_id);
	}
	@Override
	public void currentPriceUpdate(int product_id, int current_price) {
		pMapper.updateCP(product_id, current_price);
	}
	
	@Override
	public int currentPriceRead(int product_id) {
		
		return pMapper.readCP(product_id);
	}
	
	
	@Override
	public void currentPriceInsert(int product_id, int current_price, String user_id) {
		
		pMapper.insertCP(product_id, current_price, user_id);
	}
	
	@Override
	public String currentPriceUserRead(int product_id) {
		
		return pMapper.readCPU(product_id);
	}
	
	@Override
	public void productRegist(ProductVO product) {
		
		
		if (product.getNeighborhood() != null) {
			pMapper.registProductNeighborhoodSK(product);
		}else {
			pMapper.registProductSK(product);
		}
		
		if(product.getProductPic() != null ) {
			product.getProductPic().forEach(productPic -> {
				productPic.setProduct_id(product.getProduct_id());
				pPicMapper.RegistProductPic(productPic);
			});
		}

	}
	
	public void productGPSRegist(GPSVO gpsVo) {
		gpsMapper.registProductGPS(gpsVo);
	}

	
	@Override
	public String TitleRead(int product_id) {
		
		return pMapper.readTitle(product_id);
		
	}
	
	@Override
	public void productDelete(int product_id) {
		
		pPicMapper.DeleteProductPic(product_id);
		gpsMapper.deleteGPS(product_id);
		pMapper.deleteProduct(product_id);
		
	}
	
	@Override
	public Date regDateRead(int product_id) {
		
		return pMapper.readRegDate(product_id);
	}
	
	@Override
	public List<ProductVO> startPriceSortSearchList(Criteria cri) {
		
		return pMapper.searchListSortStartPrice(cri);
	}
	@Override
	public List<ProductVO> startPriceSortList() {
		
		return pMapper.listSortStartPrice();
	}
	
	@Override
	public List<ProductVO> dateSortList() {
		// TODO Auto-generated method stub
		return pMapper.listSortDate();
	}
	@Override
	public List<ProductVO> dateSortSearchList(Criteria cri) {
		// TODO Auto-generated method stub
		return pMapper.searchListSortDate(cri);
	}
	// 아래 동길

	@Override
	public List<ProductVO> getList() {
		return pMapper.getList();
	}	
	
	@Override
	public List<ProductVO> searchList(Criteria cri) {
		
		return pMapper.searchList(cri);
	}
	
	@Override
	public List<ProductVO> price_desc() {

		return pMapper.price_desc();
	}

	@Override
	public List<ProductVO> pronew() {
		
		return pMapper.pronew();
	}
	

	@Override
	public List<ProductVO> distance() {
		
		return pMapper.distance();
	}

	@Override
	public ProductVO yesDistance(int product_id) {
		
		return pMapper.distanceYES(product_id);
	}
	// 호준
	@Override
	public List<Bid_historyVO> readBidList(String user_id) {
		return pMapper.readBidList(user_id);
	}

	@Override
	public void updateDeadline(ProductVO pVo) {
		pMapper.updateDeadline(pVo);
		
	}
	@Override
	public String BuyerIsWho(int product_id) {
		
		return pMapper.BuyerIsWho(product_id);
	}

	@Override
	public int IsExist(String user_id) {
		
		return pMapper.IsExist(user_id);
	}
	@Override
	public TradeVO selectTrade(String user_id) {
		return pMapper.selectTrade(user_id);
	}
	@Override
	public void priceSale(ProductVO pVo) {
		pMapper.priceSale(pVo);
	}
	@Override
	public GPSVO selectGPS(int product_id) {
		return pMapper.selectGPS(product_id);
	}
	@Override
	public List<ProductVO> price_asc() {
	
		return pMapper.price_asc();
	}
	@Override
	public List<ProductVO> pronew_asc() {
		return pMapper.pronew_asc();
	}
	@Override
	public List<ProductVO> startPriceSortSearchList_desc(Criteria cri) {
		
		return pMapper.searchListSortStartPrice_desc(cri);
	}
	@Override
	public List<ProductVO> startPriceSortList_desc() {
		
		return pMapper.listSortStartPrice_desc();
	}
	
	@Override
	public List<ProductVO> dateSortList_desc() {
		// TODO Auto-generated method stub
		return pMapper.listSortDate_desc();
	}
	@Override
	public List<ProductVO> dateSortSearchList_desc(Criteria cri) {
		// TODO Auto-generated method stub
		return pMapper.searchListSortDate_desc(cri);
	}
}
