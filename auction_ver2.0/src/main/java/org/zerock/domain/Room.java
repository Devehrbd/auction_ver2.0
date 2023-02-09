package org.zerock.domain;

import lombok.Data;

@Data
public class Room {
	int room_id;
	int product_id;
	String roomName;
	String buyer;
	String seller;

}