package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/main")
public class MainController {		
	
	@Autowired
	private MainService mainService;
	
	
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
	
	
	// ★ main(피드) 글 수정 (화면)
	@GetMapping("/modify") // <== 로그인된 상태인지 확인필요
	public String mainEdit(@RequestParam HashMap<String, String> params, Model model) {
		
		 model.addAttribute("content" , mainService.content_view(params));		
		return "feed/edit";
	}
	
		
//========================================================================================	
		
	// ★ main(피드) 글 쓰기 (처리)
	@PostMapping("/write")
	public String mainWrite(@RequestParam HashMap<String, String> params) {
		
		mainService.write(params);
		return "redirect:list";
		
	}

	
	// ★ main(피드) 글 수정 (처리)
	@PostMapping("/modify") //
	public String mainEdit(@RequestParam HashMap<String, String> params) {

		mainService.modify(params);
		return "redirect:list";
	}
	
	

	@GetMapping("/getPosts")
	@ResponseBody
	public ResponseEntity< List<MainBoardDto> > getPosts(@RequestParam HashMap<String, String> params) {		
		
		ArrayList<MainBoardDto> dtos = mainService.list();
		return ResponseEntity.status(HttpStatus.OK).body( dtos );
	}
	
	
	
	// ★ main(피드) 글 수정 .
	// ... 내용 ... 



	// ★ main(피드) 글 삭제
	// ... 내용 ...
	
	@GetMapping("/delete")
	public String delete(@RequestParam HashMap<String, String> params) {
	 
		mainService.delete(params);
		
		
		return "redirect:list";
	}
	
}
