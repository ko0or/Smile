package com.lgy.smile.service;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lgy.smile.service.CommentService;

import lombok.extern.slf4j.Slf4j;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dao.TradeMapperInterface;
import com.lgy.smile.dto.TradeDto;

@Slf4j
@Service
public class TradeService implements TradeMapperInterface {
	
	@Autowired private SqlSession sqlSession;
	
	/* ☆ 댓글 생성,변경,삭제 서비스 ☆ */ 	
	@Autowired private CommentService commentService;
	
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;

	@Override
	public ArrayList<TradeDto> list() {
		log.info("@# TradeService.list() start");
		
		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
		ArrayList<TradeDto> list = dao.list();
		
		log.info("@# TradeService.list() end");
		return list;
	}

	@Override
	public void write(@RequestParam HashMap<String, String> params, MultipartFile[] uploadFile, HttpSession session) {
		
		//=> ☆ 넘어온 값 보기
		log.info("@# TradeService.write() start");
		log.info( params.toString() );
		
		//=> ☆ 저장할 값 세팅하기
		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);		
		params.put( "created", devUtils.getDate() );		
		if ( params.get("contacted") != null ) {
			// 비대면 결제에 체크했다면,   contacted 값을 uncontacted(비대면) 으로 저장하고
			params.replace("contacted", "비대면");
		} else {
			// 비대면 결제에 체크하지않았다면,  contacted 값을 contacted(대면) 으로 저장한다.
			params.put("contacted", "만나요");
		}
		
		/* 
		 *     파일 업로드 
		 */
		for (MultipartFile multipartFile : uploadFile) {
			log.info("========================================================");
			/* 파일이름 출력 */log.info("multipartFile.getOriginalFilename() => " + multipartFile.getOriginalFilename()); 
			/* 파일크기 출력 */log.info("multipartFile.getSize() => " + multipartFile.getSize() );
			
			
			
			log.info("========================================================");
			
			try { 				
				
				File uploadFolder = new File( "C:/upload/temp3/" );
				if (uploadFolder.exists() == false) { uploadFolder.mkdirs(); }
				// => 경로확인용 File 객체생성, 해당 경로가 없다면 하위폴더들을 만들어주기
				
				File saveFile = new File(uploadFolder.getPath(), multipartFile.getOriginalFilename());
				// => File saveFile = new File("업로드하고싶은 경로", "저장하고싶은 파일명.확장자");
				
				
				params.put("imgPath", saveFile.getPath() ); 
				
				//params.put("user", );
				
				
				
				multipartFile.transferTo( saveFile ); 
				// => 위에있는 for-each 구문에서 받았던 객체의 transferTo() 메소드 사용하면 파일저장 가능
				// => 근데 저장할때 어디 경로에, 무슨 이름으로 저장할지 정보가 필요하니, 위에서 만든 File 객체를 매개변수로 사용 ★
				
			} catch (Exception e) { e.printStackTrace(); }
			
		} // ~ for 반복문 종료
			
		//=> ☆ 쿼리 실행
		dao.write(params, uploadFile);
		
		log.info("@# TradeService.write() end");
	}

	@Override
	public TradeDto contentView(@RequestParam HashMap<String, String> params) {
		log.info("@# TradeService.contentView() start");

		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
		TradeDto dto = dao.contentView(params);
		
		log.info("@# TradeService.contentView() end");
		return dto;
	}

	@Override
	public void modify(@RequestParam HashMap<String, String> params) {
		log.info("@# TradeService.modify() start");
		
		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
		dao.modify(params);
		
		log.info("@# TradeService.modify() end");
		
	}

	@Override
	public void delete(@RequestParam HashMap<String, String> params) {
		log.info("@# TradeService.delete() start");
		
		TradeMapperInterface dao = sqlSession.getMapper(TradeMapperInterface.class);
		dao.delete(params);
		
		log.info("@# TradeService.delete() end");
		
	}

	@Override
	public void write(HashMap<String, String> param, MultipartFile[] uploadFile) {
		// TODO Auto-generated method stub
		
	}
}
