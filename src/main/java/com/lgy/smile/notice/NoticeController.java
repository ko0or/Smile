package com.lgy.smile.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	// ★ notice(공지) 목록
	@GetMapping("/list")
	public String noticeList() {
		return "notice/list";
	}

	
	// ★ notice(공지) 글 쓰기
	@GetMapping("/write")
	public String noticeWrite() {
		return "notice/write";
	}
	
	
	// ★ notice(공지) 글 읽기
	@GetMapping("/read")
	public String noticeRead() {
		return "notice/read";
	}
	
	
	// ★ notice(공지) 글 수정
	@GetMapping("/edit")
	public String noticeEdit() {
		return "notice/edit";
	}

	
	// ★ notice(공지) 글 수정
	// ... 작성 ...
	
	
	
}
