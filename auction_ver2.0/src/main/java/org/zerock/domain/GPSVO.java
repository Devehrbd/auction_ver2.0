package org.zerock.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GPSVO {
	private int product_id;
	private BigDecimal latitude;
	private BigDecimal longitude;
}