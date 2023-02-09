package org.zerock.domain;

import lombok.Data;

@Data
public class ChatStorageVO {
	private int chat_num;
	private String user_id;
	private String chat;
	private int product_id;
}
