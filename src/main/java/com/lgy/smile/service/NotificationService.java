package com.lgy.smile.service;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.NotificationMapperInterface;
import com.lgy.smile.dto.NotificationDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService implements NotificationMapperInterface {
	
	/* ☆ 마이바티스 ☆ */
	@Autowired private SqlSession sqlSession;
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;
	
	
	
	
	
	/* ★ 알람 갯수 가져오기 @Override */
	public int count(HashMap<String, String> params, HttpSession session) {
		
		NotificationMapperInterface dao = sqlSession.getMapper(NotificationMapperInterface.class);
		params.put("user", devUtils.getUserIdentityToString(session)); 
		
		return dao.count(params);
	}	
	
	
	
	/* ★ 알람 목록 가져오기 @Override */
	@Override
	public ArrayList<NotificationDto> list(HashMap<String, String> params, HttpSession session) {		
		
		NotificationMapperInterface dao = sqlSession.getMapper(NotificationMapperInterface.class);
		params.put("user", devUtils.getUserIdentityToString(session)); 
		
		return dao.list(params);
	}
	
	
	
	/* ★ 알람 만들기 @Override */
	@Override
	public void create(HashMap<String, String> params) {
		NotificationMapperInterface dao = sqlSession.getMapper(NotificationMapperInterface.class);
		
		dao.create(params);
	}

	
	
	/* ★ 알람 삭제하기 @Override */
	@Override
	public void delete(HashMap<String, String> params) {
		NotificationMapperInterface dao = sqlSession.getMapper(NotificationMapperInterface.class);
		dao.delete(params);
	}



	
	/* ☆ 매퍼 호출용 */
	@Override public ArrayList<NotificationDto> list(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return null; }
	@Override public int count(HashMap<String, String> params) { /* TODO Auto-generated method stub */ return -1; }

	
	
	
	
	
	
/* ============================================================================================================================================== >>
	
	#.  알람 만들기(메시지내용, 받을유저identity번호)
	
	
<< ============================================================================================================================================== */	

	//=> 메시지 유형 enum 으로 설정 ( none: 대상 게시판 없음,     comment_main : 메인 게시판의 댓글 알람,      comment_notice : 공지 게시판의 댓글 알람 ) 
	enum notificationType {
		none, comment_main , comment_notice 
	}
	
	//=> (타겟 : x) 단순히 해당 유저에게 메시지만 보내고 싶을때 ( 오버로딩 적용, 유저 아이덴티티를 int 혹은 String 으로 받을 수 있게함 )
	public void createSet(String userIdentity, String message) {
		 int intUserIdentity =  Integer.parseInt(userIdentity);
		HashMap<String, String> params = createHashMapSet(intUserIdentity , message, notificationType.none, -1, "", -1);		
		create(params);		
	}
	public void createSet(int userIdentity, String message) {		 
		HashMap<String, String> params = createHashMapSet(userIdentity , message, notificationType.none, -1, "", -1);		
		create(params);
	}
	
		
	//=> (타겟 : 메인게시판의 댓글) 게시판에 누군가 댓글 달았을때 알람 띄우기 ( 오버로딩 적용, 유저 아이덴티티를 int 혹은 String 으로 받을 수 있게함 )
	public void createSet(int userIdentity, String message, Enum type, int target_identity, String urlPath, int targetUserIdentity) {
		HashMap<String, String> params = createHashMapSet(userIdentity , message, type, target_identity, urlPath, targetUserIdentity);
		create(params);
	}
	public void createSet(String userIdentity, String message, Enum type, int target_identity, String urlPath, int targetUserIdentity) {
		int intUserIdentity =  Integer.parseInt(userIdentity);
		HashMap<String, String> params = createHashMapSet(intUserIdentity , message, type, target_identity, urlPath, targetUserIdentity);
		create(params);
	}
	
	
	

	
	//=> ☆ 위에서 createSet 을 사용하기위한 메소드
	private HashMap<String, String> createHashMapSet(int userIdentity, String message, Enum type , int target_identity, String urlPath, int targetUserIdentity ) {
		
		//=> 공통
		HashMap<String, String> params = new HashMap<>();
		params.put("msg", message);
		params.put("user", devUtils.intToString(userIdentity) );
		params.put("created", devUtils.getDateCustom("yyyy년 MM월 dd일(E요일) a hh:mm"));
		params.put("sender", devUtils.intToString(targetUserIdentity));
		
		
		//=> 매개변수로 받은 URL 주소가 없을경우 or 있을경우
		if (urlPath.isEmpty() == true )  { params.put("url_path", "" ); } else { params.put("url_path", urlPath ); } 
		
		  
		//=> 알람 종류에 따라 매개변수로 받은 identity (외래키) 를 어느 값에 넣어줄지 결정하기
	    switch ((com.lgy.smile.common.DevUtils.notificationType) type) {
        case none:
            params.put("comment_main_identity", null);
            params.put("comment_notice_identity", null);
            break;
        case comment_main:
            params.put("comment_main_identity", devUtils.intToString(target_identity));
            params.put("comment_notice_identity", null);
            break;
        case comment_notice:
            params.put("comment_main_identity", null);
            params.put("comment_notice_identity", devUtils.intToString(target_identity));
            break;
	    }
	    
		return params;
	}
}
