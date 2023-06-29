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
import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.NoticeMapperinterface;
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
	public String noticeList(Model model,@RequestParam HashMap<String,String> params, NoticeCriteria cri,HttpSession session) {
		
		
		String searchKeyword = ""; 
//		검색창에 값이 있으면(검색 햇으면)
		if ( params.get("searchKeyword") != null ) {
//			키워드를 가지고 와서
			searchKeyword = params.get("searchKeyword"); 
		}
//		담는다.
		model.addAttribute("searchKeyword", searchKeyword);
//		params에 넣어서 jsp파일로 가져감
		params.put("searchKeyword", searchKeyword);
		
		
		log.info("@# list");
//		list로 service단에 list메소드를 params값으로 담는다
		ArrayList<NoticeDto> list = service.list(params);
		
//		model에 담아서 ArrayList의 메개변수 list를 list.jsp로가서 list이름을 사용한다
		model.addAttribute("list", list);
		log.info( "list 갯수 => " + list.size() );
		
//		model에 담아서  service단에 getCount메소드와 NoticeCriteria에 있는 값을 pageMaker이름으로 가져간다(페이징 처리를 위해)
		model.addAttribute("pageMaker", new NoticePageDTO( service.getCount(params) , cri));
		log.info("@@# service.getCount() 갯수 => " + service.getCount(params));
		
//		(댓글 갯수 확인)
		model.addAttribute("commnetCount", commentService.getCount() );
		
		if ( devUtils.getUserRoleIsAdmin(session) == true ) {
			model.addAttribute("role", "admin" );
		}
		
		
		return "notice/list";
	}
	// ★ notice(공지) 글 쓰기 (화면)
	@GetMapping("/write")
	public String noticeWrte(HttpSession session) {
//		devUtils 에 있는 getUserRoleIsAdmin(관리자인지 확인하는 메소드) 가 사실이면(관리자로 로그인 햇을때만 글을 작성할수 있게 하기 위해)
		if ( devUtils.getUserRoleIsAdmin(session) == true ) { 
//			write로 가겟다
			return "notice/write"; 
		
		} // 관리자가 아니라면  목록 화면으로 추방
		return "redirect:/notice/list";
		
	}

	
	// ★ notice(공지) 글 쓰기(실행)
	@PostMapping("/write")
	public String noticeWrite(Model model, @RequestParam HashMap<String,String> params, HttpSession session) {
		log.info("@# write");
		service.write(params);	
		return "redirect:list";
		
	}
	
	
	// ★ notice(공지) 글 읽기
	@GetMapping("/read")
	public String noticeRead(Model model, @RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# read");
		
		
		// 조회수 증가시키는 쿼리 실행하기 => service.viewUp(params);
		service.viewUp(params);
		
//		service단에 있는 contentView 메소드를 가지고 와서 board라는 이름으로 model에 넣어 준다.
		model.addAttribute("board",service.contentView(params, session));
		
//		UserDto에 로그인된 유저 정보
		UserDto user = devUtils.getUserInfo(session);
		
		if (user != null) {
//		그 유저 정보에 identity값을 model에 넣어서 userIdentity 값으로 가지고 간다
			model.addAttribute("userIdentity", devUtils.getUserInfo(session).getIdentity());
			
		}else {
//			로그인을 하지 않았다면(getUserInfo)메소드에  값이 null인 거임 그럼 -1(identity값이 음수일수없으니까)
			model.addAttribute("userIdentity",-1);
			
		}
		
//		devUtils 에 있는 getUserRoleIsAdmin(관리자)값이 참이면
		if ( devUtils.getUserRoleIsAdmin(session) == true )  {
			
//			model에 role의 값으로 관리자의 정보를 넣어줌(수정,삭제는 관리자만 볼수 있게 하기 위함)
			model.addAttribute("role", devUtils.getUserInfo(session).getRole() );
		}
		return "notice/read";
	}
	
	
	// ★ notice(공지) 글 수정 (화면)
	
	@GetMapping("/edit")
	public String noticeEdit(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# edit");
		
		
//		service단에 있는 contentView 메소드를 가지고 와서 board라는 이름으로 model에 넣어 준다.
		model.addAttribute("board", service.contentView(params));
		
//		params.get=>쿼리스트링을 쓰기 위한것
//		그래서 params.get("identity")=>identity컬럼(??)을 가져와서 identity에 넣는다(게시글의 PK값을 가져가서 수정하기 위함)
		model.addAttribute("identity", params.get("identity"));
		return "notice/edit";
	}
	

	// ★ notice(공지) 글 수정 (실행)
	@PostMapping("/modify")
	public String noticeModify(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# edit");
//		진짜 관리자라면
		if ( devUtils.getUserRoleIsAdmin(session) == true ) { 
//			service단에 있는 modify메소드를 불러와서 수정 후
			service.modify(params);
//			PK값을가지고 다시 read로 가라
			return "redirect:read?identity=" + params.get("identity");
			
		}
//		관리자가 아니라면 그냥 리스트에 있어라
		return "redirect:list";
	}
	
	

	// ★ notice(공지) 글 삭제
	@GetMapping("/delete")
	public String noticeDelete(Model model,@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# delete");
//		진짜 관리자라면
		if ( devUtils.getUserRoleIsAdmin(session) == true ) { 
//			삭제하고
			service.delete(params);
		}
//		리스트로 다시 가라
		return "redirect:list";
		
	}
	
//	★ 새 게시글 확인여부
	@GetMapping("/confirmedCheck")
	public ResponseEntity<Integer> confirmedCheck(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		// 비로그인시 0, 로그인했고 공지도 봤었다면 1(알람 안뜸), 로그인했지만 공지안봤으면 -1(알람뜸)
		if ( devUtils.isLogin(session) == true ) {
//			유저pk값을 user로 받아라
			params.put("user", devUtils.getUserIdentityToString(session));
//			서비스 단 연결
			int checked = service.confirmedCheck(params);
			return ResponseEntity.status(HttpStatus.OK).body( checked ); 
		}
		// HttpStatus.BAD_REQUEST 로 하는게 정석이긴한데.. 그럼 콘솔창에 빨간 글자가 새겨짐
//		비로그인
		return ResponseEntity.status(HttpStatus.OK).body( 0 );
	}
}
