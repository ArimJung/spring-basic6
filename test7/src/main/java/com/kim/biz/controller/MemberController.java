package com.kim.biz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kim.biz.member.MemberVO;
import com.kim.biz.member.impl.MemberDAO;

@Controller
public class MemberController {

	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String index() {
		return "login.jsp"; // 그냥 사용하면 forward 방식
	}

	@RequestMapping(value="/login.do", method=RequestMethod.POST) 
	// value하나만 쓸 때는 생략가능 method를 POST방식으로 지정하면서 value 써줘야함
	public String selectOneMember(MemberVO vo,MemberDAO dao, HttpSession session) {
		System.out.println("로그: Login메서드 실행");
		vo = dao.selectOneMember(vo);

		if(vo == null) {
			return "login.jsp"; 
		}
		else {
			session.setAttribute("member", vo); // 데이터를 저장해서 가야할 때는 MAV 사용
			return "redirect:main.do"; // 로그인 로직이 종료되고 main으로 넘어가니 sandRedirect방식 사용
		}
	}

	@RequestMapping("/logout.do") 
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:login.do"; // 로그아웃 로직 종료 > 로그인 로직 시작
	}

	@RequestMapping("/deleteM.do") 
	public String deleteMember(MemberVO vo, MemberDAO dao) {
		dao.deleteMember(vo);
		return "redirect:logout.do"; // 회원탈퇴 로직 종료 > 로그아웃 로직 시작
	}

	@RequestMapping("/join.do") 
	public String insertMember(MemberVO vo,MemberDAO dao){
		dao.insertMember(vo);
		return "redirect:login.do"; // 회원가입 로직 종료 > 로그인 로직 시작
	}
	
	@RequestMapping("/updateM.do") 
	public String updateMember(MemberVO vo,MemberDAO dao) {
		dao.updateMember(vo);
		return "redirect:logout.do"; // 정보수정 로직 종료 > 로그아웃 로직 시작
	}
}
