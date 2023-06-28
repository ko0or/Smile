package com.lgy.smile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
/* ============================================================================================================ >>
	
	★ AWS 배포시  수정해야할 사항 
	
	[1] devUtils 에서 저장경로 변경해야함
    [2] aws 배포용 REST API KEY 로 변경해줘야함 ★
    webapp/resources/js/api/kakaoLogin.js
	WEB-INF/views/trade/write.jsp
	WEB-INF/views/trade/edit.jsp
	WEB-INF/views/trade/list.jsp

	(x) WEB-INF/views/common/navbar.jsp
	(x) WEB-INF/views/user/login.jsp

<< ============================================================================================================ */
	
	
	
	
	
	// 기본 루트 사이트에 대한 정의 ★
	
	@GetMapping("/")
	public String rootMain1() { return "redirect:/main/list"; }
	
	@GetMapping("/main")
	public String rootMain2() { return "redirect:/main/list"; }
	
	@GetMapping("/main/")
	public String rootMain3() { return "redirect:/main/list"; }
	
	
	
	@GetMapping("/main/home")
	public String home() { return "default/home"; 	}
	
	
}
