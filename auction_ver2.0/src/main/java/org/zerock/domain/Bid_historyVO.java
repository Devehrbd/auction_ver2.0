package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Bid_historyVO {
	int bid_id;
	String user_id;
	Date bid_date;
	int current_price;
	int product_id;
}