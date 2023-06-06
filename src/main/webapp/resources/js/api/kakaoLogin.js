
/* ================== 카카오 맵 API 사용  appkey  =============================================== >>

	★ AWS 배포시 => c2d1ab04a5b02c1ca16e392b9c82fd66
	
											or
	
	☆ 테스트할때 =>   149a80c17154419aa57d2cfae9d6a80d

<< =============================================================================================  */





window.Kakao.init("149a80c17154419aa57d2cfae9d6a80d");









function kakaoLogin() {
    window.Kakao.Auth.login({        
        
        // 로그인 성공시 정보조회하기
        success: function(authObj) {
            window.Kakao.API.request({
                url: '/v2/user/me'
                , success: res => {
                    const kakao_account = res.kakao_account;
                    console.log( "접속자 이메일 아디 ============= >>" );
                    console.log( kakao_account.email );
                    console.log( "============================== >>" );
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