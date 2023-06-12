package com.lgy.smile.common;

import com.lgy.smile.user.UserService;

/*   =====   ★   모든 곳에서 사용할 수 있는 공용 메소드     ★  ======   */
public class DevUtils {

	private UserService userService;
	
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
