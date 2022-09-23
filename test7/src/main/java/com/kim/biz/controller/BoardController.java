package com.kim.biz.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kim.biz.board.BoardVO;
import com.kim.biz.board.impl.BoardDAO;

@Controller
@SessionAttributes("bData") // null�� ������Ʈ �Ǵ� ���� �����غ���
// bData ��� �̸����� �����͸� �ְ� �ް� ����
public class BoardController {
	
	@ModelAttribute("scMap") //�𵨿� scMap�̶�� �̸����� ������ ����
	public Map<String,String> searchConditionMap(){
		System.out.println("�α�: modelAttribute ����");
		Map<String,String> scMap = new HashMap<String,String>();
		scMap.put("����", "TITLE"); // view���� � ������ ��� ���������� C�� �˰��ִ�
		scMap.put("�ۼ���", "WRITER");
		return scMap;
	}
	
	@RequestMapping("/main.do")
	public String selectAllBoard(
			@RequestParam(value="searchCondition",defaultValue="TITLE",required=false)String searchCondition,
			@RequestParam(value="searchContent",defaultValue="",required=false)String searchContent,
			BoardVO vo, BoardDAO dao, Model model) {
		
		System.out.println("�˻�����: "+searchCondition);
	    System.out.println("�˻���: "+searchContent);
	    
	    vo.setTitle(searchCondition);
	    vo.setContent(searchContent);

		model.addAttribute("bDatas", dao.selectAllBoard(vo)); // ������ request������ ����ϴ� ���� MAV �� ���
		return "main.jsp";
	}
	
	@RequestMapping("/board.do")
	public String selectOneBoard(BoardVO vo, BoardDAO dao, Model model) {
		System.out.println("�α�: "+vo);
		model.addAttribute("bData", dao.selectOneBoard(vo));
		return "board.jsp";
	}
	
	@RequestMapping("/updateB.do")
	public String updateBoard(@ModelAttribute("bData")BoardVO vo, BoardDAO dao, Model model) {
		System.out.println("updateB.do �α�: "+vo);
		dao.updateBoard(vo);
		return "main.do";
	}
	@RequestMapping("/insertB.do")
	public String insertBoard(BoardVO vo, BoardDAO dao, Model model) {
		System.out.println("insertB.do �α�: "+vo);
		dao.insertBoard(vo);
		return "main.do";
	}
	@RequestMapping("/deleteB.do")
	public String deleteBoard(BoardVO vo, BoardDAO dao, Model model) {
		System.out.println("deleteB.do �α�: "+vo);
		dao.deleteBoard(vo);
		return "main.do";
	}
}
