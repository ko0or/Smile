package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
		
		log.info("@# list");
//		list로 service단에 list메소드를 params값으로 담는다
		ArrayList<NoticeDto> list = service.list(params);
		
//		model에 담아서 ArrayList의 메개변수 list를 list.jsp로가서 list이름을 사용한다
		model.addAttribute("list", list);
		log.info( "list 갯수 => " + list.size() );
		
//		model에 담아서  service단에 getCount메소드와 NoticeCriteria에 있는 값을 pageMaker이름으로 가져간다
		model.addAttribute("pageMaker", new NoticePageDTO( service.getCount() , cri));
		model.addAttribute("commnetCount", commentService.getCount() );
		
		if ( devUtils.getUserRoleIsAdmin(session) == true ) {
			model.addAttribute("role", "admin" );
		}
		
		
		return "notice/list";
	}
	// ★ notice(공지) 글 쓰기 (화면)
	@GetMapping("/write")
	public String noticeWrte(HttpSession session) {
//		devUtils 에 있는 getUserRoleIsAdmin(관리자인지 확인하는 메소드) 가 사실이면
		if ( devUtils.getUserRoleIsAdmin(session) == true ) { 
//			write로 가겟다
			return "notice/write"; 
		
		} // 관리자가 아니라면  목록 화면으로 추방
		
		return "redirect:/notice/list";
		
	}

	
	// ★ notice(공지) 글 쓰기(실행)
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
		
//		게시글을 읽는 쿼리 실행 하는 contentView 메소드
		service.contentView(params);
		
		// 조회수 증가시키는 쿼리 실행하기 => service.viewUp(params);
		service.viewUp(params);
		
		model.addAttribute("board",service.contentView(params));
		
//		devUtils 에 있는 getUserInfo(로그인이 되어있는)값이 참이면
		if ( devUtils.getUserRoleIsAdmin(session) == true )  {
			
//			model에 role의 값으로 관리자의 정보를 넣어줌
			model.addAttribute("role", devUtils.getUserInfo(session).getRole() );
		}
		return "notice/read";
	}
	
	
	// ★ notice(공지) 글 수정 (화면)
	
	@GetMapping("/edit")
	public String noticeEdit(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# edit");
		
//		
		model.addAttribute("board", service.contentView(params));
		model.addAttribute("identity", params.get("identity"));
		return "notice/edit";
	}
	

	// ★ notice(공지) 글 수정 (실행)
	@PostMapping("/modify")
	public String noticeModify(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# edit");
		if ( devUtils.getUserRoleIsAdmin(session) == true ) { 
			service.modify(params);
			
			params.get("pageNum");
			// (케이스 1번) redirect:read?identity= + params.get("identity")
			// (케이스 2번) or 목록으로 갈꺼면  페이지 번호로 돌아가게끔  ( ex-> 3페이지에있는 공지에 있는 글 수정후, 목록갈땐 1페이지가 아닌  원래 보고있던 3페이지로 가게끔 )
			//               redirect:list   =>   pageNum 이 null 이라서   if 조건문에 따라  초기값 그거 설정해둕걸로 들어감 ,  결론은  list로 보내면 1 페이지 감 
			return "notice/edit";
			
		}
//		model.addAttribute("role", devUtils.getUserInfo(session).getRole() );
		
		
		// 현재 주소창에 적힌 주소중 가장 마지막에 있는 / 기준으로 바궈치기
		// http://아피주소:톰캣포트번호/패키지명/notice/modify => modify 가  list 로 바꿔치기 되는 이유
		return "redirect:list";
	}
	
	

	// ★ notice(공지) 글 삭제
	@GetMapping("/delete")
	public String noticeDelete(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# delete");
		if ( devUtils.getUserRoleIsAdmin(session) == true ) { 
			service.delete(params);
		}
//		model.addAttribute("role", devUtils.getUserInfo(session).getRole() );
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
