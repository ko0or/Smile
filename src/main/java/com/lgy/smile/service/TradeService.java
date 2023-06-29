package com.lgy.smile.service;
import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.TradeMapperInterface;
import com.lgy.smile.dto.TradeDto;
import com.lgy.smile.dto.UserDto;

@Slf4j
@Service
public class TradeService implements TradeMapperInterface {
	
	@Autowired private SqlSession sqlSession;
	
	/* ☆ 댓글 생성,변경,삭제 서비스 ☆ */ 	
	@Autowired private CommentService commentService;
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;

	//* ☆ TradeMapperInterface의 list 값 가져오기 (TradeDto)
	@Override
	public ArrayList<TradeDto> list(@RequestParam HashMap<String, String> param) {
		log.info("@# TradeService.list() start");
		
		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
		ArrayList<TradeDto> list = dao.list(param);
		
		log.info("@# TradeService.list() end");
		return list;
	}

	//* ☆ trade(중고 거래) 글 쓰기
	@Override
	public boolean write(@RequestParam HashMap<String, String> params, MultipartFile[] uploadFile, HttpSession session) {
		
		//=> ☆ 넘어온 값 보기
		log.info("@# TradeService.write() start");
			
		//=> ☆ 저장할 값 세팅하기
		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
//		getUserInfo : 세션(로그인된 정보)이 있으면 유저 정보를 반환하는 메소드 ( 없으면 null,  따라서 null 여부로 분기처리 가능 )
		UserDto user = devUtils.getUserInfo(session);
		//사진파일의 이름이 같으면 다른 글의 사진에도 영향이 있어서 UUID 추가해서 파일이름을 다르게 함
		String fileIdentity = UUID.randomUUID().toString() + "_" ;
		//로그인 하지 않으면 return false; 로 컨트롤러 @GetMapping("/write")(get방식/화면)가서 "redirect:/user/login"실행됨
		if ( user == null ) {
			log.info("☆ 글 작성 실패 => 비로그인상태");
			return false;
		}

//		params.put("userPK", String.valueOf( user.getIdentity() ) );
		// getDate 작성일자를 반환하는 메소드
		params.put( "created", devUtils.getDate() );	
		
		// 로그인상태라면 ..  세션으로부터 받은 정보를 추가해서 DB에 저장 -☆
		if ( params.get("contacted") != null ) {
			// 비대면 결제에 체크했다면,   contacted 값을 uncontacted(비대면) 으로 저장하고
			params.replace("contacted", "비대면");
		} else {
			// 비대면 결제에 체크하지않았다면,  contacted 값을 contacted(대면) 으로 저장한다.
			params.put("contacted", "만나요");
		}
		

		//=> ☆ 파일 업로드
		for (MultipartFile multipartFile : uploadFile) {
			log.info("========================================================");
			/* 파일이름 출력 */log.info("multipartFile.getOriginalFilename() => " + multipartFile.getOriginalFilename()); 
			/* 파일크기 출력 */log.info("multipartFile.getSize() => " + multipartFile.getSize() );					
			log.info("========================================================");
			
			try { 				
				//getSavePath() : "C:/upload/temp3/" 사진 업로드 경로
				File uploadFolder = new File( devUtils.getSavePath() );
				// => 경로확인용 File 객체생성, 해당 경로가 없다면 하위폴더들을 만들어주기
				if (uploadFolder.exists() == false) { uploadFolder.mkdirs(); }
				
				// => File saveFile = new File("업로드하고싶은 경로", "UUID_"+"저장하고싶은 파일명.확장자");				
				File saveFile = new File( devUtils.getSavePath() , fileIdentity + multipartFile.getOriginalFilename());
				
//				params로 받아온 값 : saveFile.getPath() 사용하니 객체확인이 안되었음
				// params.put("imgPath", saveFile.getPath() );
//				params로 받아온 값 : "imgPath", "업로드하고싶은 경로"+"UUID_"+"저장하고싶은 파일명.확장자"
				params.put("imgPath", devUtils.getSavePath() + fileIdentity + multipartFile.getOriginalFilename() );
//				params로 받아온 값 : "user", 로그인 한 유저 정보 확인
				params.put("user", String.valueOf( devUtils.getUserInfo(session).getIdentity() ));
				log.info( params.toString() );				
				
				multipartFile.transferTo( saveFile ); 
				// => 위에있는 for-each 구문에서 받았던 객체의 transferTo() 메소드 사용하면 파일저장 가능
				// => 근데 저장할때 어디 경로에, 무슨 이름으로 저장할지 정보가 필요하니, 위에서 만든 File 객체를 매개변수로 사용 ★
				
			} catch (Exception e) { e.printStackTrace(); }
			
		} // ~ for 반복문 종료
			
		//=> ☆ 쿼리 실행
		dao.write(params);
		log.info("@# TradeService.write() end");
		
		return true;
		
	}

	//=> ☆ trade(중고 거래) 글 내용 보기
	@Override
	public TradeDto contentView(@RequestParam HashMap<String, String> params) {
		log.info("@# TradeService.contentView() start");

		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
		TradeDto dto = dao.contentView(params);
		
		log.info("@# TradeService.contentView() end");
		return dto;
	}

	//=> ☆ trade(중고 거래) 글 수정
	@Override
	public boolean modify(@RequestParam HashMap<String, String> params, MultipartFile[] uploadFile, HttpSession session) {
		log.info("@# TradeService.modify() start");
		
		//=> ☆ 저장할 값 세팅하기
		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
//		getUserInfo : 세션(로그인된 정보)이 있으면 유저 정보를 반환하는 메소드 ( 없으면 null,  따라서 null 여부로 분기처리 가능 )
		UserDto user = devUtils.getUserInfo(session);
		//사진파일의 이름이 같으면 다른 글의 사진에도 영향이 있어서 UUID 추가해서 파일이름을 다르게 함
		String fileIdentity = UUID.randomUUID().toString() + "_" ;
		
		// 로그인상태라면 ..  세션으로부터 받은 정보를 추가해서 DB에 저장 -☆		
		if ( params.get("contacted") != null ) {
			// 비대면 결제에 체크했다면,   contacted 값을 uncontacted(비대면) 으로 저장하고
			params.replace("contacted", "비대면");
		} else {
			// 비대면 결제에 체크하지않았다면,  contacted 값을 contacted(대면) 으로 저장한다.
			params.put("contacted", "만나요");
		}
		
		//=> ☆ 파일 업로드
		for (MultipartFile multipartFile : uploadFile) {
			log.info("========================================================");
			/* 파일이름 출력 */log.info("multipartFile.getOriginalFilename() => " + multipartFile.getOriginalFilename()); 
			/* 파일크기 출력 */log.info("multipartFile.getSize() => " + multipartFile.getSize() );					
			log.info("========================================================");
			
			try { 				
				//getSavePath() : "C:/upload/temp3/" 사진 업로드 경로
				File uploadFolder = new File( devUtils.getSavePath() );
				// => 경로확인용 File 객체생성, 해당 경로가 없다면 하위폴더들을 만들어주기
				if (uploadFolder.exists() == false) { uploadFolder.mkdirs(); }
				// => File saveFile = new File("업로드하고싶은 경로", "UUID_"+"저장하고싶은 파일명.확장자");
				File saveFile = new File( devUtils.getSavePath() , fileIdentity + multipartFile.getOriginalFilename());
				
//				params로 받아온 값 : saveFile.getPath() 사용하니 객체확인이 안되었음				
				// params.put("imgPath", saveFile.getPath() );
//				params로 받아온 값 : "imgPath", "업로드하고싶은 경로"+"UUID_"+"저장하고싶은 파일명.확장자"				
				params.put("imgPath", devUtils.getSavePath() + fileIdentity + multipartFile.getOriginalFilename() );
//				params로 받아온 값 : "user", 로그인 한 유저 정보 확인
				params.put("user", String.valueOf( devUtils.getUserInfo(session).getIdentity() ));
				log.info( params.toString() );				
				
//				params로 받아온 값 : "price", ex>1,000원을 1000으로 
				params.replace("price", params.get("price").replace(",", "") );
				
//				업로드 된 파일 용량이 0 보다 크면 사진이과 내용 수정
				if ( multipartFile.getSize() > 0 ) {
					dao.modifyWithImgPath(params);
					log.info("@#@#사진이랑 같이 저장됨@#@#");
//				아니면 사진은 그대로 내용만 수정	
				} else {
					dao.modify(params);				
					log.info("변경할 파일 없음");
				}
				
				
				multipartFile.transferTo( saveFile );
				// => 위에있는 for-each 구문에서 받았던 객체의 transferTo() 메소드 사용하면 파일저장 가능
				// => 근데 저장할때 어디 경로에, 무슨 이름으로 저장할지 정보가 필요하니, 위에서 만든 File 객체를 매개변수로 사용 ★
				log.info("파일 존재");

				
			} catch (NoSuchFileException e) { log.info("파일 없음");
			} catch (Exception e) {  e.printStackTrace(); 
			}
			
		} // ~ for 반복문 종료
		
		log.info("@# TradeService.modify() end");
		return true;
		
	}

	// ★ trade(중고 거래) 글 삭제
	@Override
	public boolean delete(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# TradeService.delete() start");
		
		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
		UserDto user = devUtils.getUserInfo(session);
		
		// 1. 게시글 번호로 SELECT 조회
		TradeDto dto = dao.contentView(params);
		// 2. 조회된 결과의 user 값을 확인
		int userPK = dto.getUser();
		
		// 3. 현재 로그인중인 회원의 식별번호 (유저테이블의 pk번호) 확인
		user.getIdentity();
		
		// 4. 현재 로그인중인 회원 식별번호가 게시글 조회시 나온 user 외래키와 서로 일치하는지 확인
		if ( user.getIdentity() == userPK) { 		
			// 현재 로그인중인 회원번호가,  삭제하고자하는 게시글 테이블의 pk 번호와 일치하다면 삭제
			dao.delete(params);
			log.info("@# TradeService.delete() end");
			return true;
		}
		
		return false;
		
	}	
	
	
	
	
	@Override
	public int telCheck(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		//=> 로그인 상태면,  전달받은 정보로 회원 인증된 전화번호인지 확인후 리턴 ( 0: 미인증,   1: 인증 )
		if ( devUtils.isLogin(session) == true ) {			
			params.put("user", String.valueOf( devUtils.getUserInfo(session).getIdentity() ));
			log.info("user => " + String.valueOf( devUtils.getUserInfo(session).getIdentity()) );
			
			TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
			return dao.telCheck(params);
		}		
		
		return 0;
	}
	
	
	
	/* ★ trade(중고 거래) 인증된 휴대폰 번호 (or 회원에게 발송된 인증번호) 등록 @Override */ 
	@Override
	public void telUpdate(@RequestParam HashMap<String, String> params, HttpSession session) {
		if ( devUtils.isLogin(session) == true ) {			
			params.put("user", String.valueOf( devUtils.getUserInfo(session).getIdentity() ));			
			TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
			dao.telUpdate(params);
		}		
	}

	/* ★ trade(중고 거래) 좋아요 @Override */
	@Override
	public void like_toggle(@RequestParam HashMap<String, String> params, HttpSession session) {
		if ( devUtils.isLogin(session) == true ) {	
			params.put("user", String.valueOf( devUtils.getUserInfo(session).getIdentity() ));
			TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
			dao.like_toggle(params);
		}
	}
	
	
	
	
	
	@Override
	public int telCheck(@RequestParam HashMap<String, String> params) { return -1; }
	
	@Override
	public boolean write(HashMap<String, String> param) {
		// TODO Auto-generated method stub	
		return false;
	}

	@Override
	public boolean delete(HashMap<String, String> param) {
		// TODO Auto-generated method stub	
		return false;
	}

	@Override
	public boolean modify(HashMap<String, String> param) {
		// TODO Auto-generated method stub	
		return false;
	}

	@Override
	public void modifyWithImgPath(HashMap<String, String> param) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void modifyWithImgPath(HashMap<String, String> params, MultipartFile[] imgPath, HttpSession session) {
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<TradeDto> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override public void telUpdate(@RequestParam HashMap<String, String> params) {}
	
	@Override public void like_toggle(@RequestParam HashMap<String, String> params) {}
}