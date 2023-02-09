package org.zerock.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.MemberVO;
import org.zerock.service.MemberService;
import org.zerock.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Controller
@RequestMapping("/member/*")
@AllArgsConstructor
public class MemberController {

	private MemberService mService;
	private ProductService pService;

	@PostMapping("/loginb")
	public String memberLogin(@RequestParam("user_id") String user_id, @RequestParam("pw") String pw,
		HttpServletRequest request, RedirectAttributes rttr) {
		HttpSession session = request.getSession();

		if (user_id == null || user_id == "") {
			rttr.addFlashAttribute("login_message", "아이디 넣어주세요");
			return "redirect:/member/login";
		}
		if (pw == null || pw == "") {
			rttr.addFlashAttribute("login_message", "비밀번호 넣어주세요");
			return "redirect:/member/login";
		}

		MemberVO mVo = mService.MemberRead(user_id);
		if (mVo == null) {
			rttr.addFlashAttribute("login_message", "해당하는 회원이 없습니다.");
			return "redirect:/member/login";
		}
		if (mVo.getUser_id().equals(user_id)) {
			// id 일치

			if (mVo.getPw().equals(pw)) {
				// pw 일치

				session.setAttribute("sessionMember", mVo);
				session.setAttribute("userid", mVo.getUser_id());
				session.setAttribute("sessionUser", mVo.getUser_id());
				return "redirect:/main";

			} else {
				// pw 불일치
				rttr.addFlashAttribute("login_message", "로그인 실패 패스워드 불일치");
				return "redirect:/member/login";
			}

		} else {
			// id 불일치
			rttr.addFlashAttribute("login_message", "로그인 실패 아이디 불일치");
			return "redirect:/member/login";
		}

	}

	@RequestMapping("/login")
	public void memberLogin() {

	}

	@RequestMapping("/logout")
	@ResponseBody
	public ModelAndView memberLogout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		session.invalidate();
		mv.setViewName("redirect:/main");
		return mv;
	}

	@RequestMapping("/join")
	public void memberJoin() {

	}

	@PostMapping("/joinb")
	public String memberJoin(@RequestParam("user_id") String user_id, @RequestParam("pw") String pw,
			@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("birth") String birth, RedirectAttributes rttr) {

		MemberVO mVo = mService.MemberRead(user_id);

		Pattern phone_pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
		Matcher phone_matcher = phone_pattern.matcher(phone);
		
		Pattern birth_pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		Matcher birth_matcher = birth_pattern.matcher(birth);

		if (user_id == null || user_id == "") {
			rttr.addFlashAttribute("join_message", "아이디 넣어주세요.");
			return "redirect:/member/join";
		} else if (pw == null || pw == "") {
			rttr.addFlashAttribute("join_message", "비밀번호 넣어주세요.");
			return "redirect:/member/join";
		} else if (name == null || name == "") {
			rttr.addFlashAttribute("join_message", "이름 넣어주세요.");
			return "redirect:/member/join";
		} else if (mVo != null) {
			rttr.addFlashAttribute("join_message", "존재하는 회원입니다.");
			return "redirect:/member/join";
		} else if (!phone_matcher.matches()) {
			rttr.addFlashAttribute("join_message", "유효하지 않는 전화번호입니다. ex: XXX-XXXX-XXX");
			return "redirect:/member/join";
		} else if (!birth_matcher.matches()) {
			rttr.addFlashAttribute("join_message", "유효하지 않는 날짜입니다. ex: XXXX-XX-XX");
			return "redirect:/member/join";
		} else {
			MemberVO mVo1 = new MemberVO();
			mVo1.setUser_id(user_id);
			mVo1.setPw(pw);
			mVo1.setName(name);
			mVo1.setPhone(phone);
			mVo1.setBirth(birth);

			mService.joinMember(mVo1);
			rttr.addFlashAttribute("message", "회원가입을 했습니다.");
	
			return "redirect:/main";
		}
	}

	@RequestMapping("/resignb")
	public String memberResign(HttpServletRequest request, RedirectAttributes rttr) {

		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		int result = pService.IsExist(userid);
		if (result == 1) {
			rttr.addFlashAttribute("message", "판매 게시글이 존재하여 불가능합니다.");

			return "redirect:/main";
		} else {

			mService.deleteBid_history(userid);
			mService.deleteTrade(userid);
			mService.resignMember(userid);

			session.invalidate();
			rttr.addFlashAttribute("message", "탈퇴 처리를 했습니다.");
			
			return "redirect:/main";
		}
	}
	
	@GetMapping("/resign")
	public void memberResignPage(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		model.addAttribute("mVo",session.getAttribute("sessionMember"));	
	}

	@RequestMapping("/update")
	public void memberUpdate(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		model.addAttribute("mVo", session.getAttribute("sessionMember"));
	}

	@PostMapping("/updateb")
	public String memberUpdate(@RequestParam("pw") String pw, @RequestParam("name") String name,
			@RequestParam("phone") String phone, @RequestParam("birth") String birth, HttpServletRequest request, RedirectAttributes rttr) {
		MemberVO mVo = new MemberVO();
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("sessionUser");

		Pattern phone_pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
		Matcher phone_matcher = phone_pattern.matcher(phone);

		Pattern birth_pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		Matcher birth_matcher = birth_pattern.matcher(birth);

		if (pw == null || pw == "") {
			rttr.addFlashAttribute("update_message", "비밀번호 넣어주세요.");
			return "redirect:/member/update";
		} else if (name == null || name == "") {
			rttr.addFlashAttribute("update_message", "이름 넣어주세요.");
			return "redirect:/member/update";
		} else if (!phone_matcher.matches()) {
			rttr.addFlashAttribute("update_message", "유효하지 않는 전화번호입니다. ex: XXX-XXXX-XXX");
			return "redirect:/member/update";
		} else if (!birth_matcher.matches()) {
			rttr.addFlashAttribute("update_message", "유효하지 않는 날짜입니다. ex: XXXX-XX-XX");
			return "redirect:/member/update";
		} else {
			mVo.setUser_id(user_id);
			mVo.setPw(pw);
			mVo.setName(name);
			mVo.setPhone(phone);
			mVo.setBirth(birth);

			mService.updateMember(mVo);
			session.setAttribute("sessionMember", mVo);
			rttr.addFlashAttribute("message", "회원정보를 수정했습니다.");
			return "redirect:/main";
		}
	}
	@RequestMapping("/NewFile")
	public void test() {
		
	}
}
