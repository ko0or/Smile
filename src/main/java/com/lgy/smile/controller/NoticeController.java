package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dto.NoticeCriteria;
import com.lgy.smile.dto.NoticeDto;
import com.lgy.smile.dto.NoticePageDTO;
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.NoticeCommentService;
import com.lgy.smile.service.NoticeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired private NoticeService service;
	@Autowired private NoticeCommentService commentService;
	@Autowired private DevUtils devUtils;

	// ★ notice(공지) 목록
	@GetMapping("/list")
	public String noticeList(Model model, @RequestParam HashMap<String, String> params, NoticeCriteria cri,HttpSession session) {
		
	/*
		for (int i=0; i <= 130; i++) {
			
			params.put("title", "반복문으로 생성중, "+i+"번째");
			params.put("content", "for 반복문으로 만듬");
			service.write(params);
		}
	
	*/
		
		log.info("@# list");
		ArrayList<NoticeDto> list = service.list(params);
		model.addAttribute("list", list);
		log.info( "list 갯수 => " + list.size() );
		
		
		
		model.addAttribute("pageMaker", new NoticePageDTO( service.getCount() , cri));
		model.addAttribute("commnetCount", commentService.getCount() );
//		model.addAttribute("commnetCount", service.getCount(params) );
		

		
		UserDto user = devUtils.getUserInfo(session);
		
		if ( user != null ) {
			model.addAttribute("role", user.getRole());
		}

		
		return "notice/list";
	}
	@GetMapping("/write")
	public String noticeWrte(HttpSession session) {
		if (
				devUtils.getUserInfo(session) != null 
				&& devUtils.getUserInfo(session).getRole().equals("admin")
			) {
			return "notice/write";
			
		}
		
		
		return "redirect:/notice/list";
		
	}

	
	// ★ notice(공지) 글 쓰기
	@PostMapping("/write")
	public String noticeWrite(Model model, @RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# write");
		service.write(params);	
		return "redirect:list";
		
	}
	
	
	// ★ notice(공지) 글 읽기
	@GetMapping("/read")
	public String noticeRead(Model model, @RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# read");
		service.contentView(params);
		model.addAttribute("board",service.contentView(params));
		
		
		if ( devUtils.getUserInfo(session) != null )  {
			model.addAttribute("role", devUtils.getUserInfo(session).getRole() );
		}
		return "notice/read";
	}
	
	
	// ★ notice(공지) 글 수정
	
	@GetMapping("/edit")
	public String noticeEdit(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# edit");
		NoticeDto dto=	service.contentView(params);
		model.addAttribute("board", dto);
		model.addAttribute("role", devUtils.getUserInfo(session).getRole() );
		model.addAttribute("identity", params.get("identity"));
		return "notice/edit";
	}
	
//	@GetMapping("/modify")
//	public void noticeModify1(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
//		log.info("@# edit");
//		NoticeDto dto = service.contentView(params);
//		if (dto == null) {
//			return "redirect:list";
//		}
//		model.addAttribute("board", dto);
//		
//		
//		// 현재 주소창에 적힌 주소중 가장 마지막에 있는 / 기준으로 바궈치기
//		// http://아피주소:톰캣포트번호/패키지명/notice/modify => modify 가  list 로 바꿔치기 되는 이유
//		return "notice/edit";
//	}
//	진짜 수정하는애
	@PostMapping("/modify")
	public String noticeModify(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# edit");
		model.addAttribute("role", devUtils.getUserInfo(session).getRole() );
		service.modify(params);
		
		
		// 현재 주소창에 적힌 주소중 가장 마지막에 있는 / 기준으로 바궈치기
		// http://아피주소:톰캣포트번호/패키지명/notice/modify => modify 가  list 로 바꿔치기 되는 이유
		return "redirect:list";
	}
	
	

	// ★ notice(공지) 글 삭제
	@GetMapping("/delete")
	public String noticeDelete(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# delete");
		service.delete(params);
		model.addAttribute("role", devUtils.getUserInfo(session).getRole() );
		return "redirect:list";
		
	}
//	@GetMapping("/list")
//	public String getCount(int comment) {
//		log.info("@# getCount");
//		
//		service.getCount(comment);
//		return "redirect:list";
//	// ★ notice(공지) 글 수정
//	// ... 작성 ...
//	}
	
	
}
