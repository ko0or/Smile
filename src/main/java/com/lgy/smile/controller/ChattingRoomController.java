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
import com.lgy.smile.dto.MyChattingRoomDto;
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.ChattingRoomService;
import com.lgy.smile.service.ChattingService;

@Controller
@RequestMapping("/chatroom")
public class ChattingRoomController {
	
	@Autowired private ChattingService chattingService;
	@Autowired private ChattingRoomService chattingRoomService;
	@Autowired private DevUtils devUtils;
	
//	채팅방 전체 목록 불러오는 메소드
	@GetMapping("/getChattingRooms")
	public ResponseEntity<List<ChattingRoomDto>> getChattingRooms() {
		ArrayList<ChattingRoomDto> list = chattingRoomService.list();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
//	board 아이디(게시글 번호)와 buyer 아이디(구매자 번호)로 해당하는 채팅방 정보 불러오는 메소드
	@PostMapping("/getChattingRoom")
	public ResponseEntity<ChattingRoomDto> getChattingRoom(@RequestParam HashMap<String, String> params) {
		ChattingRoomDto dto = chattingRoomService.contentView(params);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
//	board 아이디(게시글 번호), seller 아이디(판매자 번호)와 buyer 아이디(구매자 번호)로 채팅방 작성
	@RequestMapping("/write")
	public String chattingRoomWrite(@RequestParam HashMap<String, String> params, HttpSession session, Model model) {
//		param으로는 board id / seller id 넘어옴
//		buyer id를 session에서 받아서 넣어줌
		params.put("buyer", devUtils.getUserIdentityToString(session));
//		채팅룸 dto roomDto 객체에 board id / seller id / buyer id로 채팅방 생성하는 chattingroom service 호출해서 담음
		ChattingRoomDto roomDto = chattingRoomService.contentView(params);
//		유저 dto user에 세션에서 유저 정보 받아옴
		UserDto user = devUtils.getUserInfo(session);
		try {
//			user가 없으면(로그인 안돼있으면) 로그인창으로 보내기
			user.toString();
		} catch (Exception e) {
			return "/user/login";
		}
//		임시로 판매자와 내 정보가 같으면(내 글 문의하기 눌렀을 경우) 로그인 창으로 보내기
		if(Integer.parseInt(params.get("seller")) == user.getIdentity()) {
			return "redirect:/trade/list";
		}
		try {
//			roomDto가 있으면(채팅방이 이미 존재하면) 채팅방번호로 채팅들 조회하는 서비스 호출해서 채팅dto리스트 list에 담음
			roomDto.toString();
			ArrayList<ChattingDto> list = chattingService.contentView(roomDto.getIdentity());
//			세션 정보는 user, 채팅방정보는 room, 채팅리스트는 list에 담아서 chatContent 채팅방 페이지로 리턴
			model.addAttribute("user", user);
			model.addAttribute("room", roomDto);
			model.addAttribute("list", list);
			
			return "redirect:/chat/chatContent?board="+ roomDto.getBoard()  +"&buyer="+roomDto.getBuyer();
		} catch(Exception e) {
//			roomDto가 없으면(채팅방이 없으면) board id, buyer id, seller id로 채팅방 생성하는 서비스 호출해서 생성
			chattingRoomService.write(params);
//			생성한 채팅방 정보를 다시 roomDto에 담음
			roomDto = chattingRoomService.contentView(params);
//			roomDto의 채팅방 번호로 조회한 채팅들 전부를 list에 담음
			ArrayList<ChattingDto> list = chattingService.contentView(roomDto.getIdentity());
//			세션 정보는 user, 채팅방정보는 room, 채팅리스트는 list에 담아서 chatContent 채팅방 페이지로 리턴
			model.addAttribute("user", user);
			model.addAttribute("room", roomDto);
			model.addAttribute("list", list);
			return "/trade/chatContent";			
		}
	}
	
//	채팅방 삭제
	@PostMapping("/delete")
	public String chattingRoomDelete(@RequestParam HashMap<String, String> params) {
		chattingRoomService.delete(params);
		return "/trade/test/chatRoomListTest";
	}
	
//	채팅방 생성 테스트
	@RequestMapping("/chatRoomCreateTest")
	public String chatRoomCreateTest() {
		
		return "/trade/test/chatRoomCreateTest";
	}
	
//	내 채팅방 리스트
	@GetMapping("/myChatRoomList")
	public String myChatRoomList(@RequestParam HashMap<String, String> params,HttpSession session, Model model) {
		ArrayList<MyChattingRoomDto> list = chattingRoomService.myChatRoomList(params);
		UserDto user = devUtils.getUserInfo(session);
		model.addAttribute("user", user);
		model.addAttribute("list", list);
		return "/trade/myChatRoomList";
	}
	
//	채팅방 리스트 테스트
	@GetMapping("/chatRoomListTest")
	public String list(@RequestParam HashMap<String, String> params, Model model) {
		ArrayList<ChattingRoomDto> list = chattingRoomService.list();
		model.addAttribute("list", list);
		return "/trade/test/chatRoomListTest";
	}
	
	@GetMapping("/chatRoomListSeller")
	public String chatRoomListSeller(@RequestParam HashMap<String, String> params, Model model) {
		ArrayList<ChattingRoomDto> list = chattingRoomService.sellerList(params);
		model.addAttribute("list", list);
		return "/trade/test/chatRoomListTest";
	}
	
	
	
	
	
	
	
	
	
	
	
	
//============================================================================================== >>
	
	
//	내 채팅방 리스트 (AJAX 응답)
	@GetMapping("/myChatRoomListAJAX")
	public ResponseEntity< ArrayList<MyChattingRoomDto> > myChatRoomListAJAX(@RequestParam HashMap<String, String> params,HttpSession session, Model model) {
		
		if ( devUtils.isLogin(session) == true ) { params.put("userId", devUtils.getUserIdentityToString(session)); }
		ArrayList<MyChattingRoomDto> list = chattingRoomService.myChatRoomList(params);
		return ResponseEntity.status(HttpStatus.OK).body( list );
	}
	

//	
//	@GetMapping("/myChatRoomList")
//	public String myChatRoomList(@RequestParam HashMap<String, String> params,HttpSession session, Model model) {
//		ArrayList<MyChattingRoomDto> list = chattingRoomService.myChatRoomList(params);
//		UserDto user = devUtils.getUserInfo(session);
//		model.addAttribute("user", user);
//		model.addAttribute("list", list);
//		return "/trade/myChatRoomList";
//	}
	
	
	
	
}
