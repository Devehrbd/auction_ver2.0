package org.zerock.domain;

import lombok.Data;

@Data
public class ProductPicVO {
	private int picture_id;
	private int product_id;
	private String picture_path;
	private String picture_name;
}