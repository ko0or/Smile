package com.lgy.smile.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.common.EmailSender;
import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/main")
public class MainController {		
	
	@Autowired private MainService mainService;
	@Autowired private DevUtils devUtils;
	
	
	// ★ main(피드) 목록
	@GetMapping("/list")
	public String mainList(Model model, HttpSession session) {
		
		
		
		UserDto dto = devUtils.getUserInfo(session);
		if ( dto == null ) {
			model.addAttribute("userIdentity", -1  );			
		} else 
			model.addAttribute("userIdentity", dto.getIdentity()  );
		
		return "feed/list";
	}	


	@GetMapping("/getPosts")
	@ResponseBody
	public ResponseEntity< List<MainBoardDto> > getPosts(HashMap<String, String> params) {		
		
		ArrayList<MainBoardDto> dtos = mainService.list();		
		return ResponseEntity.status(HttpStatus.OK).body( dtos );
	}
	
	
	// ★ main(피드) 글 쓰기 (화면)
	@GetMapping("/write")
	public String mainWrite(HttpSession session) {
		
		// 비로그인 상태라면, list 주소로 돌려보내고
		if ( devUtils.getUserInfo(session) == null ) return "redirect:/user/login"; 		
		
		else // 로그인상태라면, 글 쓰기 화면(views/feed/write.jsp 파일)을 보여준다 -!
		return "feed/write";
	}
	
	
	// ★ main(피드) 글 수정 (화면)
	@GetMapping("/modify") 
	public String mainEdit(@RequestParam HashMap<String, String> params, Model model, HttpSession session) {
		
		// 비로그인 상태라면, list 주소로 돌려보내고
		if ( devUtils.getUserInfo(session) == null ) return "redirect:list"; 		
		
		// 로그인상태라면, 글 쓰기 화면(views/feed/write.jsp 파일)을 보여준다 -!
		
		MainBoardDto dto = mainService.content_view(params, session);
		if ( dto == null ) { return "redirect:list"; }
		
		model.addAttribute("board" , dto );		
		return "feed/edit";
	}
	
		
//========================================================================================	
		
	// ★ main(피드) 글 쓰기 (처리)
	@PostMapping("/write")
	public String mainWrite(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		mainService.write(params, session);
		return "redirect:list";
		
	}

	
	// ★ main(피드) 글 수정 (처리)
	@PostMapping("/modify") 
	public String mainEdit(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		// 글 수정화면 <input type="hidden" value="${identity}"> 였던 값과,   로그인된 세션의 유저보 identity 값이 서로 일치하는지 확인
		// if ( devUtils.getUserInfo(session).getIdentity() == Integer.parseInt(params.get("user")) == true ) {
		if ( devUtils.userIdentityMatchesByJSP(params, session) == true ) {
			mainService.modify(params, session);			
		}
		
		return "redirect:list";
	}
	
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		mainService.delete(params, session);		
		return "redirect:list";
	}
	
}
