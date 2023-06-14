package com.lgy.smile.controller;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dao.UserMapperInterface;
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;

	
	// ★ user(유저) 정보 조회화면
	@GetMapping("/info")
	public String userInfo(HttpSession session) {
		if (session.getAttribute("userInfo") == null ) {
			return "redirect:login";
		}
		return "user/info";
	}

	
	// ★ user(유저) 로그인 화면
	@GetMapping("/login")
	public String userLogin() {
		return "user/login";
	}

		
	
	// ★ user(유저) 회원가입 화면
	@GetMapping("/createAccount")
	public String userCreateAccount() {
		return "user/createAccount";
	}
	
	
//================================================================================ >
	
	@PostMapping("/login")
	public void login(@RequestParam HashMap<String, String> params) {
		
		UserDto dto = userService.login( params );
		
		if ( dto != null ) {
			log.info("@ => 로그인 성공 ( 닉네임 : " + dto.getNickname() + ") " );
		} else {
			log.info("@ => 로그인 실패");
		}
	}
	
}
