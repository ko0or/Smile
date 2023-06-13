package com.lgy.smile.service;

public class UserService {

	
	// ☆ 유저 정보조회 기능 필요 ( pk로 검색 )
	// public 유저DTO findByPK(int userPK) { ... return 유저dto  }
	
	
	
	
	// ☆ 유저 정보조회 기능 필요 ( 닉네임으로 검색 )
	// public 유저DTO findByNickname(String nickName) { ... return 유저dto  }	
	
	
	
	
	// ☆ "중복여부체크" 눌렀을때,  해당 이메일ID 와 닉네임이 사용중인지 확인후 리턴하는 메소드 필요
	// public boolean isDuplicated(Model model) {  ... 조건문 if-else로 return 분기처리 true/false }
	
	
	
	// ☆ user(유저) 회원가입을 처리하는 메소드 필요	
	// 위에서 만든 isDuplicated 메소드가 false 일때 진행 ex->  if ( isDuplicated(model) == true ) { ... }
	// dao 객체 호출해서 insert 쿼리진행
		

	
/*
	  	☆ 로그인 버튼 눌렀을때 AJAX 방식으로 처리하는 컨트롤러 필요
  		＃ 예시
  		
  		ResponseEntity<Void> 로그인체크 () {
		
			DAO객체 dao참조변수 = new DAO객체();
		
			if ( dao참조변수.findByAccount(받은아디, 받은비번) == true ) {
				return new ResponseEntity<>(HttpStatus.OK); // HTTP Status 200 (성공)
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // HTTP Status 400 (잘못된 요청)
			}
	  	}
	  	
 */
	
}
