package com.lgy.smile.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.service.NoticeCommentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/notice/comment")
public class NoticeCommentController {
	@Autowired private NoticeCommentService commentService;
	
	@PostMapping("/write")
	public String noticeWrite(@RequestParam HashMap<String, String> params) {
		log.info("@# write");
		commentService.writecomment(params);
		return "redirect:list";
	}
	
	
	// ★ notice(공지) 글 읽기
	@GetMapping("/read")
	public String noticeRead(@RequestParam HashMap<String, String> params) {
		log.info("@# read");
		commentService.contentViewcomment(params);
		return "notice/read";
	}
	
	
	// ★ notice(공지) 글 수정
	@GetMapping("/edit")
	public String noticeEdit(@RequestParam HashMap<String, String> params) {
		log.info("@# edit");
		commentService.modifycomment(params);
		return "notice/edit";
	}

	// ★ notice(공지) 글 삭제
	@GetMapping("/delete")
	public String noticeDelete(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# delete");
		commentService.deletecomment(params,session);
		return "redirect:list";
		
	}

}
