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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.MainCommentDto;
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.MainCommentService;
import com.lgy.smile.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/main/comment")
public class MainCommentController {
	
	@Autowired private MainCommentService commentService;
	@Autowired private DevUtils devUtils;

	
	
	//=> ★ 댓글 목록 보여주기
	@GetMapping("/getComments") @ResponseBody
	public ResponseEntity<List<MainCommentDto>> getPosts(@RequestParam HashMap<String, String> params) {

		ArrayList<MainCommentDto> dtos = commentService.list(params);
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
	
	
	
	//=> ★ 댓글 새로 작성하기
	@PostMapping("/write") @ResponseBody
	public ResponseEntity<Void> write(@RequestParam HashMap<String, String> params, HttpSession session) {
	
		log.info("@@ 대댓글 테스트중 params => " + params.toString() );
		
		if ( params.get("replyTargetIdentity") == null ) {
			//=> ☆ 대댓글 대상이 없다면,  일반 댓글 생성
			commentService.write(params, session);
			
		} else {
			//=> ☆ 대댓글 대상이 있다면, 대댓글로 생성
			commentService.replyWrite(params, session);
			
		}
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	
	//=> ★ 댓글 내용 수정하기
	@PostMapping("/modify") @ResponseBody
	public ResponseEntity<Void> modfiy(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		commentService.modify(params, session);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	
	//=> ★ 등록된 댓글 삭제하기
	@GetMapping("/delete") @ResponseBody
	public ResponseEntity<Void> delete(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		log.info("@# delete Controller start()");
		log.info("@# modify Controller params => " + params.toString());
		commentService.delete(params, session);
		log.info("@# delete Controller end()");
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
