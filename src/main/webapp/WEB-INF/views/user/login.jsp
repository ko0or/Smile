<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/user/login.css'</style>
<section>
<!-- -------------------------------------------------------------------------- -->

<div class="login-wrapper">

	<!-- 로그인 배너부분 -->
	<div class="logo">
		<h1>회원 로그인</h1>
		<p>반갑습니다- 싱글벙글 부산 커뮤니티 사이트입니다 ! </p>
	</div>
	
	<!-- 아디, 비밀번호 입력 폼 화면 -->
	<div class="form-wrapper">
		<form id="loginForm" action="login" method="POST">
		
			<div class="form-floating mb-3">
				<input name="id" type="email" class="form-control" id="floatingInput" placeholder="name@example.com"> 
				<label for="floatingInput">이메일 계정</label>
			</div>
			<div class="form-floating">
				<input name="password" type="password" class="form-control" id="floatingPassword" placeholder="Password"> 
				<label for="floatingPassword">비밀번호</label>
				<a href="#">비밀번호가 뭐..였더라 ..? (기억안날시)</a>
			</div>
			
		</form>
	</div>
	
	<!-- 간편 로그인 영역 -->
	<div class="btns">
		<button id="loginCheck" type="button" class="btn btn-primary">일반 로그인</button>
		<button id="kakaoLogin" type="button" class="btn btn-warning"><i class="fa-solid fa-comment"></i>
		카카오 간편 로그인</button>
	</div>
	
	<h1 style="color: grey;">OR</h1><br>
	<a href="createAccount">사이트 회원가입 (클릭)</a>
	
</div>


<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script src="../resources/js/api/kakaoLogin.js"></script>
<script>
$(document).ready(function() {
    
	// ★ 카카오 간편 로그인 버튼눌렀을경우 ============================================================= >>
	$("#kakaoLogin").click(function() {
		kakaoLogin();
	})
	
	// 패스워드 입력란에서 엔터(키코드 13) 누르면 로그인 버튼 클릭하게 만들기
	$("input[name='password']").keydown(function (e) {
		if ( e.keyCode == 13 ) {
			$("#loginCheck").click();
		} 
	})
	
	// 로그인 버튼 눌렀을때 처리
	$("#loginCheck").click(function() {
// 		$("#loginForm").submit();
		
		var formData = $("#loginForm").serialize();
		console.log("@# ====> " + formData);
		
		$.ajax({
			type: "POST"
		   ,data: formData
		   ,url: "login"
// 		   ,statusCode: {404 : function(data){console.log(data);}}
		
		   ,success: function(data, status){
// 		   ,success: function(data){
			   
			   console.log("data before");
			   console.log(data);			//200	// HTTP에러 발생하면 데이터 수신 못함(200만 받음)
			   console.log(status); 		//success
			   console.log("after data");
			   
			    if (data === 200) {
			    	console.log("로그인 성공")
				    location.href = "/smile/main/list";		// 컨트롤러의 URL 로 이동
// 				★ aws 배포시 => location.href = "/main/list";		
			    	
// 			    }else if(data === 404) {
// 			    	console.log("로그인 실패 => 비밀번호 불일치")
// 				    $(".loginFailed").html("<br><h3>wrong password</h3>");
			    	
// 			    }else if(data === 400) {
// 			    	console.log("로그인 실패 => 회원 아이디 조회 불가")
// 				    $(".loginFailed").html("<br><h3>no id found</h3>");
			    	
			    }
		   }
		   ,error: function(){
			   alert("로그인 실패했습니다.");
// 			   $(".loginFailed").html("<br><h3>로그인 실패. 아이디 혹은 비밀번호 확인해주세요.</h3>");
			   location.href = "login";		// 실패알람 확인 클릭하면 로그인 페이지로 이동
		   }
		});
	})
	
})// ~~ end
</script>
</html>


