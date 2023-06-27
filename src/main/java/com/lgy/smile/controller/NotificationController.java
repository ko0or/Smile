package com.lgy.smile.controller;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dto.NotificationDto;
import com.lgy.smile.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notification")
public class NotificationController {

	/* ☆ 실제 처리를 담당하는 서비스 계층  ☆ */
	@Autowired private NotificationService service;
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;
	
	
	
	
	

	
	//=> ★ 알람 생성 메소드 확인은 서비스 계층으로 .. !
	
	
	
	
	
	
	
	

//============================================================================================================== >>
	
	
	@GetMapping("/count")//★=> 알람 목록을 반환
	public ResponseEntity< Integer > count(@RequestParam HashMap<String, String> params, HttpSession session) {		
		if ( devUtils.isLogin(session) == true ) { 
			//=> 만약, 로그인 상태라면 해당 회원의 알람들을 AJAX 응답으로 넘겨주고
			return ResponseEntity.status(HttpStatus.OK).body( service.count(params, session) ); 
		}		
		//=> 그렇지않을땐(비로그인시),  아무것도 넘겨주지 않는다.
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
	
	@GetMapping("/list")//★=> 알람 목록을 반환
	public ResponseEntity< ArrayList<NotificationDto> > list(@RequestParam HashMap<String, String> params, HttpSession session) {		
		if ( devUtils.isLogin(session) == true ) { 
			//=> 만약, 로그인 상태라면 해당 회원의 알람들을 AJAX 응답으로 넘겨주고
			return ResponseEntity.status(HttpStatus.OK).body( service.list(params, session) ); 
		}		
		//=> 그렇지않을땐(비로그인시),  아무것도 넘겨주지 않는다.
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	

	//★=> 알람 삭제하기
	@PostMapping("/delete")//★=> 알람 삭제하기
	public ResponseEntity<Void> delete(@RequestParam HashMap<String, String> params) {
		service.delete(params);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
