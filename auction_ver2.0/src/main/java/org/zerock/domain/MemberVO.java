package org.zerock.domain;

import lombok.Data;

@Data
public class MemberVO {
	private String user_id;
	private String pw;
	private String name;
	private String birth;
	private String phone;

}