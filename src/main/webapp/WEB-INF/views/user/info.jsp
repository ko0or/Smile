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
	
	<!-- 회원정보 수정 배너부분 -->
	<div class="logo">
		<h1>회원 정보 수정 화면</h1>
		<p>회원정보 수정 화면입니다! </p>
	</div>
		
	<!-- 아디, 비밀번호 수정 화면 -->
	<div class="form-wrapper">	
		<form id="modifyForm" action="#" method="POST">
		
			<div class="form-floating mb-3">
				<input name="nickname" type="text" class="form-control" id="floatingNickname" value="${ user.nickname }" placeholder="번쩍점프 장인"> 
				<label for="floatingNickname"><i>*</i>
				닉네임</label>
			</div>
							
			<div class="form-floating mb-3">
				<div name="id" type="email" class="form-control" id="floatingEmail" placeholder="name@example.com" style="color:grey">
				${ user.id }</div> 
				<label for="floatingEmail"><i>*</i>
				이메일 계정</label>
			</div>
			
			<div class="form-floating mb-3">
				<input name="password" type="password" class="form-control" id="floatingPassword" placeholder="Password"> 
				<label for="floatingPassword"><i>*</i>
				비밀번호</label>				
			</div>
			
			<div class="form-floating mb-3">
				<input name="password2" type="password" class="form-control" id="floatingPassword2" placeholder="Password">
				<label for="floatingPassword2"><i>*</i>
				비밀번호 재확인</label>				
			</div>
			
			<div class="form-floating mb-3">
				<div name="point" type="text" class="form-control" id="floatingPoint" placeholder="point" style="color:grey">
				${ user.point }</div>
				<label for="floatingPassword2"><i>*</i>
				포인트</label>				
			</div>
			
			<div class="form-floating mb-3">
				<div name="role" type="text" class="form-control" id="floatingRole" placeholder="role" style="color:grey">
				${ user.role }</div>
				<label for="floatingPassword2"><i>*</i>
				회원구분</label>				
			</div>
		</form>
	</div>

	<div class="btns">
		<button id="modifyInfo" type="button" class="btn btn-primary">회원정보 수정</button>
		<button id="logOut" type="button" class="btn btn-warning"><i class="fa-solid fa-comment"></i>
		로그아웃</button>
	</div>
	
	<h1 style="color: grey;">OR</h1><br>
	<a href="createAccount">회원탈퇴 (클릭)</a>

</div>









<!-- 	<div class="form-wrapper"> -->
<!-- 		<form action="#" method="POST"> -->
		
<!-- 			<h3 align="center" style="background-color: yellow;"> -->
<!-- 				<hr>회원 정보 수정 화면<br><hr> -->
<!-- 			</h3><br> -->
			
<!-- 			<div class="form-floating mb-3"> -->
<%-- 				<input name="nickname" type="text" class="form-control" id="floatingNickname" value="${ user.nickname }" placeholder="번쩍점프 장인">  --%>
<!-- 				<label for="floatingNickname"><i>*</i> -->
<!-- 				닉네임</label> -->
<!-- 			</div> -->
							
<!-- 			<div class="form-floating mb-3"> -->
<!-- 				<div name="id" type="email" class="form-control" id="floatingEmail" placeholder="name@example.com" style="color:grey"> -->
<%-- 				${ user.id }</div>  --%>
<!-- 				<label for="floatingEmail"><i>*</i> -->
<!-- 				이메일 계정</label> -->
<!-- 			</div> -->
			
<!-- 			<div class="form-floating mb-3"> -->
<!-- 				<input name="password" type="password" class="form-control" id="floatingPassword" placeholder="Password">  -->
<!-- 				<label for="floatingPassword"><i>*</i> -->
<!-- 				비밀번호</label>				 -->
<!-- 			</div> -->
			
<!-- 			<div class="form-floating mb-3"> -->
<!-- 				<input name="password2" type="password" class="form-control" id="floatingPassword2" placeholder="Password"> -->
<!-- 				<label for="floatingPassword2"><i>*</i> -->
<!-- 				비밀번호 재확인</label>				 -->
<!-- 			</div> -->
			
<!-- 			<div class="form-floating mb-3"> -->
<!-- 				<div name="point" type="text" class="form-control" id="floatingPoint" placeholder="point" style="color:grey"> -->
<%-- 				${ user.point }</div> --%>
<!-- 				<label for="floatingPassword2"><i>*</i> -->
<!-- 				포인트</label>				 -->
<!-- 			</div> -->
			
<!-- 			<div class="form-floating mb-3"> -->
<!-- 				<div name="role" type="text" class="form-control" id="floatingRole" placeholder="role" style="color:grey"> -->
<%-- 				${ user.role }</div> --%>
<!-- 				<label for="floatingPassword2"><i>*</i> -->
<!-- 				회원구분</label>				 -->
<!-- 			</div> -->
			
<!-- 		</form> -->
<!-- 	</div> -->


<!-- <input type="button" value="수정" onclick=""> -->
<!-- <input type="button" value="로그아웃" onclick=""> -->
<!-- <input type="button" value="탈퇴" onclick=""> -->


<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<script>
$(document).ready(function() {
    

})// ~~ end
</script>
</html>