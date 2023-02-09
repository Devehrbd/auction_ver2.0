package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.domain.ChatStorageVO;
import org.zerock.domain.ChatVO;
import org.zerock.domain.CompleteVO;
import org.zerock.domain.MemberVO;
import org.zerock.domain.ProductPicVO;
import org.zerock.domain.ProductVO;
import org.zerock.domain.Room;
import org.zerock.domain.ScoreVO;
import org.zerock.service.ChatService;
import org.zerock.service.MemberService;
import org.zerock.service.ProductService;
import org.zerock.service.RoomService;


import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/chatting/*")
public class ChatController {
	
	@Setter(onMethod_ = @Autowired)
	private MemberService mService;
	
	@Setter(onMethod_ = @Autowired)
	private RoomService rService;
	
	@Setter(onMethod_ = @Autowired)
	private ChatService cService;
	
	@Setter(onMethod_ = @Autowired)
	private ProductService pService;
		
	
	List<Room> roomList = new ArrayList<Room>();
	
	@RequestMapping("/chat")
	public ModelAndView chat() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("chatting/chat");
		return mv;
	}
	
	/**
	 * 방 생성하기
	 * @param params
	 * @return
	 */
	@RequestMapping("/createRoom")
	public @ResponseBody List<Room> createRoom(@RequestParam HashMap<Object, Object> params){
		String roomName = (String) params.get("roomName");
		int product_id =  Integer.parseInt((String)params.get("product_id"));
		String buyer = (String) params.get("buyer");
		String seller = (String) params.get("seller");
		
		CompleteVO complete = new CompleteVO();
		
		if(roomName != null && !roomName.trim().equals("")) {
			Room room = new Room();
			room.setProduct_id(product_id);
			room.setRoomName(roomName);
			room.setBuyer(buyer);
			room.setSeller(seller);
			
			complete.setProduct_id(product_id);
			complete.setBuyer_id(buyer);
			complete.setSeller_id(seller);
			
			rService.insertRoom(room);
			rService.insertComplete(complete);
			
			roomList.add(room);
		}
		return roomList;
	}
	
	
	
	/**
	 * 방 정보가져오기
	 * @param params
	 * @return
	 */
	@RequestMapping("/getRoom")
	public @ResponseBody List<Room> getRoom(@RequestParam HashMap<Object, Object> params){

		String user_id = (String) params.get("user_id");
		
//		roomList = rService.selectAllRoom();

		roomList = rService.selectSellerRoom(user_id); // 특정인만 출력
		roomList.addAll(rService.selectBuyerRoom(user_id)); 
		
		List<ChatVO> chat_data = new ArrayList<ChatVO>();
		
		for (int i = 0; i < roomList.size(); i++) {
			ChatVO cVo = new ChatVO();
			
			cVo = cService.chatDataRead(roomList.get(i).getRoom_id());
			chat_data.add(cVo);
		}
		
		
		
		
		return roomList;
	}
	
	/**
	 * 채팅방
	 * @return
	 */
	@RequestMapping("/moveChating")
	public ModelAndView chating(@RequestParam HashMap<Object, Object> params) {
		ModelAndView mv = new ModelAndView();
		int room_id = Integer.parseInt((String) params.get("room_id"));
		String user_id = (String)params.get("user_id");
		Room room = new Room();
		room = rService.selectOneRoom(room_id);
		
			if(user_id.equals(room.getBuyer())) {
				mv.addObject("seller",room.getSeller());
				mv.addObject("buyer", user_id);
				mv.addObject("member", mService.MemberRead(room.getSeller()));
				List<ScoreVO> scores = rService.selectScore(room.getSeller());
			
				float average=0.0f;
				int count=0;
				if (scores.isEmpty()) {
					mv.addObject("info_msg", "평가받은 적이 없는 사람입니다.");
				} else {
					for(int i=0; i<scores.size(); i++) {
						average+=scores.get(i).getUser_score();
						count++;
					}
					mv.addObject("average", average / count);
				}
			} else {
				mv.addObject("seller", user_id);
				mv.addObject("buyer",room.getBuyer());
				mv.addObject("member", mService.MemberRead(room.getBuyer()));
				List<ScoreVO> scores = rService.selectScore(room.getBuyer());
		
				float average=0.0f;
				int count=0;
				if (scores.isEmpty()) {
					mv.addObject("info_msg", "평가받은 적이 없는 사람입니다.");
				} else {
					for(int i=0; i<scores.size(); i++) {
						average+=scores.get(i).getUser_score();
						count++;
					}
					mv.addObject("average", average / count);
				}
			}
			List<ChatVO> chat_log = cService.SelectChat(room_id);
			mv.addObject("roomName", params.get("roomName"));
			mv.addObject("room_id", params.get("room_id"));
			mv.addObject("chat_log",chat_log);
			mv.addObject("product_id", room.getProduct_id());
			
			List<String> chat_date = new ArrayList<String>();
			
			for (int i = 0; i < chat_log.size(); i++) {
				
				Date chatting_date = chat_log.get(i).getChat_date();

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy년 MM월 dd일 HH시 mm분 ss초");
				String a = simpleDateFormat.format(chatting_date);

				String chat_message;
				
				Date current_date = new Date();
				long current_time = current_date.getTime();
				long chat_time = chatting_date.getTime();
				
				if(current_time - chat_time > 0 && current_time - chat_time < 60000 ) {
					chat_message = "방금전";	
					
				}else if (current_date.getDate() == chatting_date.getDate()){
					
				    simpleDateFormat = new SimpleDateFormat("a h시 mm분");
					chat_message = simpleDateFormat.format(chatting_date);	
				} else {
					 simpleDateFormat = new SimpleDateFormat("MM월 dd일 a h시 mm분");
					chat_message = simpleDateFormat.format(chatting_date);
					
				}
				
				chat_date.add(chat_message);
			}
			
			mv.addObject("chat_date",chat_date);
				
			ProductPicVO pPicVo = new ProductPicVO();
			pPicVo =cService.readProductPicOne(room.getProduct_id());
						
			mv.addObject("picture", pPicVo);
			
			mv.setViewName("chatting/chat");
		
		return mv;
	}
	
	@GetMapping("/score")
	public ModelAndView score(@RequestParam("room_id") int room_id, @RequestParam("user_id") String user_id, @RequestParam("product_id") int product_id, @RequestParam("user_score") int user_score, @RequestParam("cause") String cause) {

		ModelAndView mv = new ModelAndView();
		ScoreVO score = new ScoreVO();
		CompleteVO complete = new CompleteVO();
		Room room = new Room();
		room = rService.selectOneRoom(room_id);
		String anotherUser = "";
		
		complete.setProduct_id(product_id);
		
		if(user_id.equals(room.getSeller())) {
			// 평가한 사람이 판매자일 때
			anotherUser = room.getBuyer();
			complete.setBuyer_id(anotherUser);
			complete.setSeller_id(user_id);
			complete.setSeller_check(true);
			rService.updateSellerCheck(complete);	// 평가완료
			
			complete = rService.resultCheck(product_id);
			if(complete.isBuyer_check() && complete.isSeller_check()) {
				complete.setResult(1);
				rService.updateResult(product_id);
			}
			
		} else {
			// 평가한 사람이 구매자일 때
			anotherUser = room.getSeller();
			complete.setBuyer_id(user_id);
			complete.setSeller_id(anotherUser);
			complete.setBuyer_check(true);
			rService.updateBuyerCheck(complete);	// 평가완료
			
			complete = rService.resultCheck(product_id);
			if(complete.isBuyer_check() && complete.isSeller_check()) {
				complete.setResult(1);
				rService.updateResult(product_id);	
			}
		}
		
		if(mService.existScore(anotherUser, product_id) == null) {
		
			score.setUser_id(anotherUser);
			score.setProduct_id(product_id);
			score.setUser_score(user_score);
			score.setCause(cause);
		
			rService.insertScore(score);
		}
		
		mv.setViewName("redirect:/chatting/room2");
		return mv;
	}
	
	@RequestMapping("/log")
	@ResponseBody
	public ModelAndView log(@RequestParam("product_id") int product_id) {
		ModelAndView mv = new ModelAndView();
		
		List<ChatStorageVO> storageVo = cService.selectStorage(product_id);
		mv.addObject("log", storageVo);
		
		return mv;
	}
	
	
	
	//동규
	@GetMapping("/room2")
	public void roomList(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String sessionUser = (String)session.getAttribute("sessionUser");
		
		List<Room> room = new ArrayList<Room>();
		
		room = rService.selectSellerRoom(sessionUser); 
		room.addAll(rService.selectBuyerRoom(sessionUser)); 
		List<String> chat_data = new ArrayList<String>();
		
		for (int i = 0; i < room.size(); i++) {
			ChatVO cVo = new ChatVO();
			cVo = cService.chatDataRead(room.get(i).getRoom_id());
			if(cVo != null) {
				chat_data.add(cVo.getChat());
			}else {
				chat_data.add("");
			}
		}
		

		List<String> pic_data = new ArrayList<String>();
		
		for (int i = 0; i < room.size(); i++) {
			ProductPicVO pPicVo = new ProductPicVO();
			pPicVo = cService.readProductPicOne(room.get(i).getProduct_id());
			pic_data.add(pPicVo.getPicture_name());	
		}
		
		if(!room.isEmpty()) {
			model.addAttribute("room",room);
		}
		model.addAttribute("room_chat", chat_data);
		model.addAttribute("room_pic", pic_data);
	}
	

	@PostMapping("/ajaxChatting")
	public void ajaxChatting(@RequestParam("room_id") int room_id, Model model) {
		List<ChatVO> chat_log = cService.SelectChat(room_id);
		model.addAttribute("chat_log",chat_log);
		
		System.out.println(chat_log);
		
		List<String> chat_date = new ArrayList<String>();
		

		
		for (int i = 0; i < chat_log.size(); i++) {
			
			Date chatting_date = chat_log.get(i).getChat_date();

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy년 MM월 dd일 HH시 mm분 ss초");
			String a = simpleDateFormat.format(chatting_date);

			String chat_message;
			
			Date current_date = new Date();
			long current_time = current_date.getTime();
			long chat_time = chatting_date.getTime();
			
			if(current_time - chat_time > 0 && current_time - chat_time < 60000 ) {
				chat_message = "방금전";	
				
			}else if (current_date.getDate() == chatting_date.getDate()){
				
			    simpleDateFormat = new SimpleDateFormat("a h시 mm분");
				chat_message = simpleDateFormat.format(chatting_date);	
			} else {
				 simpleDateFormat = new SimpleDateFormat("MM월 dd일 a h시 mm분");
				chat_message = simpleDateFormat.format(chatting_date);
				
			}
			
			chat_date.add(chat_message);
		}
		model.addAttribute("chat_date",chat_date);
	}

}