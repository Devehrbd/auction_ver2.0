package org.zerock.domain;

import lombok.Data;

@Data
public class CompleteVO {
	private int product_id;
	private String buyer_id;
	private String seller_id;
	private int result;
	private boolean buyer_check;
	private boolean seller_check;
}
