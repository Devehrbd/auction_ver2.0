package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.domain.BidPageVO;
import org.zerock.domain.Bid_historyVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.MemberVO;
import org.zerock.domain.PageMaker;
import org.zerock.domain.ProductPicVO;
import org.zerock.domain.ProductVO;
import org.zerock.service.MemberService;
import org.zerock.service.ProductService;
import org.zerock.service.TotalService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/info/*")
@AllArgsConstructor
public class InfoController {

	private TotalService tService;
	private ProductService pService;
	private MemberService mService;
	
	@GetMapping("/bid")
	public void bid(@RequestParam("bid_page")int bid_page,@RequestParam("reg_page")int reg_page,@RequestParam("awd_page")int awd_page ,HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		List<Bid_historyVO> BidList = new ArrayList<Bid_historyVO>();
		List<Bid_historyVO> BidList2 = new ArrayList<Bid_historyVO>();
		String user_id = (String) session.getAttribute("sessionUser");
		
		
		if(bid_page == 0) {
			bid_page = 1;
		}
		
		if(reg_page == 0) {
			reg_page = 1;
		}
		
		if(awd_page == 0) {
			awd_page = 1;
		}
		
	
		Criteria bid_cri = new Criteria();
		bid_cri.setCurrentPage(bid_page);
		PageMaker bid_pageMaker = new PageMaker();
		bid_pageMaker.setCri(bid_cri);
		bid_pageMaker.setTotalCount(tService.TotalBidCountRead(user_id)); 
		
		
		//list ??????
		
		BidList = tService.TotalBidRead(user_id,bid_cri);
		BidList2 = tService.TotalBidReadAll(user_id);
		model.addAttribute("BidList", BidList);
		model.addAttribute("BidListPage",bid_pageMaker);
		
		
		//title ????????????
		List<String> title_list = new ArrayList<String>();
		
		for (int i = 0 ; i < BidList.size(); i++) {
			
			int product_id = BidList.get(i).getProduct_id();
			String title = pService.TitleRead(product_id);
			title_list.add(title);
			
		}
		
		model.addAttribute("BidListTitle", title_list);
		
		//??????????????????(fmt?????????)
		List<String> Date_list = new ArrayList<String>();
		
		for (int i = 0; i < BidList.size(); i++) {
			
			Date BidDate = BidList.get(i).getBid_date();
			System.out.println("?????? ??????????????????"+BidDate);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy??? MM??? dd???");
			String Bid_Date = simpleDateFormat.format(BidDate);
			
			Date_list.add(Bid_Date);
			
		}
		
		model.addAttribute("BidListDate", Date_list);
		
		//???????????????
		List<String> Bid_etc = new ArrayList<String>();
		
		for (int i = 0; i < BidList.size(); i++) {
			String etc;
			int product_id = BidList.get(i).getProduct_id();
			String currentUser = pService.currentPriceUserRead(product_id);	
			
			Date date = new Date();
			Date regDate = pService.regDateRead(product_id);
			long endDate = regDate.getTime()+259200000;
			long currentDate = date.getTime();
			
			if(endDate < currentDate) {
				
				etc = "????????? ?????????????????????.";
				
			}else {
				
				
				if(BidList.get(i).getUser_id().equals(currentUser)) {
					etc = "????????? ???????????????.";
				} else {
					etc = "?????? ????????? ???????????????.";
				}
				
			}
				
			Bid_etc.add(etc);		
		}
		
		model.addAttribute("BidListEtc", Bid_etc);
		
		
		Criteria reg_cri = new Criteria();
		reg_cri.setCurrentPage(reg_page);
		PageMaker reg_pageMaker = new PageMaker();
		reg_pageMaker.setCri(reg_cri);
		reg_pageMaker.setTotalCount(tService.ProductlistCountRead(user_id)); 
		
		//???????????????
		List<ProductVO> product_list = new ArrayList<ProductVO>();
		
		product_list = tService.ProductlistRead(user_id,reg_cri);
		
		model.addAttribute("productList",product_list);
		model.addAttribute("productListPage",reg_pageMaker);
		
		//??????????????? ?????? ??????
		List<String> date_list_reg = new ArrayList<String>();
		
		for (int i = 0; i < product_list.size(); i++) {
			
			Date regDate = product_list.get(i).getDate();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy??? MM??? dd???");
			String reg_date = simpleDateFormat.format(regDate);
			
			date_list_reg.add(reg_date);
			
		}
		
		
		model.addAttribute("RegListDate", date_list_reg);
		
		//awd cri ??????
		Criteria awd_cri = new Criteria();
		awd_cri.setCurrentPage(awd_page);
		
		
		
		//???????????????, ????????????, ????????? ????????????
		List<ProductVO> award_list = new ArrayList<ProductVO>();
		List<String> endDate_list = new ArrayList<String>();
		List<String> phoneNum = new ArrayList<String>();
		
		
		
		// 
		// 0 -> 1?????? 
		for(int i = 0; i < BidList2.size(); i++) {
			
			
			int product_id = BidList2.get(i).getProduct_id();
			String currentUser = pService.currentPriceUserRead(product_id);
			
			
			Date date = new Date();
			Date regDate = pService.regDateRead(product_id);
			long endDate = regDate.getTime()+259200000;
			long currentDate = date.getTime();
			
			//??????????????????
			if(endDate < currentDate) {
				
				//????????????????????????
				if(user_id.equals(currentUser)) {
					//awadlist add
					ProductVO awardPvo = new ProductVO();					
					awardPvo = pService.productRead(product_id);
					award_list.add(awardPvo);
					//endDate add
					Date awardDate = new Date(endDate);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy??? MM??? dd???");
					String awardDateString = simpleDateFormat.format(awardDate);
					endDate_list.add(awardDateString);
					//phoneNum add
					MemberVO mVo = new MemberVO();
					String sellerId = awardPvo.getUser_id();
					mVo = mService.MemberRead(sellerId);
					String sellerPhone = mVo.getPhone();
					phoneNum.add(sellerPhone);
					
					
					
				}
				
			}
			
			
		}
		//for??? ???
		
		List<ProductVO> award_list2 = new ArrayList<ProductVO>();
		List<String> endDate_list2 = new ArrayList<String>();
		List<String> phoneNum2 = new ArrayList<String>();
		
		int pn = awd_cri.getOnePageNum();
		int ps = awd_cri.getPageStart();
		
		
		for (int i = 0; i < award_list.size(); i++) {
			
			if(i >= ps) {
				
				if(i < ps+pn) {
					
					award_list2.add(award_list.get(i));
					endDate_list2.add(endDate_list.get(i));
					phoneNum2.add(phoneNum.get(i));
					
				}	
			}	
		}
		
		//awd pageMaker??????
		PageMaker awd_pageMaker = new PageMaker();
		awd_pageMaker.setCri(awd_cri);
		awd_pageMaker.setTotalCount(award_list2.size()); 
		
		model.addAttribute("AwardList2", award_list);
		model.addAttribute("AwardDate2", endDate_list);
		model.addAttribute("AwardNum2", phoneNum);
		model.addAttribute("AwardListPage",awd_pageMaker);
		
		model.addAttribute("bid_page_model", bid_page);
		model.addAttribute("reg_page_model", reg_page);
		model.addAttribute("awd_page_model", awd_page);
		
	}


}