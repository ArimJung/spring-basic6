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
		return "login.jsp"; // �׳� ����ϸ� forward ���
	}

	@RequestMapping(value="/login.do", method=RequestMethod.POST) 
	// value�ϳ��� �� ���� �������� method�� POST������� �����ϸ鼭 value �������
	public String selectOneMember(MemberVO vo,MemberDAO dao, HttpSession session) {
		System.out.println("�α�: Login�޼��� ����");
		vo = dao.selectOneMember(vo);

		if(vo == null) {
			return "login.jsp"; 
		}
		else {
			session.setAttribute("member", vo); // �����͸� �����ؼ� ������ ���� MAV ���
			return "redirect:main.do"; // �α��� ������ ����ǰ� main���� �Ѿ�� sandRedirect��� ���
		}
	}

	@RequestMapping("/logout.do") 
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:login.do"; // �α׾ƿ� ���� ���� > �α��� ���� ����
	}

	@RequestMapping("/deleteM.do") 
	public String deleteMember(MemberVO vo, MemberDAO dao) {
		dao.deleteMember(vo);
		return "redirect:logout.do"; // ȸ��Ż�� ���� ���� > �α׾ƿ� ���� ����
	}

	@RequestMapping("/join.do") 
	public String insertMember(MemberVO vo,MemberDAO dao){
		dao.insertMember(vo);
		return "redirect:login.do"; // ȸ������ ���� ���� > �α��� ���� ����
	}
	
	@RequestMapping("/updateM.do") 
	public String updateMember(MemberVO vo,MemberDAO dao) {
		dao.updateMember(vo);
		return "redirect:logout.do"; // �������� ���� ���� > �α׾ƿ� ���� ����
	}
}
