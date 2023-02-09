package org.zerock.domain;

import lombok.Data;

@Data
public class ScoreVO {
	String user_id;
	int product_id;
	int user_score;
	String cause;
}
