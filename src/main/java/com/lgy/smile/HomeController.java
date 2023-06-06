package com.lgy.smile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
/* ============================================================================================================ >>
	
	★ AWS 배포시  수정해야할 사항 
	
	 [1] views/common/navbar.jsp 파일에 있는 a href 속성 값(경로) 변경
	    .. 배포시엔 /smile 패키지 경로가 포함되면 페이지를 못찾는 에러(HTTP 404) 발생하니,  /smile 내용을 뺴줘야함
	  
	[2] views/trade/write.jsp
      .. <script> 부분에 REST API KEY 를 변경해줘야함
      .. 개발 테스트 단계에서 사용하는 IP, PORT에 맞는 REST API 키 ->  배포시 사용하는 IP, PORT에 맞는 REST API 키

      
<< ============================================================================================================ */
	
	
	
	
	
	// 기본 루트 사이트에 대한 정의 ★
	
	@GetMapping("/")
	public String root() {
		return "redirect:main/home";		
	}
	
	@GetMapping("/main/home")
	public String home() {
		return "default/home";		
	}
	
	@GetMapping("/main")
	public String rootMain() {
		return "redirect:main/list";
	}
	
	
}
