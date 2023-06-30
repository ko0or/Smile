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
import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/main")
public class MainController {		

/*

	# 작성 순서 (주석단위)
	
	 		.. 화면
	 		.. 처리
	 		.. AJAX 응답
	
 */
	
	
	@Autowired private MainService mainService;
	@Autowired private DevUtils devUtils;
	
//========================================================================================	
	
	
	// ★ 목록 (화면)
	@GetMapping("/list")
	public String mainList(@RequestParam HashMap<String, String> params, Model model, HttpSession session) {
		
		UserDto dto = devUtils.getUserInfo(session);
		if ( dto == null ) { model.addAttribute("userIdentity", -1  );			
		} else {  model.addAttribute("userIdentity", dto.getIdentity()  ); }
		
		//☆=> 닉네임에 대한 검색 결과 출력하기
		if ( params.get("searchByNickname") == null ) { params.put("searchByNickname", "\"\""); }
		model.addAttribute("searchByNickname", params.get("searchByNickname"));  
		
		//☆=> 해당 닉네임으로 작성된 게시글 번호에 대한 검색 결과 출력하기 
		if ( params.get("searchByBoardIdentity") == null ) { params.put("searchByBoardIdentity", "\"\""); }
		model.addAttribute("searchByBoardIdentity", params.get("searchByBoardIdentity"));  
		
		//☆=> 해당 유저가 누른 좋아요된 글들만 보기 
		if ( params.get("searchByLikeUser") == null ) { params.put("searchByLikeUser", "\"\""); }
		model.addAttribute("searchByLikeUser", params.get("searchByLikeUser"));  
		
		
		return "feed/list";
	}	

	
	// ★ main(피드) 글 쓰기 (화면)
	@GetMapping("/write")
	public String mainWrite(HttpSession session) {
		
		//☆=> 비로그인 상태라면, list 주소로 돌려보내고
		if ( devUtils.getUserInfo(session) == null ) return "redirect:/user/login"; 		
		
		//☆=> 로그인상태라면, 글 쓰기 화면(views/feed/write.jsp 파일)을 보여준다 -!
		else 
		return "feed/write";
	}
	
	
	// ★ main(피드) 글 수정 (화면)
	@GetMapping("/modify") 
	public String mainEdit(@RequestParam HashMap<String, String> params, Model model, HttpSession session) {
		
		//☆=>  비로그인 상태라면, list 주소로 돌려보내고
		if ( devUtils.getUserInfo(session) == null ) return "redirect:list"; 		
		
		//☆=>  로그인상태라면, 글 쓰기 화면(views/feed/write.jsp 파일)을 보여준다 -!		
		MainBoardDto dto = mainService.content_view(params, session);
		if ( dto == null ) { return "redirect:list"; }
		
		//☆=> 수정할 내용을 화면에 보여주기위해 모델 사용
		model.addAttribute("board" , dto );		
		return "feed/edit";
	}
	
		
//========================================================================================	
		
	// ★ main(피드) 글 쓰기 (처리)
	@PostMapping("/write")
	public String mainWrite(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		//☆=> 로그인 상태일때만 글 작성이 완료되게끔 처리 
		if ( devUtils.isLogin(session) == true ) {
			mainService.write(params, session);
		}
 
		return "redirect:list";		
	}

	
	// ★ main(피드) 글 수정 (처리)
	@PostMapping("/modify") 
	public String mainEdit(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		//☆=> 히든타입에 input 으로 숨겨놨던 값(글 작성자)과 현재 로그인된 유저가 일치하는지 검사 ( 변조 방지 ) 
		if ( devUtils.userIdentityMatchesByJSP(params, session) == true ) {
			
			//☆=> 작성자 본인이 맞다고 확인되었을때만 수정되도록 처리 
			mainService.modify(params, session);			
		}
		
		return "redirect:list";
	}
	
	
	// ★ main(피드) 글 삭제 (처리)
	@GetMapping("/delete")
	public String delete(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		//☆=> 게시글 삭제 (  본인여부는 서비스 단에서 확인후 처리 )
		mainService.delete(params, session);		
		return "redirect:list";
	}

	
	
	
//========================================================================================	

	// ★ main(피드) 게시글 목록 화면으로 전달하기 (AJAX 응답)	
	@ResponseBody
	@GetMapping("/getPosts") 
	public ResponseEntity< List<MainBoardDto> > getPosts(@RequestParam HashMap<String, String> params) {		

		//☆=> board/list 페이지로 접속했을때,  화면에 보여줄 데이터를 찾아서 리턴 ( ajax 요청에 대한 응답 )
		ArrayList<MainBoardDto> dtos = mainService.list(params);		
		return ResponseEntity.status(HttpStatus.OK).body( dtos );
	}
	

	// ★ main(피드) 좋아요 버튼 처리 결과를  화면으로 전달하기(AJAX 응답)
	@GetMapping("/like_toggle")
	public ResponseEntity<Void> likeToggle(@RequestParam HashMap<String, String> params, HttpSession session) {
	
		//☆=> 게시글에 좋아요 버튼 처리 ( 토글 형식 )
		mainService.like_toggle(params, session);
		return ResponseEntity.status(HttpStatus.OK).build();
		
	}
}
