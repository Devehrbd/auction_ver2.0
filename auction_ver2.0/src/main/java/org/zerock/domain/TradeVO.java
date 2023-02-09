package org.zerock.domain;

import lombok.Data;

@Data
public class TradeVO {
	String user_id;
	int total_count_s;
	int fail_count_s;
	int total_count_b;
	int fail_count_b;
}
