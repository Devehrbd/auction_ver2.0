package org.zerock.service;

import java.util.List;

import org.zerock.domain.ChatStorageVO;
import org.zerock.domain.ChatVO;
import org.zerock.domain.ProductPicVO;

public interface ChatService {

	public void insertChat(ChatVO chat);
	public List<ChatVO> SelectChat(int room_id);
	public void insertStorage(ChatStorageVO storage);
	public void deleteLog(int room_id);
	public List<ChatStorageVO> selectStorage(int product_id);
	
	//동규
	public ChatVO chatDataRead(int room_id);
	public ProductPicVO readProductPicOne(int product_id);
}
