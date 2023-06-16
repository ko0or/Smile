package com.lgy.smile.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lgy.smile.dto.TradeDto;
import com.lgy.smile.service.TradeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/trade")
public class TradeController {

	@Autowired private TradeService service;

	// ★ trade(중고 거래) 글 목록
	@GetMapping("/list")
	public String tradeList(Model model) {
		log.info("@# list");

		ArrayList<TradeDto> list = service.list();
		model.addAttribute("list", list);

		return "trade/list";
	}

	
	@GetMapping("/write")
	public String tradeWrite() {
		return "trade/write";
	}
	
	
	
	// ★ trade(중고 거래) 글 쓰기
	@PostMapping("/write")
	public String tradeWrite(@RequestParam HashMap<String, String> params, MultipartFile imgPath) {
		log.info("@# write");

		service.write(params, imgPath);
		
		return "redirect:list";
	}

	// ★ trade(중고 거래) 글 읽기
	@RequestMapping("/write_view")
	public String tradeWrite_view() {
		log.info("@# write_view");

		return "trade/write_view";
	}

	// ★ trade(중고 거래) 글 수정
	@GetMapping("/modify")
	public String tradeModify(@RequestParam HashMap<String, String> params) {
		log.info("@# modify");

		service.modify(params);

		return "trade/modify";
	}

	// ★ trade(중고 거래) 글 삭제
	public String tradeDelete(@RequestParam HashMap<String, String> params) {
		log.info("@# delete");

		service.delete(params);

		return "trade/delete";
	}
	
	// 파일업로드
	@Controller
	public class UploadController {
		
		@PostMapping("/uploadFormAction")
		public void uploadFormAction(MultipartFile[] uploadFile) {
			
			for (MultipartFile multipartFile : uploadFile) {
				log.info("========================================================");
				/* 파일이름 출력 */log.info("multipartFile.getOriginalFilename() => " + multipartFile.getOriginalFilename()); 
				/* 파일크기 출력 */log.info("multipartFile.getSize() => " + multipartFile.getSize() );			
				log.info("========================================================");
				
				try { 				
					
					File uploadFolder = new File( "C:/upload/temp3/" );
					if (uploadFolder.exists() == false) { uploadFolder.mkdirs(); }
					// => 경로확인용 File 객체생성, 해당 경로가 없다면 하위폴더들을 만들어주기
					
					
					File saveFile = new File(uploadFolder.getPath(), multipartFile.getOriginalFilename());
					// => File saveFile = new File("업로드하고싶은 경로", "저장하고싶은 파일명.확장자");
					
					multipartFile.transferTo( saveFile ); 
					// => 위에있는 for-each 구문에서 받았던 객체의 transferTo() 메소드 사용하면 파일저장 가능
					// => 근데 저장할때 어디 경로에, 무슨 이름으로 저장할지 정보가 필요하니, 위에서 만든 File 객체를 매개변수로 사용 ★
					
				} catch (Exception e) { e.printStackTrace(); }
				
			} // ~ for 반복문 종료
		}
	}	
}
