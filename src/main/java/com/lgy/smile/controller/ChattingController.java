package com.lgy.smile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.ChattingDto;
import com.lgy.smile.service.ChattingService;

@Controller
@RequestMapping("/chat")
public class ChattingController {
	
	@Autowired
	ChattingService chattingService;
	
	@GetMapping("/list")
	public ResponseEntity<List<ChattingDto>> getChattings(@RequestParam HashMap<String, String> params) {
		ArrayList<ChattingDto> list = chattingService.list();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping("/contentView")
	public ResponseEntity<ChattingDto> getChatting(@RequestParam HashMap<String, String> params) {
		ChattingDto dto = chattingService.contentView(params);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@GetMapping("/write")
	public String chattingRoomWrite(@RequestParam HashMap<String, String> params) {
		chattingService.write(params);
		return "/write";
	}

}
