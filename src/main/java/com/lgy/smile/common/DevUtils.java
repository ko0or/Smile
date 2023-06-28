package com.lgy.smile.common;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lgy.smile.common.EmailSender.sendType;
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.NotificationService;

@Service
public class DevUtils  {

	/* ===== 파일 저장되는 공간 ===== */
	private String savePath = "C:/upload/temp3/";
//	private String savePath = "/home/ec2-user/tomcat9/webapps/First_resources/images/";
	public String getSavePath() { return savePath;	}
	
	/* ===== 이메일 발송 API 	===== */
	@Autowired private EmailSender emailSender;
	
	/* ===== 사이트내 알람 띄우기	===== */
	@Autowired private NotificationService notificationService;
	 

 

/*
 
		  #. 리턴타입 |  메소드명

			String 	 | StringToPassword(String strFromInput)
			boolean 	 | psswordMatches(String strFromInput, String strFromDatabase)
					
			String 	 | getDate()
			String 	 | getDateCustom()

            String      | intToString(int number) 
			String 	 | getUserIdentityToString(HttpSession session)
			UserDto  | getUserInfo(HttpSession session)
			boolean 	 | isLogin(HttpSession session)
			boolean 	 | userIdentityMatchesByJSP(HashMap<String, String>params, HttpSession session)
			boolean 	 | getUserRoleIsAdmin(HttpSession session)

			int 		     | emailSenderByCreate(String to)
			int 		     | emailSenderByForget(String to)
  
  			int		     | smsSender(String to)
  
            void         | createNotification(int userIdentity, String message, String urlPath)
            void         | createNotification(String userIdentity, String message, String urlPath)
            
 */	

	
	// [★] 입력받은 문자열을 암호화
	public String StringToPassword(String strFromInput) {		
		return new BCryptPasswordEncoder().encode(strFromInput);
	}
	
	// [★] 입력받은 문자열과, 암호화되어있는 문자열이 서로 일치하는지 비교 (일치시 true ,  아니면 false)
	public boolean passwordMatches(String strFromInput, String strFromDatabase) {
		return new BCryptPasswordEncoder().matches(strFromInput, strFromDatabase);
	}
	
	
	
	// ========================================================================================================= ☆
	
	
	
	// [★] 작성일자를 반환하는 메소드
	public String getDate() {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 작성", new Locale("ko", "KR"));
		String formattedDate = sdf.format(date);
		
		return formattedDate;
	}
	
	// [★] 작성일자를 반환하는 메소드 ( 매개변수를 이용해서 원하는 포맷방식을 사용할 수 있음 )
	public String getDateCustom(String format) {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("ko", "KR"));
		String formattedDate = sdf.format(date);
		
		return formattedDate;
	}
	
	
	// ========================================================================================================= ☆	

	// [★] 숫자를 문자열로 변환해주는 메소드
	public String intToString(int number) {
		return String.valueOf(number);
	}
	
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


	
	
	
	
	// ========================================================================================================= ☆
	
	
	// [★] 인증 번호 SMS문자 발송 메소드  ( 발송된 인증번호 숫자를 리턴하며,   하이픈"-" 포함해도 상관없음 ! )
	public int smsSender(String to) {
		return new SmsSender().send(to);
	}
	
	
	
	
	
	// ========================================================================================================= ☆
	
	
/*

	#. 알람 생성 메소드 사용 예시
		
		★=> 알람 만들기(받을유저identity번호, 메시지내용)
		 devUtils.createNotification(53, "타겟없는 테스트");
		 
		 ★=> 알람 만들기(받을유저identity번호, 메시지내용, 게시판타입, 해당타입의아덴티티, 클릭시 이동할 url주소)
		 devUtils.createNotificationMainComment(53, "타겠있음", devUtils.getNotificationTypeCommentMain() , 67, "main/list?searchByNickname='민우'&searchByBoardIdentity=54");

 */
	
	
	//
	//=> 메시지 유형 enum 으로 설정 ( none: 대상 게시판 없음,     comment_main : 메인 게시판의 댓글 알람,      comment_notice : 공지 게시판의 댓글 알람 ) 
	private enum notificationType {
		none, comment_main , comment_notice 
	}
	 public Enum getNotificationTypeCommentMain() { return notificationType.comment_main; }
	 public Enum getNotificationTypeCommentNotice() { return notificationType.comment_notice; }
	 public Enum getNnotificationTypeNone() { return notificationType.none; }
	
	public void createNotification(int userIdentity, String message) 	  {	notificationService.createSet(userIdentity, message); }
	public void createNotification(String userIdentity, String message) {	notificationService.createSet(userIdentity, message); }
	public void createNotificationMainComment(int userIdentity, String message, Enum type , int target_identity, String urlPath) {	notificationService.createSet(userIdentity, message, type, target_identity, urlPath); }
	public void createNotificationNoticeComment(int userIdentity, String message, Enum type , int target_identity, String urlPath) {	notificationService.createSet(userIdentity, message, type, target_identity, urlPath); }
}
