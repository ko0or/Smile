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
		
		params.put("buyer", devUtils.getUserIdentityToString(session));
		ChattingRoomDto roomDto = chattingRoomService.contentView(params);
		UserDto user = devUtils.getUserInfo(session);
		if(Integer.parseInt(params.get("seller")) == user.getIdentity()) {
			return "redirect:/trade/list";
		}
		try {
			roomDto.toString();
			ArrayList<ChattingDto> list = chattingService.contentView(roomDto.getIdentity());
			
			model.addAttribute("user", user);
			model.addAttribute("room", roomDto);
			model.addAttribute("list", list);
			return "/trade/chatContent";
		} catch(Exception e) {
			chattingRoomService.write(params);
			roomDto = chattingRoomService.contentView(params);
			ArrayList<ChattingDto> list = chattingService.contentView(roomDto.getIdentity());
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
}
