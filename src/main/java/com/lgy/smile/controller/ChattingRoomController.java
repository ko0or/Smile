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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgy.smile.dto.ChattingRoomDto;
import com.lgy.smile.service.ChattingRoomService;

@Controller
@RequestMapping("/chatroom")
public class ChattingRoomController {
	
	@Autowired
	private ChattingRoomService chattingRoomService;
	
	@GetMapping("/list")
	public ResponseEntity<List<ChattingRoomDto>> getChattingRooms(@RequestParam HashMap<String, String> params) {
		ArrayList<ChattingRoomDto> list = chattingRoomService.list();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping("/contentView")
	public ResponseEntity<ChattingRoomDto> getChattingRoom(@RequestParam HashMap<String, String> params) {
		ChattingRoomDto dto = chattingRoomService.contentView(params);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@GetMapping("/write")
	public String chattingRoomWrite(@RequestParam HashMap<String, String> params) {
		chattingRoomService.write(params);
		return "/write";
	}
	
}
