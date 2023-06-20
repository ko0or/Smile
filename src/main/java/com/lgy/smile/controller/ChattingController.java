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

import com.lgy.smile.dto.ChattingDto;
import com.lgy.smile.dto.ChattingRoomDto;
import com.lgy.smile.service.ChattingRoomService;
import com.lgy.smile.service.ChattingService;

@Controller
@RequestMapping("/chat")
public class ChattingController {
	
	@Autowired
	ChattingService chattingService;
	
	@Autowired
	ChattingRoomService chatttingRoomService;
	
	@GetMapping("/getChattings")
	public ResponseEntity<List<ChattingDto>> getChattings(@RequestParam HashMap<String, String> params) {
		ArrayList<ChattingDto> list = chattingService.list();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@PostMapping("/getChatting")
	public ResponseEntity<List<ChattingDto>> getChatting(@RequestParam HashMap<String, String> params) {
		ChattingRoomDto roomDto = chatttingRoomService.contentView(params);
		ArrayList<ChattingDto> list = chattingService.contentView(roomDto.getIdentity());
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@PostMapping("/write")
	public String chattingRoomWrite(@RequestParam HashMap<String, String> params) {
		chattingService.write(params);
		return "/write";
	}
	
	@RequestMapping("/chatCreateTest")
	public String chatCreateTest() {
		
		return "/trade/test/chatCreateTest";
	}
	
	@GetMapping("/chatListTest")
	public String list(@RequestParam HashMap<String, String> params, Model model) {
		ArrayList<ChattingDto> list = chattingService.list();
		model.addAttribute("list", list);
		return "/trade/test/chatListTest";
	}

}
