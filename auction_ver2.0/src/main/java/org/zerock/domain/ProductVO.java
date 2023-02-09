package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProductVO {
	private int product_id;
	private String user_id;
	private String title;
	private String description;
	private int start_price;
	private int current_price;
	private Date date;
	private String neighborhood = "NO";
	private int deadline;
	

	private List<ProductPicVO> productPic;
	

}
