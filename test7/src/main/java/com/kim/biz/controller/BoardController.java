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
@SessionAttributes("bData") // null로 업데이트 되는 것을 방지해보자
// bData 라는 이름으로 데이터를 주고 받고 있음
public class BoardController {
	
	@ModelAttribute("scMap") //모델에 scMap이라는 이름으로 데이터 저장
	public Map<String,String> searchConditionMap(){
		System.out.println("로그: modelAttribute 실행");
		Map<String,String> scMap = new HashMap<String,String>();
		scMap.put("제목", "TITLE"); // view에서 어떤 값들이 어떻게 보여지는지 C는 알고있다
		scMap.put("작성자", "WRITER");
		return scMap;
	}
	
	@RequestMapping("/main.do")
	public String selectAllBoard(
			@RequestParam(value="searchCondition",defaultValue="TITLE",required=false)String searchCondition,
			@RequestParam(value="searchContent",defaultValue="",required=false)String searchContent,
			BoardVO vo, BoardDAO dao, Model model) {
		
		System.out.println("검색조건: "+searchCondition);
	    System.out.println("검색어: "+searchContent);
	    
	    vo.setTitle(searchCondition);
	    vo.setContent(searchContent);

		model.addAttribute("bDatas", dao.selectAllBoard(vo)); // 이전에 request단위로 사용하던 것을 MAV 로 사용
		return "main.jsp";
	}
	
	@RequestMapping("/board.do")
	public String selectOneBoard(BoardVO vo, BoardDAO dao, Model model) {
		System.out.println("로그: "+vo);
		model.addAttribute("bData", dao.selectOneBoard(vo));
		return "board.jsp";
	}
	
	@RequestMapping("/updateB.do")
	public String updateBoard(@ModelAttribute("bData")BoardVO vo, BoardDAO dao, Model model) {
		System.out.println("updateB.do 로그: "+vo);
		dao.updateBoard(vo);
		return "main.do";
	}
	@RequestMapping("/insertB.do")
	public String insertBoard(BoardVO vo, BoardDAO dao, Model model) {
		System.out.println("insertB.do 로그: "+vo);
		dao.insertBoard(vo);
		return "main.do";
	}
	@RequestMapping("/deleteB.do")
	public String deleteBoard(BoardVO vo, BoardDAO dao, Model model) {
		System.out.println("deleteB.do 로그: "+vo);
		dao.deleteBoard(vo);
		return "main.do";
	}
}
