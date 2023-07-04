package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.common.DevUtils;
import com.lgy.smile.dto.ChattingDto;
import com.lgy.smile.dto.ChattingRoomDto;
import com.lgy.smile.dto.NotificationDto;
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.ChattingRoomService;
import com.lgy.smile.service.ChattingService;
import com.lgy.smile.service.NotificationService;
import com.lgy.smile.service.TradeService;
import com.lgy.smile.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/chat")
public class ChattingController {
	
	@Autowired private ChattingService chattingService;	
	@Autowired private ChattingRoomService chattingRoomService;	
	@Autowired private UserService userService;
	@Autowired private TradeService tradeService;
	@Autowired private NotificationService notificationService;
	@Autowired private DevUtils devUtils;
	
//	채팅 전체 목록 불러오는 메소드
	@GetMapping("/getChattings")
	public ResponseEntity<List<ChattingDto>> getChattings() {
		ArrayList<ChattingDto> list = chattingService.list();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
//	board 아이디(게시글 번호)와 buyer 아이디(구매자 번호)로 채팅방 번호를 찾아서 해당 채팅창 번호의 채팅 모두 불러오는 메소드
	@PostMapping("/getChatting")
	public ResponseEntity<List<ChattingDto>> getChatting(@RequestParam HashMap<String, String> params) {
		ChattingRoomDto roomDto = chattingRoomService.contentView(params);
		ArrayList<ChattingDto> list = chattingService.contentView(roomDto.getIdentity());
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
//	채팅 작성 메소드
	@PostMapping("/write")
	public ResponseEntity<Void> chattingWrite(@RequestParam HashMap<String, String> params) {
		chattingService.write(params);
		
		// chatDuplicatedCheck 결과(카운트 0이냐 아니냐)에 따라   INSERT 혹은 UPDATE 진행 ㄱㄱ
		NotificationDto notificationDto = notificationService.chatDuplicatedCheck(params);
		int count = notificationDto.getCount();
		log.info("@@# notificationDto  => " + notificationDto.toString() );
		
		String boardIdentity = params.get("board");
		String sellerIdentity = params.get("seller");
		String sendIdentity = params.get("senderIdentity");
		String receiverIdentity = params.get("receiverIdentity");
		
		String buyerIdentity = (  sellerIdentity.equals(sendIdentity) == true ) ? receiverIdentity : sendIdentity;
		params.put("buyer", buyerIdentity);
		
		
		if ( count == 0 ) {
			params.put("url_path", "chat/chatContent?board="+boardIdentity+"&buyer="+buyerIdentity);
			notificationService.chatNotificationCreate(params);
		} else {
			String notificationIdentity = String.valueOf(notificationDto.getNotificationIdentity());
			params.put("notificationIdentity", notificationIdentity);
			notificationService.chatNotificationUpdate(params);
		}
		
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
//	채팅작성 테스트페이지
	@RequestMapping("/chatCreateTest")
	public String chatCreateTest() {
		
		return "/trade/test/chatCreateTest";
	}

//	채팅리스트 테스트페이지
	@GetMapping("/chatListTest")
	public String list(Model model) {
		ArrayList<ChattingDto> list = chattingService.list();
		model.addAttribute("list", list);
		return "/trade/test/chatListTest";
	}
	
//	채팅방 화면 테스트페이지
	@GetMapping("/chatContentTest")
	public String chatContentTest(@RequestParam HashMap<String, String> params, Model model) {
		ChattingRoomDto roomDto = chattingRoomService.contentView(params);
		ArrayList<ChattingDto> list = chattingService.contentView(roomDto.getIdentity());
		model.addAttribute("list", list);
		return "/trade/test/chatContentTest";
	}

	@GetMapping("/chatContent")
	public String chatContent(@RequestParam HashMap<String, String> params, HttpSession session, Model model) {
		
		//=> 페이지 표시에 필요한 정보들 조회
		ChattingRoomDto roomDto = chattingRoomService.contentView(params);
		int roomNum = roomDto.getIdentity();
		
		ArrayList<ChattingDto> list = chattingService.contentView(roomNum);
		UserDto user = devUtils.getUserInfo(session);
		
		//=> 비 로그인 상태에서 접근 요청시 로그인 화면으로 보내기
		if ( devUtils.isLogin(session) == false ) {
			return "redirect:/user/login";
		}
		
		//=> 로그인은했지만, 구매자 or 판매자에 해당되지않는다면  메인 화면으로 보내기
		if ( user.getIdentity() != roomDto.getBuyer() && user.getIdentity() != roomDto.getSeller() ) {
			return "redirect:/main/list";
		}
		
		//=> 정상접근이라면, JSP 페이지에 필요한 정보를 넘겨준다
		model.addAttribute("user", user);
		model.addAttribute("room", roomDto);
		model.addAttribute("list", list);
		model.addAttribute("count", list.size());
		
		if ( list.size() > 0 ) {
			model.addAttribute("tradeStatus", list.get( list.size()-1 ).getTradeStatus() );
		}
		
		//=> 그리고 프로필 이미지도 넘겨주기 (구매자, 판매자)
		params.put("userIdentity", String.valueOf(roomDto.getBuyer()));
		model.addAttribute("buyerImgPath", chattingService.getImgPath(params));		
		params.replace("userIdentity", String.valueOf(roomDto.getSeller()));
		model.addAttribute("sellerImgPath", chattingService.getImgPath(params));
		
		return "/trade/chatContent";
	}
	
	
	
	
	@GetMapping("/newCheck")
	public ResponseEntity< ChattingDto > newCheck(@RequestParam HashMap<String, String> params) {
		
		// 게시글 번호를 받고
		int roomNum = Integer.parseInt(params.get("roomNum"));
		
		// 그 게시글에 있는 채팅 갯수를 확인한다 (현재 기준)
		ChattingDto countAndStatus = chattingService.countCheck(roomNum);
		int newCount = countAndStatus.getCount();
		
		// 그리고, 기존 갯수와 비교 (기존)
		int oldCount = Integer.parseInt(params.get("count"));

		// 서로 다를때만 ( 업데이트 )
		if ( newCount != oldCount ) {			
			ChattingDto lastChat = chattingService.lastContent(roomNum);
			return ResponseEntity.status(HttpStatus.OK).body( lastChat );			
		}
		
		// 새로운 소식이 없을때는 아무것도 안보냄
		 return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/statusCheck")
	public ResponseEntity< String > statusCheck(@RequestParam HashMap<String, String> params) {
		
		int roomNum = Integer.parseInt(params.get("roomNum"));
		String tradeStatus = chattingService.countCheck(roomNum).getTradeStatus();		
		return ResponseEntity.status(HttpStatus.OK).body( tradeStatus );
	}
	
	@PostMapping("/tradeStatusUpdate")
	public ResponseEntity<Void> tradeStatusUpdate(@RequestParam HashMap<String, String> params, HttpSession session) {
		
		//=> 거래 진행 상태 업데이트 하기
		chattingService.tradeStatusUpdate(params);		
		
		
		//=> 만약, 요청받은 상태가 거래 완료일 경우 실행
		if ( params.get("status").equals("confirmed") == true ) {

			// (0) 필요한 정보 준비하기
			 ChattingRoomDto roomDto = chattingRoomService.contentView(params);
			 String buyerIdentity = devUtils.intToString(roomDto.getBuyer());
			 String sellerIdentity = devUtils.intToString(roomDto.getSeller()); 
			 String price = devUtils.intToString(roomDto.getPrice());
			 
			 // (1) buyer 찾아서 point 차감 
			 params.put("point", price);
			 params.put("userIdentity", buyerIdentity);
			 userService.pointDownByUserIdentity(params);
			 
			 // (2) seller 찾아서 point 올려주기
			 params.replace("userIdentity", sellerIdentity);
			 userService.pointUpByUserIdentity(params);			 
			
			// (3) trade 게시판 삭제하기
			 tradeService.tradeSuccess(params);
			 
			 // (4) 세션 정보 새로고침
			 UserDto user = devUtils.getUserInfo(session);
			 params.remove("identity");			 
			 String newPoint = userService.getPoint(params, session);
			 user.setPoint(newPoint);
			 session.setAttribute("userInfo", user);
			 
		}
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
