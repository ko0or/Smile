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
		<form action="#" method="GET">
		
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

})// ~~ end
</script>
</html>