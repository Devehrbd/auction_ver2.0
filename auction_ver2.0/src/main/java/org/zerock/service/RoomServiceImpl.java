package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.CompleteVO;
import org.zerock.domain.Room;
import org.zerock.domain.ScoreVO;
import org.zerock.mapper.RoomMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("RoomService")
public class RoomServiceImpl implements RoomService{

	private RoomMapper roomMapper;
	
	@Override
	public void insertRoom(Room room) {
		roomMapper.insertRoom(room);
	}

	@Override
	public List<Room> selectAllRoom() {
		return roomMapper.selectAllRoom();
	}

	@Override
	public List<Room> selectSellerRoom(String seller) {
		return roomMapper.selectSellerRoom(seller);
	}

	@Override
	public void insertScore(ScoreVO score) {
		roomMapper.insertScore(score);
	}

	@Override
	public Room selectOneRoom(int room_id) {
		return roomMapper.selectOneRoom(room_id);
	}
	@Override
	public Room selectOneRoomByProduct_id(int product_id) {		
		return roomMapper.selectOneRoomByProduct_id(product_id);
	}
	@Override
	public void deleteRoom(int room_id) {
		roomMapper.deleteRoom(room_id);
	}
	@Override
	public void insertComplete(CompleteVO complete) {
		roomMapper.insertComplete(complete);
	}

	@Override
	public void updateBuyerCheck(CompleteVO complete) {
		roomMapper.updateBuyerCheck(complete);
	}

	@Override
	public void updateSellerCheck(CompleteVO complete) {
		roomMapper.updateSellerCheck(complete);
	}

	@Override
	public CompleteVO resultCheck(int product_id) {
		return roomMapper.resultCheck(product_id);
	}

	@Override
	public void updateResult(int product_id) {
		roomMapper.updateResult(product_id);
	}

	@Override
	public void insertSuccessTradeSeller(String user_id) {
		roomMapper.insertSuccessTradeSeller(user_id);	
	}

	@Override
	public void insertSuccessTradeBuyer(String user_id) {
		roomMapper.insertSuccessTradeBuyer(user_id);
	}

	@Override
	public List<Room> selectBuyerRoom(String buyer) {
		return roomMapper.selectBuyerRoom(buyer);
	}

	@Override
	public CompleteVO selectComplete(int product_id) {
		
		return roomMapper.selectComplete(product_id);
	}

	@Override
	public void deleteComplete(int product_id) {
		roomMapper.deleteComplete(product_id);
	}

	@Override
	public void insertFailTradeSeller(String user_id) {
		roomMapper.insertFailTradeSeller(user_id);		
	}

	@Override
	public void insertFailTradeBuyer(String user_id) {
		roomMapper.insertFailTradeBuyer(user_id);
	}

	@Override
	public List<ScoreVO> selectScore(String user_id) {
		return roomMapper.selectScore(user_id);
	}
}
