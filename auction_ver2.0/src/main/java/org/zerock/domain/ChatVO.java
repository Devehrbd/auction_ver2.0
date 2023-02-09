package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ChatVO {
	private int chat_num;
	private String user_id;
	private String chat;
	private int room_id;
	private Date chat_date;
}
