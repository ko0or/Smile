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

//=========================== 회원 로그인 ===================================================== >
	
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
		
		// 사용자가 입력한 비밀번호
		String strFromInput = params.get("password");
		log.info("strFromInput ===> " + strFromInput);
		
		// 데이터베이스에서 조회한 비밀번호
		UserDto dto = userService.login( params );
		String strFromDatabase = dto.getPwd();
		log.info("strFromDatabase ===> " + strFromDatabase);
		
		// 비교하여 리턴값이 true 인 경우 사용자 입력 비밀번호가 데이터베이스에 암호화되어 저장된 비밀번호와 일치
		boolean passwordMatches = devUtils.passwordMatches(strFromInput, strFromDatabase);
		log.info("passwordMatches ===> " + passwordMatches);
		
		if ( dto != null ) {
			
//			if( dto.getPwd().equals(params.get("password"))) {
			if( passwordMatches ) {
				log.info("@ => 로그인 성공 ( 닉네임 : " + dto.getNickname() + ") " );
				
				session.setAttribute("userInfo", dto);
				session.setMaxInactiveInterval(1800);
				log.info("@ => session " + session.getAttribute("userInfo"));
				
				log.info("@# === body(200)=== >" + ResponseEntity.status(HttpStatus.OK).body(200));
				return ResponseEntity.status(HttpStatus.OK).body(200);
				
			} else {
				log.info("@ => 로그인 실패 => 비밀번호 불일치");
				
				log.info("@# === body(404)=== >" + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(404));
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(404);
			}
			
		}else {
			log.info("@ => 로그인 실패 => 회원 아이디 조회 불가");
			
			log.info("@# === body(400)=== >" + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(400));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(400);
		}
	}	
	
//=================== 임시 비밀번호 발급 ============================================================= >
	
	@PostMapping("/sendTempPwd")
	public ResponseEntity<String> sendTempPwd(@RequestParam HashMap<String, String> params) {
		
		int tempPassword = devUtils.emailSenderByForget(params.get("id"));
		log.info("임시 비밀번호를 발송할 이메일 주소 ===> " +params.get("id"));
		log.info("발송한 임시 비밀번호 받아보기 ===> "+ tempPassword);
		
		//====================== 여기서 DB의 비밀번호를 임시 비밀번호로 변경해야 함 ===============
		
		// 임시 비밀번호를 암호화
		String password = devUtils.StringToPassword(Integer.toString(tempPassword));
		params.put("password", password);
		
		userService.tempPwd(params);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
//=================== 회원정보 조회 및 수정 ============================================================= >
	
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
		
		// 사용자가 입력한 "현재" 비밀번호
		String strFromInput = params.get("password");
		log.info("UserController ===> modify ===> 현재 비밀번호(strFromInput) ===> " + strFromInput);
		
		// 세션 값으로 파라미터에 id 추가
		UserDto user = new DevUtils().getUserInfo(session);
		log.info("user 세션을 통해 받아온 사용자 DTO ===> " + user);
		params.put("id", user.getId() );
		log.info("user 세션을 통해 받아온 사용자 DTO 의 getId 를 통해 받은 ID값 ===> " + user.getId());
		log.info("=========================================================");
		
		// 데이터베이스에서 조회한 비밀번호
		UserDto dto = userService.login( params );
		log.info("modify ===> dto ===> " + dto);
		
		String strFromDatabase = dto.getPwd();
		log.info("UserController ===> modify ===> 데이터베이스에 저장된 비밀번호(strFromDatabase) ===> " + strFromDatabase);
		
		// 비교하여 리턴값이 true 인 경우 사용자 입력 비밀번호가 데이터베이스에 암호화되어 저장된 비밀번호와 일치
		boolean passwordMatches = devUtils.passwordMatches(strFromInput, strFromDatabase);
		log.info("UserController ===> modify ===> passwordMatches ===> " + passwordMatches);

		// 새 비밀번호와 새 비밀번호 확인
		String password = params.get("newPassword");
		String password2 = params.get("newPassword2");
		log.info("새 비밀번호(password) ===> " + password);
		log.info("새 비밀번호 확인(password2) ===> " + password2);

		log.info("============ if 문 시작 =============================================");
		// 현재 비밀번호와 데이터베이스 조회 비밀번호가 일치하면
		if (passwordMatches) {
			
			// 새 비밀번호와 새 비밀번호 확인 란에 공백이 아닌 경우
			if(password.equals(password2) && (password.isEmpty() == false )) {
				
				log.info( params.toString() );
				
				log.info("============ UserService 이전 =============================================");
				
				userService.modify(params, session);
				
				log.info("============ UserService 다음 =============================================");
				
//				UserDto user = (UserDto) session.getAttribute("userInfo");
				// 새로 설정한 닉네임과 비밀번호를 setter 로 세션 재설정
				user.setNickname(params.get("nickname"));
				
				// 입력받은 비밀번호를 암호화하는 메소드(StringToPassword) 호출
				password = devUtils.StringToPassword(params.get("newPassword"));
				
				log.info("새 비밀번호 ===> " + params.get("newPassword"));
				log.info("새 비밀번호 암호화 ===> " + password);
				
				// params 값을 replace 로 바꿔치기
				params.replace("password", password);
				
				log.info("새 비밀번호 암호화를 파라미터(password)로 교체한 후 ===> " + params.get("password"));

				// 암호화된 비밀번호를 setter 로 세션값에 셋팅
//				user.setPwd(password);
				
				userService.modify(params, session);
				
//				session.setAttribute("userInfo", user);
				
				log.info("UserController ===> modify method ====> end");
				
				return ResponseEntity.status(HttpStatus.OK).body("success");
			}else {
				log.info("@# UserController ===> modify ===> else ");
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
			}
		} else {
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
	
	//============== 회원가입 처리 ================================================================== >
	
	// 이메일 중복확인
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
	
	// ★ user(유저) 회원가입 처리
	@PostMapping("/createAccount")
	public String userCreateAccount(@RequestParam HashMap<String, String> params) {
		log.info("UserController ===> createAccount ===> start");
		
		// 입력받은 비밀번호를 암호화하는 메소드(StringToPassword) 호출
		String password = devUtils.StringToPassword(params.get("password"));
		
		// params 값을 replace 로 바꿔치기 한 후 회원가입 진행
		params.replace("password", password);
		userService.register(params);
		
		return "user/login";
	}
	
//========================= 회원 탈퇴처리 ======================================================= >
	
	// ★ user(유저) 회원탈퇴 처리
	@PostMapping("/unregister")
	public String delete(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("UserController ===> delete ===> start");

//		UserDto user = (UserDto) session.getAttribute("userInfo");
//		log.info("UserDto user ===> "+ user);
		
//		String nickname = params.get("nickname");
//		log.info("UserController ===> delete ===> nickname===> " +nickname);
		
		userService.delete(params, session);
		
		log.info("UserController ===> delete ===> end");
		
		return "redirect:/user/logOut";
	}

}

