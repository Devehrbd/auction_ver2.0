package org.zerock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.domain.ProductPicVO;
@Mapper
public interface ProductPicMapper {
	
	public void RegistProductPic(ProductPicVO productPicVO);
	public ProductPicVO readProductPicOne(int product_id);
	public void DeleteProductPic(int product_id);
}
