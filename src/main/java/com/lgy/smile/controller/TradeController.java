package com.lgy.smile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trade")
public class TradeController {

	
	// ★ trade(중고 거래) 글 목록
	@GetMapping("/list")
	public String tradeList() {
		return "trade/list";
	}

	
	// ★ trade(중고 거래) 글 쓰기
	@GetMapping("/write")
	public String tradeWrite() {
		return "trade/write";
	}
	
	
	// ★ trade(중고 거래) 글 읽기
	// .. 내용 ..
	
	
	// ★ trade(중고 거래) 글 수정
	// .. 내용 ..
	
	
	// ★ trade(중고 거래) 글 삭제
	// .. 내용 ..
	
	
	
}
