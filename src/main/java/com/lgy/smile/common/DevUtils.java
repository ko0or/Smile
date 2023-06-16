package com.lgy.smile.common;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.UserService;

/*   =====   ★   모든 곳에서 사용할 수 있는 공용 메소드     ★  ======   */
@Service
public class DevUtils {

	private UserService userService;
	
	// 작성일자를 반환하는 메소드
	public String getDate() {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 작성", new Locale("ko", "KR"));
		String formattedDate = sdf.format(date);
		
		return formattedDate;
	}
	
	
	// 세션이 있으면 유저 정보를 반환하는 메소드 ( 없으면 null,  따라서 null 여부로 분기처리 가능 )
	public UserDto getUserInfo(HttpSession httpSession) {
		if ( httpSession.getAttribute("userInfo") != null ) {
			return (UserDto) httpSession.getAttribute("userInfo");			
		}
		return null;
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
