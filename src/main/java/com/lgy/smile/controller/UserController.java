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
			return "user/info";
		}
	}
	
	// ★ user(유저) 회원정보 수정화면
	@GetMapping("/modify")
	public String modify(HttpSession session, Model model) {
		if (devUtils.getUserInfo(session) == null) {
			return "redirect:login";
		}else {
			UserDto user = devUtils.getUserInfo(session);
			model.addAttribute("user", user);
			return "user/modifyInfo";
		}
	}
	
	// ★ user(유저) 회원정보 수정처리 (비밀번호와 비밀번호 확인 일치하면 회원정보 수정 후 메인 게시판으로 이동, 아니면 경고창)
	@PostMapping("/modify")
	public ResponseEntity<String> modify(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("UserController ===> modify method ====> start");
		
		String password = params.get("password");
		String password2 = params.get("password2");
		
		if(password.equals(password2) && (password.isEmpty() == false )) {	// 비밀번호가 공백이 아닌 경우
			
			log.info( params.toString() );
			
			userService.modify(params, session);
			
			UserDto user = (UserDto) session.getAttribute("userInfo");
			user.setNickname(params.get("nickname"));		// 새로 설정한 닉네임과 비밀번호를 setter 로 세션 재설정
			user.setPwd(params.get("password"));
			session.setAttribute("userInfo", user);
			
			
			log.info("UserController ===> modify method ====> end");
			
			return ResponseEntity.status(HttpStatus.OK).body("success");
		}else {
			log.info("@# UserController ===> modify ===> else ");
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
		}
	}
	
	// ★ user(유저) 회원가입 화면
	@GetMapping("/logOut")
	public String logOut(HttpSession session) {
		
		log.info("UserController ===> logOut ===> session ");
		session.invalidate();
		
		return "redirect:/main/list";
	}
	
	//================================================================================ >
	
	
	@PostMapping("/isDuplicated")
	public ResponseEntity<String> isDuplicated(@RequestParam HashMap<String, String> params) {
		log.info("UserController ===> isDuplicated method ====> start");
		
		if(userService.isDuplicated(params)!=null) {	// 해당 이메일이 존재하는 경우 경고창
			log.info("UserController ===> isDuplicated method ====> end");
			
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
			
		}else {		// 이메일이 존재하지 않는 경우 회원가입 진행
			log.info("UserController ===> isDuplicated method ====> else");
			
			return new ResponseEntity(HttpStatus.OK);
		}
	}
	
	int AutoCreatedCode;
	
	// 인증번호 발송 동의함과 동시에 인증번호 이메일로 발송
	@PostMapping("/sendCode")
	public ResponseEntity<String> sendCode(@RequestParam HashMap<String, String> params) {
//		AutoCreatedCode = devUtils.emailSenderByCreate("zihye.choi@gmail.com");
		AutoCreatedCode = devUtils.emailSenderByCreate(params.get("id"));
		log.info("발송한 인증번호 받아보기 => " + AutoCreatedCode );
		
		return new ResponseEntity(HttpStatus.OK); 
	}

	// 사용자가 입력한 인증번호를 검증
	@PostMapping("/checkCode")
	public ResponseEntity<String> checkCode(@RequestParam HashMap<String, String> params) {
		log.info("UserController ===> checkCode ===> start");
		
		int inputCode = Integer.parseInt(params.get("code"));
		
		if(inputCode == AutoCreatedCode) {
			log.info("UserController ===> checkCode ===> if true");
			
			return new ResponseEntity(HttpStatus.OK); 
		}else {
			log.info("UserController ===> checkCode ===> if false");
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	// ★ user(유저) 회원가입 화면
	@GetMapping("/createAccount")
	public String userCreateAccount() {
		
		
		
		
		
		return "user/createAccount";
	}
	
//================================================================================ >
	

}





