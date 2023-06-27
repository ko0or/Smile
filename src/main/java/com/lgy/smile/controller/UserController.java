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
	//zhtester
	@Autowired private UserService userService;
	@Autowired private DevUtils devUtils;


//=========================== 회원 로그인 ===================================================== >
	
	// 로그인 화면
	@GetMapping("/login")
	public String userLogin() {
		return "user/login";
	}

	// userService 의 login 함수 호출하여 로그인 처리 (아이디 조회되고 비밀번호 일치하면 로그인 처리 후 메인 게시판으로 이동, 아니면 다시 로그인 화면)
	@SuppressWarnings("unused")
	@PostMapping("/login")
	public ResponseEntity<Integer> login(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("UserController ===> login ===> start");
		
		// 사용자가 입력한 비밀번호
		String strFromInput = params.get("password");
		
		// 데이터베이스에서 조회한 비밀번호
		UserDto dto = userService.login( params );
		String strFromDatabase = dto.getPwd();
		
		// 비교하여 리턴값이 true 인 경우 사용자 입력 비밀번호가 데이터베이스에 암호화되어 저장된 비밀번호와 일치
		boolean passwordMatches = devUtils.passwordMatches(strFromInput, strFromDatabase);
		
		// DTO 가 NULL 이 아닐 때 => 즉, 해당 아이디가 DB에서 조회될 때
		if ( dto != null ) {
			
			// 입력 비밀번호와 DB 조회 비밀번호가 일치하면 로그인 처리
			if( passwordMatches ) {
				
				// 사용자 DTO 로 userInfo 라는 이름의 세션 만들고 만료 시간(30분) 설정
				session.setAttribute("userInfo", dto);
				session.setMaxInactiveInterval(1800);
				
				return ResponseEntity.status(HttpStatus.OK).body(200);
				
			} else {	// DTO 가 NULL 은 아니지만 비밀번호가 일치하지 않을 때
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(404);
				return ResponseEntity.status(HttpStatus.OK).body(404);
			}
			
		}else {	// DTO 가 NULL 일 때 => 즉, 해당 아이디가 DB에서 조회되지 않을 때 ( 위에서 이미 DTO가 NULL 일 수 없음으로 DEAD CODE => SuppressWarnings("unused") )
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(400);
		}
	}
	
	
//============== 회원가입 처리 ================================================================== >
	
	// 회원가입 화면(카카오 간편 로그인으로 들어오는 경우 인증된 카카오 이메일 값을 회원가입 화면으로 가져가기)
	@GetMapping("/createAccount")
	public String createAccount(@RequestParam HashMap<String, String> params, Model model) {
		log.info("UserController ===> GET createAccount ===> start");

		String id = params.get("id");
		model.addAttribute("id", id);
		
		log.info("UserController ===> GET createAccount ===> end");

		return "user/createAccount";
	}
	
	// userService 의 isDuplicated 함수 호출하여 이메일 중복확인
	@PostMapping("/isDuplicated")
	public ResponseEntity<String> isDuplicated(@RequestParam HashMap<String, String> params) {
		log.info("UserController ===> isDuplicated ====> start");
		
		// DTO 가 NULL 이 아닌 경우 => 해당 이메일이 존재함 => 경고창
		if(userService.isDuplicated(params)!=null) {
			log.info("UserController ===> isDuplicated ====> 이메일 사용불가");
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			
		// DTO 가 NULL 인 경우 => 해당 이메일이 존재하지 않음 => 회원가입 진행
		}else {
			log.info("UserController ===> isDuplicated ====> 이메일 사용가능");
			return new ResponseEntity<String>(HttpStatus.OK);
		}
	}
	
	// 인증번호를 받기 위한 전역 변수 선언
	int AutoCreatedCode;
	
	// 이메일 인증 동의하면 인증번호를 이메일로 발송
	@PostMapping("/sendCode")
	public ResponseEntity<String> sendCode(@RequestParam HashMap<String, String> params) {
		log.info("UserController ===> sendCode ===> start");
		
		// DevUtils 의 emailSenderByCreate 함수 호출하여 파라미터로 받은 id 값(이메일주소)으로 인증번호 발송
		AutoCreatedCode = devUtils.emailSenderByCreate(params.get("id"));
		log.info("발송한 인증번호 받아보기 => " + AutoCreatedCode );
		log.info("UserController ===> sendCode ===> end");
		
		return new ResponseEntity<String>(HttpStatus.OK); 
	}

	// 사용자가 입력한 인증번호를 검증
	@PostMapping("/checkCode")
	public ResponseEntity<String> checkCode(@RequestParam HashMap<String, String> params) {
		log.info("UserController ===> checkCode ===> start");
		
		// 사용자가 입력한 인증번호
		int inputCode = Integer.parseInt(params.get("code"));
		
		// 실제 생성하여 메일로 발송한 인증번호와 비교
		if(inputCode == AutoCreatedCode) {
			log.info("UserController ===> checkCode ===> if true");
			return new ResponseEntity<String>(HttpStatus.OK);
		}else {
			log.info("UserController ===> checkCode ===> if false");
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	// 회원가입 처리
	@PostMapping("/createAccount")
	public String userCreateAccount(@RequestParam HashMap<String, String> params) {
		log.info("UserController ===> POST createAccount ===> start");
		
		// DevUtils 의 StringToPassword 함수 호출하여 파라미터로 받은 password 값(비밀번호)을 암호화된 스트링으로 변환
		String password = devUtils.StringToPassword(params.get("password"));
		
		// 해시맵의 replace 메소드 호출하여 회원가입 화면에서 전달된 파라미터 값에서 비밀번호를 암호화된 비밀번호로 치환하고 회원가입 진행
		params.replace("password", password);
		userService.register(params);
		
		log.info("UserController ===> POST createAccount ===> start");
		
		return "user/login";
	}
	
	
//========================= 카카오 간편 로그인 ======================================================= >

	// 카카오 로그인 아이디의 회원가입 여부 확인
	@GetMapping("/kakaoEmailCheck")
	public ResponseEntity<String> kakaoEmailCheck(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("UserController ===> kakaoEmailCheck ===> start");
		UserDto user = userService.login(params);
		
		if( user != null ) {	// 회원가입된 이메일이므로 바로 로그인 처리
			
			log.info("UserController ===> kakaoEmailCheck ===> if");
			session.setAttribute("userInfo", user);
			session.setMaxInactiveInterval(1800);
			return ResponseEntity.ok().body("{\"exists\": true}");

		}else { // 회원가입 되지 않은 이메일이므로 회원가입 화면으로 이동(이메일 주소 들고가기)
			
			log.info("UserController ===> kakaoEmailCheck ===> else");
			return ResponseEntity.ok().body("{\"exists\": false}");
		}
	}
	

//=================== 임시 비밀번호 발급 ============================================================= >
	
	// 이메일 계정의 가입여부 확인
	@PostMapping("/checkEmailExists")
	public ResponseEntity<String> checkEmailExists(@RequestParam HashMap<String, String> params){
		UserDto user = userService.login(params);
		
		if(user != null) {
			return ResponseEntity.ok().body("{\"exists\": true}");
		}else {
			return ResponseEntity.ok().body("{\"exists\": false}");
		}
	}
	
	// 임시 비밀번호를 이메일로 발송
	@PostMapping("/sendTempPwd")
	public ResponseEntity<String> sendTempPwd(@RequestParam HashMap<String, String> params) {
		
		// DevUtils 의 emailSenderByForget 함수 호출하여 파라미터로 받은 id 값(이메일주소)으로 임시 비밀번호 발송
		int tempPassword = devUtils.emailSenderByForget(params.get("id"));
		
		log.info("임시 비밀번호를 발송할 이메일 주소 ===> " +params.get("id"));
		log.info("발송한 임시 비밀번호 받아보기 ===> "+ tempPassword);
		
		// DevlUtils 의 StringToPassword 함수 호출하여 위에서 생성한 임시 비밀번호를 암호화하여 파라미터로 저장
		String password = devUtils.StringToPassword(Integer.toString(tempPassword));
		
		// 해시맵의 put 메소드 호출하여 암호화된 비밀번호를 파라미터에 추가
		params.put("password", password);
		
		// 암호화된 비밀번호를 DB에서 업데이트
		userService.tempPwd(params);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	
//=================== 회원정보 조회 및 수정 ============================================================= >
		
	// 회원정보 조회화면
	@GetMapping("/info")
	public String userInfo(HttpSession session, Model model) {
		log.info("GET방식으로 info 사이트로 들어옴");
		
		// userInfo 세션이 null 이면 로그인 화면으로 이동
		if (session.getAttribute("userInfo") == null ) {
			return "redirect:login";
		// userInfo 세션이 null 이 아니면 회원정보 화면으로 이동
		}else {
			UserDto user = (UserDto) session.getAttribute("userInfo");
			model.addAttribute("user", user);
			return "user/info";
		}
	}
	
	// 회원정보 수정화면
	@GetMapping("/modify")
	public String modify(HttpSession session, Model model) {
		log.info("UserController ===> GET modify ===> start");
		
		if (devUtils.getUserInfo(session) == null) {
			log.info("UserController ===> modify ===> if ===> 세션 없으므로 로그인 화면으로 이동");
			return "redirect:login";
		}else {
			log.info("UserController ===> GET modify ===> if ===> 세션 있으므로 회원정보 수정 화면으로 이동");
			UserDto user = devUtils.getUserInfo(session);
			model.addAttribute("user", user);
			return "user/modifyInfo";
		}
	}
	
	// 회원정보 수정처리 (비밀번호와 비밀번호 확인 일치하면 회원정보 수정 후 메인 게시판으로 이동, 아니면 경고창)
	@PostMapping("/modify")
	public ResponseEntity<String> modify(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("UserController ===> POST modify ===> start");
		
		// 사용자가 입력한 "현재" 비밀번호
		String strFromInput = params.get("password");
		
		// 현재 로그인된 사용자의 세션 값으로 사용자 DTO를 생성해서 id 를 파라미터에 추가
		UserDto user = new DevUtils().getUserInfo(session);
		params.put("id", user.getId());
		
		// 데이터베이스에서 조회한 비밀번호
		UserDto dto = userService.login(params);
		String strFromDatabase = dto.getPwd();
		
		// 비교하여 리턴값이 true 인 경우 사용자 입력 비밀번호가 데이터베이스에 암호화되어 저장된 비밀번호와 일치
		boolean passwordMatches = devUtils.passwordMatches(strFromInput, strFromDatabase);

		// 새 비밀번호와 새 비밀번호 확인
		String password = params.get("newPassword");
		String password2 = params.get("newPassword2");

		// 현재 비밀번호와 데이터베이스 조회 비밀번호가 일치하면
		if (passwordMatches) {
			
			// 새 비밀번호와 새 비밀번호 확인이 일치하고, 비밀번호 란이 공백이 아닌 경우
			if(password.equals(password2) && (password.isEmpty() == false )) {
				
				userService.modify(params, session);
				// 새로 설정한 닉네임과 비밀번호를 setter 로 세션 재설정
				user.setNickname(params.get("nickname"));
				
				// 입력받은 새 비밀번호를 암호화
				password = devUtils.StringToPassword(params.get("newPassword"));
				
				// params 값을 replace 로 바꿔치기
				params.replace("password", password);
				
				// 암호화된 비밀번호를 setter 로 세션값에 셋팅
				userService.modify(params, session);
				
				log.info("UserController ===> POST modify ===> if");
				
				return ResponseEntity.status(HttpStatus.OK).body("success");
			}else {
				log.info("UserController ===> POST modify ===> else");
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
		}
	}
	
	
//=================== 포인트 충전 =========================================================================== >
	
	// 입력된 포인트 금액(amount)을 쿼리로 DB에 업데이트
	@PostMapping("/pointUp")
	public ResponseEntity<String> pointUp(@RequestParam HashMap<String, String> params, HttpSession session){
		log.info("UserController ===> pointUp ===> start");
		
		// 파라미터로 받은 충전할 포인트 금액
		String point = params.get("amount");
		
		// 세션으로 사용자 DTO 생성
		UserDto user = (UserDto) session.getAttribute("userInfo");
		String id = user.getId();
		params.put("point", point);		// 실제 쿼리에 사용될 파라미터 (충전할 포인트 금액)
		params.put("id", id);			// 실제 쿼리에 사용될 파라미터 (이메일 계정)
		
		// 포인트 충전 처리
		userService.pointUp(params);
		
		// 충전 후 포인트 가져오기
		String pointTotal = userService.getPoint(params, session);
		log.info("pointTotal ===> " +pointTotal);
		user.setPoint(pointTotal);
		
		log.info("UserController ===> pointUp ===> end");
		return ResponseEntity.ok().body(pointTotal);
	}

	
//=================== 로그아웃 처리 =========================================================================== >

	// 로그아웃 처리
	@GetMapping("/logOut")
	public String logOut(HttpSession session) {
		
		log.info("UserController ===> logOut ===> session ");
		session.invalidate();
		
		return "redirect:/main/list";
	}


//========================= 회원 탈퇴처리 ====================================================================== >
	
	// 회원탈퇴 처리
	@PostMapping("/unregister")
	public String delete(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("UserController ===> delete ===> start");

		userService.delete(params, session);
		
		log.info("UserController ===> delete ===> end");
		
		return "redirect:/user/logOut";
	}
}











