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
import com.lgy.smile.dto.UserDto;
import com.lgy.smile.service.ChattingRoomService;
import com.lgy.smile.service.ChattingService;

@Controller
@RequestMapping("/chat")
public class ChattingController {
	
	@Autowired
	ChattingService chattingService;
	
	@Autowired
	ChattingRoomService chattingRoomService;
	
	@Autowired
	DevUtils devUtils;
	
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
	public String chattingWrite(@RequestParam HashMap<String, String> params) {
		chattingService.write(params);
		return "/write";
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
		ChattingRoomDto roomDto = chattingRoomService.contentView(params);
		ArrayList<ChattingDto> list = chattingService.contentView(roomDto.getIdentity());
		UserDto user = devUtils.getUserInfo(session);
		model.addAttribute("user", user);
		model.addAttribute("room", roomDto);
		model.addAttribute("list", list);
		return "/trade/chatContent";
	}
	
}
