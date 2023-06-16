package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.NoticeCriteria;
import com.lgy.smile.dto.NoticeDto;
import com.lgy.smile.dto.NoticePageDTO;
import com.lgy.smile.service.NoticeCommentService;
import com.lgy.smile.service.NoticeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/notice")
public class NoticeController {
	@Autowired private NoticeService service;
	@Autowired private NoticeCommentService commentService;	

	// ★ notice(공지) 목록
	@GetMapping("/list")
	public String noticeList(Model model, @RequestParam(name = "pageNum" , defaultValue = "1") int page) {
		
		NoticeCriteria cri = new NoticeCriteria();
		cri.setPageNum(page);


		
		
		log.info("@# list");
		ArrayList<NoticeDto> list = service.list(page);
		model.addAttribute("list", list);
		log.info( "list 갯수 => " + list.size() );
		
		
		model.addAttribute("pageMaker", new NoticePageDTO(123, cri));
		model.addAttribute("commnetCount", commentService.getCount() );
		
		//=> 나중에 바꿔야함 유저 dto에서 .getRole 써서 넣어줘야함
		model.addAttribute("role", "@admin@@");
		
		return "notice/list";
	}

	
	// ★ notice(공지) 글 쓰기
	@PostMapping("/write")
	public String noticeWrite(@RequestParam HashMap<String, String> params) {
		log.info("@# write");
		service.write(params);
		return "redirect:list";
	}
	
	
	// ★ notice(공지) 글 읽기
	@GetMapping("/read")
	public String noticeRead(@RequestParam HashMap<String, String> params) {
		log.info("@# read");
		service.contentView(params);
		return "notice/read";
	}
	
	
	// ★ notice(공지) 글 수정
	@GetMapping("/edit")
	public String noticeEdit(@RequestParam HashMap<String, String> params) {
		log.info("@# edit");
		service.modify(params);
		return "notice/edit";
	}

	// ★ notice(공지) 글 삭제
	@GetMapping("/delete")
	public String noticeDelete(@RequestParam HashMap<String, String> params) {
		log.info("@# delete");
		service.delete(params);
		return "redirect:list";
		
	}
	// ★ notice(공지) 글 수정
	// ... 작성 ...
	
	
	
}
