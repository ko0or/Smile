package com.lgy.smile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {		
	
	// ★ main(피드) 목록
	@GetMapping("/list")
	public String mainList() {
		return "feed/list";
	}	

	
	// ★ main(피드) 글 쓰기 (화면)
	@GetMapping("/write") // <== 로그인된 상태인지 확인필요
	public String mainWrite() {
		return "feed/write";
	}
	
	
	// ★ main(피드) 글 쓰기 (처리)
	// @PostMapping("/write") 
	

	
	// ★ main(피드) 글 읽기 .
	// ... 내용 ... 
	
	
	// ★ main(피드) 글 수정 .
	// ... 내용 ... 



	// ★ main(피드) 글 삭제
	// ... 내용 ...
	
	
}
