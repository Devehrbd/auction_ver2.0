package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.domain.ChatStorageVO;
import org.zerock.domain.ChatVO;

@Mapper
public interface ChatMapper {
	public void insertChat(ChatVO chat);
	public List<ChatVO> SelectChat(int room_id);
	public void insertStorage(ChatStorageVO storage);
	public void deleteLog(int room_id);
	public List<ChatStorageVO> selectStorage(int product_id);
	
	
	//동규
	public ChatVO readChatData(int room_id);
}
