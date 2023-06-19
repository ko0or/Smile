package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.ChattingRoomDto;
import com.lgy.smile.service.ChattingRoomService;

@Controller
@RequestMapping("/chatroom")
public class ChattingRoomController {
	
	@Autowired
	private ChattingRoomService chattingRoomService;
	
	@GetMapping("/getChattingRooms")
	public ResponseEntity<List<ChattingRoomDto>> getChattingRooms(@RequestParam HashMap<String, String> params) {
		ArrayList<ChattingRoomDto> list = chattingRoomService.list();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@PostMapping("/getChattingRoom")
	public ResponseEntity<ChattingRoomDto> getChattingRoom(@RequestParam HashMap<String, String> params) {
		ChattingRoomDto dto = chattingRoomService.contentView(params);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@PostMapping("/write")
	public String chattingRoomWrite(@RequestParam HashMap<String, String> params) {
		chattingRoomService.write(params);
		return "/trade/test/chatRoomListTest";
	}
	
	@PostMapping("/delete")
	public String chattingRoomDelete(@RequestParam HashMap<String, String> params) {
		chattingRoomService.delete(params);
		return "/trade/test/chatRoomListTest";
	}
	
	@RequestMapping("/chatRoomCreateTest")
	public String chatRoomCreateTest() {
		
		return "/trade/test/chatRoomCreateTest";
	}
	
	@GetMapping("/chatRoomListTest")
	public String list(@RequestParam HashMap<String, String> params, Model model) {
		ArrayList<ChattingRoomDto> list = chattingRoomService.list();
		model.addAttribute("list", list);
		return "/trade/test/chatRoomListTest";
	}
	
}
