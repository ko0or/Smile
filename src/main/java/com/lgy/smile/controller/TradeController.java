package com.lgy.smile.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lgy.smile.dto.MainBoardDto;
import com.lgy.smile.dto.TradeDto;
import com.lgy.smile.service.TradeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/trade")
public class TradeController {

	@Autowired private TradeService tradeService;

	// ★ trade(중고 거래) 글 목록
	@GetMapping("/list")
	public String tradeList(Model model) {
		log.info("@# list");

		ArrayList<TradeDto> list = tradeService.list();
		model.addAttribute("list", list);
		model.addAttribute("show", true);
		

		return "trade/list";
	}
	
	// ★ trade(중고 거래) TradeDto에서 받아온 값들
	@GetMapping("/getPosts")
	public ResponseEntity< ArrayList<TradeDto> > getPosts() {
		return ResponseEntity.status(HttpStatus.OK).body( tradeService.list() );
	}
	
	
	

	// ★ trade(중고 거래) 글 쓰기(get방식)	
	@GetMapping("/write")
	public String tradeWrite() {
		return "trade/write";
	}
	
	
	// ★ trade(중고 거래) 글 쓰기(post방식)
	@PostMapping("/write")
	public String tradeWrite(@RequestParam HashMap<String, String> params, MultipartFile[] imgPath, HttpSession session) {
		log.info("@# write");

		tradeService.write(params, imgPath, session);
		
		
		return "redirect:list";
	}

	// ★ trade(중고 거래) 글 읽기
	@RequestMapping("/write_view")
	public String tradeWrite_view() {
		log.info("@# write_view");

		return "trade/write_view";
	}

	// ★ trade(중고 거래) 글 수정 (★ 화면) 
	@GetMapping("/modify")
	public String tradeModify(@RequestParam HashMap<String, String> params, Model model) {
		
		log.info("@# modify");		 
		model.addAttribute("board", tradeService.contentView(params));
		
		return "trade/edit";
	}
	
	// ★ trade(중고 거래) 글 수정 (★ 처리)
	@PostMapping("/modify")
	public String tradeModify(@RequestParam HashMap<String, String> params, MultipartFile[] imgPath, HttpSession session) {
		
		log.info("@# modify");
		tradeService.modify(params, imgPath, session);	
		
		return "redirect:list";
	}

	// ★ trade(중고 거래) 글 삭제
	@GetMapping("/delete")
	public String tradeDelete(@RequestParam HashMap<String, String> params, HttpSession session) {
		log.info("@# delete");

		tradeService.delete(params, session);

		return "redirect:list";
	}
	
	// ======= ★<img src="요기들어갈꺼 리턴해주는 메소드"> ============================= >
		@ResponseBody
		@GetMapping("/display")
		public ResponseEntity<byte[]> getFile(String fileName) {
	    
	 
	    	// 리턴용 객체와 파일 조회용 객체생성
			ResponseEntity<byte[]> result = null;		
			File file = new File(fileName);

			
			try {
				
				// 화면에 무슨 타입으로 보여줄지 + 해당 타입으로 뭘 보여줄지 작성
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Type", Files.probeContentType(file.toPath()));
				result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
				

	        // 오류발생했을땐 해당 메시지 + null 값 리턴
			} catch (Exception e) { e.printStackTrace();}		
			return result;
		}
}
