
/* ================== 카카오 맵 API 사용  appkey  =============================================== >>

	★ AWS 배포시 => c2d1ab04a5b02c1ca16e392b9c82fd66
	
											or
	
	☆ 테스트할때 =>   149a80c17154419aa57d2cfae9d6a80d

<< =============================================================================================  */





window.Kakao.init("149a80c17154419aa57d2cfae9d6a80d");









function kakaoLogin() {
	// 카카오 로그인을 요청(로그인 요청에 대한 옵션 객체를 매개변수로 받음)
    window.Kakao.Auth.login({        
        
        // 로그인 성공 시 실행되는 콜백함수
        success: function(authObj) {
        
        	// 로그인 성공 후 사용자 정보 조회를 위해 호출(요청에 대한 옵션 객체를 매개변수로 받음)
            window.Kakao.API.request({
            	// 요청할 API 경로(현재 로그인한 사용자의 정보를 조회하는 API 경로임)
                url: '/v2/user/me'
                
                // API 요청이 성공했을 때 실행되는 콜백함수 (res 매개변수에 응답 데이터가 전달됨)
                , success: res => {
                    const kakao_account = res.kakao_account;
                    //console.log( "카카오 간편 로그인 성공 ============= >>" );
                    //console.log( kakao_account.email );
                    //console.log( "해당 카카오 계정으로 우리 사이트에 가입되어있는지 확인 = >>" );
                    
                    // jQuery의 AJAX를 사용하여 서버에 HTTP GET 요청(사이트 가입 여부 확인요청)을 보내는 함수
                    $.ajax({
                    	method: "GET"
                       ,data: { "id" : kakao_account.email }
                       ,url: "kakaoEmailCheck"
                       ,dataType: "json"
                       ,success : function(data){
                       		//console.log("success" + data);	// [object Object]
                       		//console.log("success" + data.exists); // true
                       		//console.log("성공이라는 건 데이터를 받았다는 뜻이지");
                       		
                       		if(data.exists){	// true 이면 로그인 처리
                       			//console.log("데이터 받는데 성공했고 받은 값이 true 이므로 로그인 처리하겠음");
                       			//location.href = "/smile/main/list";
                       			location.href = urlConverter("main/list");
                       			
                       		}else {
                       			//console.log("데이터 받는데 성공했고 받은 값이 false 이므로 회원가입 페이지로 넘어가겠음");
                       			//console.log("id값은? " + kakao_account.email);
                       			//location.href = "/smile/user/createAccount?id="+kakao_account.email;
                       			location.href = urlConverter("user/createAccount?id="+kakao_account.email);
                       		}
                       }//ajax success
                    })//ajax

                    kakaoLogout();
                }
            }) // ~ (종료) 카카오 정보조회 시도
          } // ~ (종료) 카카오 정보조회 성공시
      }) // ~ (종료) 카카오 로그인 요청
}


function kakaoLogout() {
    Kakao.API.request({
        url: '/v1/user/logout',
        success: function() {
        	console.log("로그아웃 성공");
        }
    })
}