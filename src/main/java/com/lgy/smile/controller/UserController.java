package com.lgy.smile.controller;
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
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired private UserService userService;
	@Autowired private DevUtils devUtils;

//================================================================================ >
	
	// ★ user(유저) 로그인 화면
	@GetMapping("/login")
	public String userLogin() {
		
//		int number = devUtils.emailSenderByCreate("taehwa10404@naver.com");
//		log.info("발송한 인증번호 받아보기 => " + number );
				
		return "user/login";
	}

	// ★ user(유저) 로그인 처리 (아이디 조회되고 비밀번호 일치하면 로그인 완료 후 메인 게시판으로 이동, 아니면 다시 로그인 화면)
	@PostMapping("/login")
//	public ResponseEntity<Void> login(@RequestParam HashMap<String, String> params, HttpSession session) {
	public ResponseEntity<Integer> login(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		UserDto dto = userService.login( params );
		
		if ( dto != null ) {
			
			if( dto.getPwd().equals(params.get("password"))) {
				log.info("@ => 로그인 성공 ( 닉네임 : " + dto.getNickname() + ") " );
				
				session.setAttribute("userInfo", dto);
				session.setMaxInactiveInterval(1800);	// 세션 30분 설정 
				log.info("@ => session " + session.getAttribute("userInfo"));
				
//				log.info("@# HttpStatus.OK ===>"+ ResponseEntity.status(HttpStatus.OK).build());
//				return ResponseEntity.status(HttpStatus.OK).build();
//				return new ResponseEntity<Void>(HttpStatus.OK);
				
				log.info("@# === body(200)=== >" + ResponseEntity.status(HttpStatus.OK).body(200));
				return ResponseEntity.status(HttpStatus.OK).body(200);
				
			} else {
				log.info("@ => 로그인 실패 => 비밀번호 불일치");
				
//				log.info("@# HttpStatus.NOT_FOUND ===>"+ ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
				
				log.info("@# === body(404)=== >" + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(404));
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(404);
			}
			
		}else {
			log.info("@ => 로그인 실패 => 회원 아이디 조회 불가");
			
			
//			log.info("@# HttpStatus.BAD_REQUEST ===>"+ ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
					
			log.info("@# === body(400)=== >" + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(400));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(400);
		}
	}	
	
//================================================================================ >
	
	// ★ user(유저) 회원정보 조회화면
	@GetMapping("/info")
	public String userInfo(HttpSession session, Model model) {
		
		log.info("GET방식으로 info 사이트로 들어옴");
		
		if (session.getAttribute("userInfo") == null ) {
			return "redirect:login";
		}else {
			UserDto user = (UserDto) session.getAttribute("userInfo");
			model.addAttribute("user", user);
		}
		
		return "user/info";
	}
	
	// ★ user(유저) 회원정보 수정처리 (비밀번호와 비밀번호 확인 일치하면 회원정보 수정 후 메인 게시판으로 이동, 아니면 경고창)
	@PostMapping("/modify")
	public ResponseEntity<String> modify(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("UserController ===> modify method ====> start");
		
		String password = params.get("password");
		String password2 = params.get("password2");
		log.info(password);
		log.info(password2);
		
		if(password.equals(password2)) {
			
			userService.modify(params, session);
			
			log.info("UserController ===> modify method ====> end");
			
			//return "/smile/main/list";
			return ResponseEntity.status(HttpStatus.OK).body("success");
		}else {
			log.info("@# UserController ===> modify ===> else ");
			//return "login";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
		}
	}


	// ★ user(유저) 회원가입 화면
	@GetMapping("/createAccount")
	public String userCreateAccount() {
		return "user/createAccount";
	}
	
//================================================================================ >
	

}





