package com.lgy.smile.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@GetMapping("/list")
	public String noticeList() {
		return "notice/list";
	}
	
	@GetMapping("/write")
	public String noticeWrite() {
		return "notice/write";
	}
	
	@GetMapping("/read")
	public String noticeRead() {
		return "notice/read";
	}
	
	@GetMapping("/edit")
	public String noticeEdit() {
		return "notice/edit";
	}
	
	
}
