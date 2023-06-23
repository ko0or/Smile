<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<!-- <style>@import '../resources/css/user.css'</style> -->
<style>@import '../resources/css/user/info.css'</style>
<section>
<!-- -------------------------------------------------------------------------- -->

<div class="info-wrapper">
	
	<div class="logo">
		<h1>회원 정보</h1>
		<p>회원정보 화면입니다! </p>
	</div>
		
	<!-- 아디, 비밀번호 수정 화면 -->
	<div class="form-wrapper">	
		<form id="modifyForm" action="modify" method="POST">
		
			<div class="form-floating mb-3">
				<div name="nickname" type="text" class="form-control" id="floatingNickname">
				${ user.nickname }</div> 
				<label for="floatingNickname"><i>*</i>
				닉네임</label>
			</div>
							
			<div class="form-floating mb-3">
				<div name="id" type="email" class="form-control" id="floatingEmail">
				${ user.id }</div> 
				<label for="floatingEmail"><i>*</i>
				이메일 계정</label>
			</div>
			
<!-- 			<div class="form-floating mb-3"> -->
<!-- 				<div name="password" type="password" class="form-control" id="floatingPassword"> -->
<%-- 				${ user.pwd }</div>  --%>
<!-- 				<label for="floatingPassword"><i>*</i> -->
<!-- 				비밀번호</label>				 -->
<!-- 			</div> -->
			
<!-- 			<div class="form-floating mb-3"> -->
<!-- 				<div name="password2" type="password" class="form-control" id="floatingPassword2"> -->
<%-- 				${ user.pwd }</div> --%>
<!-- 				<label for="floatingPassword2"><i>*</i> -->
<!-- 				비밀번호 재확인</label>				 -->
<!-- 			</div> -->
			
			<div class="form-floating mb-3">
				<div name="role" type="text" class="form-control" id="floatingRole">
				${ user.role }</div>
				<label for="floatingPassword2"><i>*</i>
				회원구분</label>				
			</div>
			
			<div class="form-floating mb-3">
				<div name="point" type="text" class="form-control" id="floatingPoint">
				${ user.point }</div>
				<label for="floatingPassword2"><i>*</i>
				포인트</label>
				<button id="pointUp" type="button" class="btn btn-primary">
				포인트 충전
				</button>		
			</div>
		</form>
	</div>

	<div class="btns">
		<button id="modifyInfo" type="button" class="btn btn-primary">회원정보 수정으로 가기</button>
		<button id="logOut" type="button" class="btn btn-warning"><i class="fa-solid fa-comment"></i>
		로그아웃</button>
	</div>

</div>

<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<script src="../resources/js/user/info.js"></script>
<script src="../resources/js/api/easyPayment.js"></script>
</html>