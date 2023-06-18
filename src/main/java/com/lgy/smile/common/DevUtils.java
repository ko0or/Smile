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

import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.UserService;

@Service
public class DevUtils {

	@Autowired private UserService userService;

/* ================================================================================================================= >  
	  
	#. 각 기능마다 필요한 코드들을 모아놓은 DevUtils.java 파일 ★
	  
	 @ 메소드 소개
	  	
	  	
	  	[1] 메소드명: getDate()    => 리턴타입 : String  , 문자열
	  		.. 언제 사용 => DB에 작성 일시 넣기위해 현재 시간정보가 필요할때
	  		.. 무슨 기능 => 한국 기준으로 시간을 구하고, "yyyy년 MM월 dd일 작성" 형식으로 문자열을 만들어줌 ! (ex-> 2023년 06월 12일 작성)
	  		
	  	[2] 메소드명: isLogin()    => 리턴타입 : true 혹은 false , 조건문에 해당 메소드 사용 가능
	  		.. 언제 사용 => 로그인 여부 체크해야할때
	  		.. 무슨 기능 => 로그인시 생성되는 세션이 있는지 체크하고, 결과를 true 혹은 false 로 리턴
	  
	  [3] public UserDto getUserInfo(HttpSession session)    => 리턴타입 : UserDto , 회원정보
	  		.. 언제 사용 => 현재 로그인된 사용자의 정보가 필요할때 
	  		.. 무슨 기능 => 로그인중인 회원의 정보를 꺼내올 수 있도록 UserDto를 반환하는 역할
	  
	  [4] public boolean userIdentityMatchesByJSP(HashMap<String, String>params, HttpSession session)    => 리턴타입 : true 혹은 false , 조건문에 해당 메소드 사용가능 
	  		.. 언제 사용 => 게시글 혹은 댓글을 수정, 삭제할 때
	  		.. 무슨 기능 => JSP 파일에서 <form> 정보를 전달받을때,  해당 글을 작성한 사람이  지금 로그인한 사람이 맞는지 서로 비교하는 역할. 
	  		
	  		.. ★ 중요 : input 태그의 name 속성 값이 user 로 되어있어야함 ! 
	  
	  
< ================================================================================================================= */
	
	
	
	
	// [1] 작성일자를 반환하는 메소드
	public String getDate() {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 작성", new Locale("ko", "KR"));
		String formattedDate = sdf.format(date);
		
		return formattedDate;
	}
	
	
	// [2] 현재 로그인 여부를 반환하는 메소드
	public boolean isLogin(HttpSession session) {
		if ( session.getAttribute("userInfo") != null ) {
			return true;
		}
		return false;
	}
	
	
	// [3] 세션(로그인된 정보)이 있으면 유저 정보를 반환하는 메소드 ( 없으면 null,  따라서 null 여부로 분기처리 가능 )
	public UserDto getUserInfo(HttpSession session) {
		if ( session.getAttribute("userInfo") != null ) {
			return (UserDto) session.getAttribute("userInfo");			
		}
		return null;
	}
	

	// [4] 세션에 있는 식별번호(현재 로그인중인 회원의 PK값) 과   JSP 파일의 <input name="user" typde="hidden" value="${세션에있던pk값}"> 이 일치하는지 확인하는 메소드
	public boolean userIdentityMatchesByJSP(HashMap<String, String>params, HttpSession session) {
		if ( getUserInfo(session).getIdentity() == Integer.parseInt(params.get("user")) == true ) {
			return true;
		}
		return false;
	}
	
	
	
	
	// ☆ 작성자 or 관리자 인지 확인후 boolean 타입으로 리턴 
	// => 유저 서비스 계층에서 만들어둔 메소드 가져오기
	
	
	// ☆ 로그인 여부(세션) 조회 + boolean 타입으로 리턴
	// => 게시글및 댓글 등 CRUD 시도시 해당 메소드를 조건문에다가 사용할 수 있는 메소드를 만들어두면 편함 !
	
	
	// ☆ 로그인된 계정과 글 작성자가 동일한지 조회 + boolean 타입으로 리턴
	// => 작성자 본인에게만 게시글 수정, 삭제 버튼이 보이도록 분기처리해주기 위해서 필요한 메소드 !
	
	
	
	
/*

	...
	☆ 이메일 인증및 문자 발송 메소드도  여기 적어두고 사용하기
	..
	
*/
	
	
}
