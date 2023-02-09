package org.zerock.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.ChatStorageVO;
import org.zerock.domain.ChatVO;
import org.zerock.domain.CompleteVO;
import org.zerock.domain.ProductVO;
import org.zerock.domain.Room;
import org.zerock.mapper.ChatMapper;
import org.zerock.mapper.ProductMapper;
import org.zerock.mapper.RoomMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Component
public class PeriodCheckTask {

	@Setter(onMethod_ = { @Autowired })
	private ProductMapper pMapper;

	@Setter(onMethod_ = { @Autowired })
	private RoomMapper rMapper;

	@Setter(onMethod_ = { @Autowired })
	private ChatMapper cMapper;

	@Scheduled(cron = "0 0/10 * * * *")
	public void checkFiles() {
		Date now = new Date();
		List<ProductVO> productList = pMapper.getList();

		productList.forEach(product -> {

			Date productDate = product.getDate();

			CompleteVO complete = new CompleteVO();
			long diffTime = now.getTime() - productDate.getTime();
			long oneday = 86400000;
			int product_id = product.getProduct_id();
			Room room = rMapper.selectOneRoomByProduct_id(product_id);
			String Buyer_id = pMapper.BuyerIsWho(product_id);

			if (diffTime >= oneday*3 && diffTime < oneday*10) {
				// 3일 지난 상태
				// 채팅방 개설
				
				if (Buyer_id == null) { // 구매자가 없으면
					return;
				} else if (room != null) { // 방이 존재하는지
					complete = rMapper.selectComplete(product_id);
					if (complete.getResult() == 1) { // 둘다 거래 햇으면 거래완료테이블, 방 제거
						
						rMapper.insertSuccessTradeBuyer(Buyer_id);
						rMapper.insertSuccessTradeSeller(product.getUser_id());		// 거래 횟수 증가
						
						List<ChatVO> cVo = cMapper.SelectChat(room.getRoom_id());
					
						cVo.forEach(chat -> {
							ChatStorageVO storage = new ChatStorageVO();

							storage.setUser_id(chat.getUser_id());
							storage.setChat(chat.getChat());
							storage.setProduct_id(product.getProduct_id());

							cMapper.insertStorage(storage);
						}); // 채팅기록 다른 곳에 저장

						cMapper.deleteLog(room.getRoom_id()); // 채팅방 기록 삭제
						rMapper.deleteRoom(room.getRoom_id());
						rMapper.deleteComplete(product_id);
					}
				} else {	// 방이 없을 경우
					Room new_room = new Room(); // 방 개설

					new_room.setProduct_id(product_id);
					new_room.setRoomName(product.getTitle());
					new_room.setBuyer(Buyer_id);
					new_room.setSeller(product.getUser_id());

					complete.setProduct_id(product_id);
					complete.setBuyer_id(Buyer_id);
					complete.setSeller_id(product.getUser_id());

					rMapper.insertRoom(new_room);
					rMapper.insertComplete(complete);
				}

			} else if (diffTime >= oneday*10) {
				// 10일 지난 상태
				// 채팅방 자동 폭파
				if (room != null) { // 방이 존재하면
					complete = rMapper.selectComplete(product_id);
					List<ChatVO> cVo = cMapper.SelectChat(room.getRoom_id());
					cVo.forEach(chat -> {
						ChatStorageVO storage = new ChatStorageVO();

						storage.setUser_id(chat.getUser_id());
						storage.setChat(chat.getChat());
						storage.setProduct_id(product.getProduct_id());

						cMapper.insertStorage(storage);
					}); // 채팅기록 다른 곳에 저장

					if (complete.getResult() == 1) { // 둘다 거래완료 눌럿으면
						rMapper.insertSuccessTradeBuyer(Buyer_id);
						rMapper.insertSuccessTradeSeller(product.getUser_id());
					} else { // 거래완료가 안되었으면
						rMapper.insertFailTradeBuyer(Buyer_id);
						rMapper.insertFailTradeSeller(product.getUser_id());
					}
					cMapper.deleteLog(room.getRoom_id()); // 채팅방 기록 삭제
					rMapper.deleteRoom(room.getRoom_id());
					rMapper.deleteComplete(product_id);
				}
			} else {	// 3일 이전
				if (Buyer_id == null) { // 구매자가 없으면
					if(diffTime/oneday >= 1 && diffTime/oneday < 2) {	// deadline 입력
						product.setDeadline(1);
						pMapper.updateDeadline(product);
					} else if(diffTime/oneday >= 2) {
						product.setDeadline(2);
						pMapper.updateDeadline(product);
					}
				}
			}
		});
	}
	
	@Scheduled(cron = "0 0 0 1/1 * ?")
	public void DeadLineCheck() {
		List<ProductVO> productList = pMapper.getList();
		
		productList.forEach(product -> {
			int product_id = product.getProduct_id();
			String Buyer_id = pMapper.BuyerIsWho(product_id);
			
			if (Buyer_id == null) {	// 구매자가 없을 시
				if (product.getDeadline() == 1) {
					int price = (int) (product.getStart_price() * 0.9); // 10퍼 할인
					product.setStart_price(price);
					pMapper.priceSale(product);
				} else if (product.getDeadline() == 2) {
					int price = (int) (product.getStart_price() * 10 / 9); // 등록가격으로 변경
					product.setStart_price((int) (price * 0.8)); // 20퍼 할인
					pMapper.priceSale(product);
				}
			}
		});
	}
}
