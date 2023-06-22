package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;


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

import com.lgy.smile.dto.NoticeCommentDto;
import com.lgy.smile.service.NoticeCommentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/notice/comment")
public class NoticeCommentController {
	@Autowired private NoticeCommentService commentService;
	
//	@GetMapping("/list")
//	public int  count() {
//		return commentService.getCount();
//	}
	
	
	@PostMapping("/write")
	@ResponseBody
	public ResponseEntity<String> noticeWrite(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# write");
		commentService.writecomment(params, session);		
		
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}
	
	
	// ★ notice(공지) 댓글 읽기
	@GetMapping("/read")
	public ResponseEntity< ArrayList<NoticeCommentDto> > noticeRead(Model model, @RequestParam HashMap<String, String> params) {
		return ResponseEntity.status(HttpStatus.OK).body( commentService.contentViewcomment(params) );
	}
	
	
	// ★ notice(공지) 댓글 수정
	@PostMapping("/edit")
	@ResponseBody
	public ResponseEntity<String> noticeEdit(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# edit");
		commentService.modifycomment(params, session);
		log.info("edit 들어오니?");
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// ★ notice(공지) 댓글 삭제
	@GetMapping("/delete")
	public ResponseEntity<String> noticeDelete(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# delete");
		commentService.deletecomment(params,session);
		return ResponseEntity.status(HttpStatus.OK).build();
		
	}

}
