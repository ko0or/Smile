package com.lgy.smile.common;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgy.smile.common.EmailSender.sendType;
import com.lgy.smile.dto.UserDto;

@Service
public class DevUtils  {

	/* ===== 파일 저장되는 공간 ===== */
	private String savePath = "C:/upload/temp3/";
	public String getSavePath() { return savePath;	}
	
	/* ===== 이메일 발송 API 	===== */
	@Autowired private EmailSender emailSender;
	 

/*
 
		  #. 리턴타입 |  메소드명
		
			String 		| getDate()

			String 		| getUserIdentityToString(HttpSession session)
			UserDto 	| getUserInfo(HttpSession session)
			boolean 		| isLogin(HttpSession session)
			boolean 		| userIdentityMatchesByJSP(HashMap<String, String>params, HttpSession session)
			boolean 		| getUserRoleIsAdmin(HttpSession session)

			int 				| emailSenderByCreate(String to)
			int 				| emailSenderByForget(String to)
  
  
  
 */	
	
	
	
	
	
	
	// [★] 작성일자를 반환하는 메소드
	public String getDate() {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 작성", new Locale("ko", "KR"));
		String formattedDate = sdf.format(date);
		
		return formattedDate;
	}
	
	
	// ========================================================================================================= ☆	

	
	// [★] 유저 세션에 있는 identity (PK값)을 문자열로 반환하는 메소드
	public String getUserIdentityToString(HttpSession session) {
		UserDto user = getUserInfo(session);
		if ( user != null ) {
			return String.valueOf( user.getIdentity() );
		}
		return null;
	}
	
	// [★] 세션(로그인된 정보)이 있으면 유저 정보를 반환하는 메소드 ( 없으면 null,  따라서 null 여부로 분기처리 가능 )
	public UserDto getUserInfo(HttpSession session) {
		if ( session.getAttribute("userInfo") != null ) {
			return (UserDto) session.getAttribute("userInfo");			
		}
		return null;
	}
	
	// [★] 현재 로그인 여부를 반환하는 메소드
	public boolean isLogin(HttpSession session) {
		if ( session.getAttribute("userInfo") != null ) {
			return true;
		}
		return false;
	}
	
	// [★] 세션에 있는 식별번호(현재 로그인중인 회원의 PK값) 과   JSP 파일의 <input name="user" typde="hidden" value="${세션에있던pk값}"> 이 일치하는지 확인하는 메소드
	public boolean userIdentityMatchesByJSP(HashMap<String, String>params, HttpSession session) {
		if ( getUserInfo(session).getIdentity() == Integer.parseInt(params.get("user")) == true ) {
			return true;
		}
		return false;
	}
	
	// [★] 현재 로그인된 유저가 관리자인지 확인하는 메소드
	public boolean getUserRoleIsAdmin(HttpSession session) {
		UserDto user = getUserInfo(session);
		if ( user != null && user.getRole().equals("admin") ) {
			return true;
		}
		return false;
	}
	
	
	// ========================================================================================================= ☆
	
	
	// [★] 본인확인용 이메일 발송 (계정 생성할때나, 비밀번호 분실시 본인 인증용)
	public int emailSenderByCreate(String to) {
		return emailSender.sendEmail(to, sendType.create);
	}
	
	// [★] 임시 비밀번호 발송 (비밀번호 분실시 본인 인증후 이용)
	public int emailSenderByForget(String to) {
		return emailSender.sendEmail(to, sendType.forget);
	}

	
	
	
	
}
