package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.ChatStorageVO;
import org.zerock.domain.ChatVO;
import org.zerock.domain.ProductPicVO;
import org.zerock.mapper.ChatMapper;
import org.zerock.mapper.ProductPicMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("ChatService")
public class ChatServiceImpl implements ChatService{
	
	private ChatMapper chatmapper;
	private ProductPicMapper pPicMapper;
	
	@Override
	public void insertChat(ChatVO chat) {
		chatmapper.insertChat(chat);
	}

	@Override
	public List<ChatVO> SelectChat(int room_id) {
		return chatmapper.SelectChat(room_id);		
	}

	@Override
	public void insertStorage(ChatStorageVO storage) {
		chatmapper.insertStorage(storage);
	}

	@Override
	public void deleteLog(int room_id) {
		chatmapper.deleteLog(room_id);
	}

	@Override
	public List<ChatStorageVO> selectStorage(int product_id) {
		return chatmapper.selectStorage(product_id);
	}
	
	@Override
	public ChatVO chatDataRead(int room_id) {

		return chatmapper.readChatData(room_id);
	}
	
	@Override
	public ProductPicVO readProductPicOne(int product_id) {
		
		return pPicMapper.readProductPicOne(product_id);
	}
}
