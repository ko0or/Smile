package com.lgy.smile.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dto.TradeDto;
import com.lgy.smile.service.TradeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/trade")
public class TradeController {

	@Autowired private TradeService tradeService;
	/* ☆ 공용으로 사용가능한 메소드들을 모아놓은 devUtils  ☆ */
	@Autowired private DevUtils devUtils;

	// ★ trade(중고 거래) 글 목록
	@GetMapping("/list")
	public String tradeList(@RequestParam HashMap<String, String> params, Model model, HttpSession session) {
		log.info("@# list");

		//☆=> 닉네임에 대한 검색 결과 출력하기
		if ( params.get("searchByNickname") == null ) { params.put("searchByNickname", "\"\""); }
		model.addAttribute("searchByNickname", params.get("searchByNickname"));  
		
		//☆=> 해당 닉네임으로 작성된 게시글 번호에 대한 검색 결과 출력하기 
		if ( params.get("searchByBoardIdentity") == null ) { params.put("searchByBoardIdentity", "\"\""); }
		model.addAttribute("searchByBoardIdentity", params.get("searchByBoardIdentity"));  
		
		//☆=> 좋아요된 게시글 검색 결과 출력하기 
		if ( params.get("searchByLikeUser") == null ) { params.put("searchByLikeUser", "\"\""); }
		model.addAttribute("searchByLikeUser", params.get("searchByLikeUser"));  
		
		log.info("@@# params => " + params.toString() );
		//ArrayList<TradeDto> list = tradeService.list();
		//model.addAttribute("list", list);
		//
		model.addAttribute("user", devUtils.getUserIdentityToString(session) );
		
		//수정 삭제버튼 글 안으로 옮김
		//model.addAttribute("show", "block" ); //톱니바퀴 표시
		//model.addAttribute("show", "none" );  //톱니바퀴 숨김
		
		return "trade/list";
	}
	
	// ★ trade(중고 거래) TradeDto에서 받아온 값들
	@GetMapping("/getPosts")
	//ResponseEntity : ajax를 사용하기 위해 필요함
	public ResponseEntity< ArrayList<TradeDto> > getPosts(@RequestParam HashMap<String, String> params) {
		log.info("ajax로부터 전달받은 값 => " + params.toString() );
		return ResponseEntity.status(HttpStatus.OK).body( tradeService.list(params) );
	}	
		
	// ★ trade(중고 거래) 글 쓰기(get방식/화면)
	@GetMapping("/write")
	public String tradeWrite(HttpSession session) {
		
		// 비로그인 상태라면, 로그인 화면으로 돌려보내고
		if ( devUtils.getUserInfo(session) == null ) return "redirect:/user/login"; 		
				
		else // 로그인상태라면, 글 쓰기 화면(views/trade/write.jsp 파일)을 보여준다 -!
			
		return "trade/write";
	}
		
	// ★ trade(중고 거래) 글 쓰기(post방식/처리)
	@PostMapping("/write")
	public String tradeWrite(@RequestParam HashMap<String, String> params, MultipartFile[] imgPath, HttpSession session) {
		log.info("@# write");

		tradeService.write(params, imgPath, session);
		return "redirect:list";
	}		

	// ★ trade(중고 거래) 글 읽기
	@RequestMapping("/write_view")
	public String tradeWrite_view() {
		log.info("@# write_view");

		return "trade/write_view";
	}

	// ★ trade(중고 거래) 글 수정 (★ 화면) 
	@GetMapping("/modify")
	public String tradeModify(@RequestParam HashMap<String, String> params, Model model, HttpSession session) {
		
		log.info("@# modify");
		
		// 비로그인 상태라면, list 주소로 돌려보내고
		// 로그인상태라면, 글 쓰기 화면(views/trade/write.jsp 파일)을 보여준다 -!
		if ( devUtils.getUserInfo(session) == null ) return "redirect:list"; 		
				
		model.addAttribute("board", tradeService.contentView(params));
		model.addAttribute("userIdentity", devUtils.getUserIdentityToString(session) );
		
		return "trade/edit";
	}
	
	// ★ trade(중고 거래) 글 수정 (★ 처리)
	@PostMapping("/modify")
	public String tradeModify(@RequestParam HashMap<String, String> params, MultipartFile[] imgPath, HttpSession session) {
		
		log.info("@# modify");		
		log.info("@##@@ => " + params.toString() );
		
		if ( devUtils.userIdentityMatchesByJSP(params, session) == true ) {
			tradeService.modify(params, imgPath, session);							
		}
		return "redirect:list";		
	}

	// ★ trade(중고 거래) 글 삭제
	@GetMapping("/delete")
	public String tradeDelete(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# delete");

		tradeService.delete(params, session);

		return "redirect:list";
	}
	
	// ======= ★<img src="요기들어갈꺼 리턴해주는 메소드"> ============================= >
		@ResponseBody
		@GetMapping("/display")
		public ResponseEntity<byte[]> getFile(String fileName) {
	    
	 
	    	// 리턴용 객체와 파일 조회용 객체생성
			ResponseEntity<byte[]> result = null;		
			File file = new File(fileName);

			
			try {
				
				// 화면에 무슨 타입으로 보여줄지 + 해당 타입으로 뭘 보여줄지 작성
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Type", Files.probeContentType(file.toPath()));
				result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
				

	        // 오류발생했을땐 해당 메시지 + null 값 리턴
			} catch (NoSuchFileException e) { 
			} catch (Exception e) { e.printStackTrace();}		
			return result;
		}
						
		
		// ★ trade(중고 거래) 내용 보기
		@GetMapping("/content_view")
		public ResponseEntity<TradeDto> content_view(@RequestParam HashMap<String, String> params) {
			//=> 중고거래 게시글 눌렀을때 모달에 보여질 정보를 보여주기 위한 역할 ★ 
			return ResponseEntity.status(HttpStatus.OK).body( tradeService.contentView(params) ); 
		}
					
		
		// ★ trade(중고 거래) 문자 인증
		@GetMapping("/telCheck")
		public ResponseEntity<Boolean> telCheck(@RequestParam HashMap<String, String> params, HttpSession session) {

			params.replace("tel", params.get("tel").replaceAll("-", "") );
			int result = tradeService.telCheck(params, session);
			
			if ( result == 1 ) {
				//=> 입력된 전화번호가 DB에 등록된 번호라면 ? (인증 성공)
				return ResponseEntity.status(HttpStatus.OK).body( true );
			} else {
				//=> 입력된 전화번호가 DB에 등록되지않았다면 ? (인증 실패)
				return ResponseEntity.status(HttpStatus.OK).body( false );
			}			
		}
		
		// ★ trade(중고 거래) 문자 인증 (위에서 인증 실패라면)
		@PostMapping("/telUpdate") 
		public ResponseEntity<Boolean> telUpdate(@RequestParam HashMap<String, String> params, HttpSession session) {
			
			/* ★ trade(중고 거래) 인증된 휴대폰 번호 (or 회원에게 발송된 인증번호) 등록 */
			params.replace("tel", params.get("tel").replaceAll("-", "") );
			
			//=> 휴대폰 인증 요청이라면
			if ( params.get("telAuthentication") != null ) {
				
				// (1) devUtils 이용해서 인증번호를 보내고 + 보낸 인증번호를 변수로 받음
				int certificationNumber = devUtils.smsSender(params.get("tel"));
				
				// (2) params 값이 <String, String> = 문자열이기때문에, 위의 숫자를 넣을 수 없음
				// 그래서, String.valueOf() 메소드를 이용해서 숫자 -> 문자열로 변경
				String stringTel = String.valueOf(certificationNumber);
				
				// (3) 위에 2번 덕분에 문자열 바뀐걸 tel 에 집어넣고
				params.replace("tel", stringTel);			
				
			} 
			// (4) 메소드 실행하면,  마이바티스에 #{tel} 이  인증번호로 업데이트 되버림 
			tradeService.telUpdate(params, session);
			
			return ResponseEntity.status(HttpStatus.OK).body( true );
		}
		
		// ★ trade(중고 거래) 좋아요 등록
		@PostMapping("/like_toggle")
		public ResponseEntity<Void> likeToggle(@RequestParam HashMap<String, String> params, HttpSession session) {
		
			tradeService.like_toggle(params, session);
			//리턴할 값이 없을때 </Void> , .build()
			//리턴할 값이 있을땐 <Boolean> , .body( true ) 또는 ( false )			
			return ResponseEntity.status(HttpStatus.OK).build();	
		}
}
