<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/user/createAccount.css'</style>
<section>
<!-- -------------------------------------------------------------------------- -->

<div class="createAccount-wrapper">
	
	<!-- 로그인 배너부분 -->
	<div class="logo">
		<h1>회원 가입</h1>
		<p>새로운 회원은 언제나 환영이죠 !</p>
	</div>
	
	<!-- 아디, 비밀번호 입력 폼 화면 -->
	<div class="form-wrapper">
		<form action="#" method="POST">
		
			<div class="form-floating">
				<input name="password2" type="text" class="form-control" id="floatingName" placeholder="홍길동"> 
				<label for="floatingName"><i>*</i>
				이름</label>				
			</div>			
			
			<div class="form-floating mb-3">
				<input name="nickname" type="text" class="form-control" id="floatingNickname" placeholder="번쩍점프 장인"> 
				<label for="floatingNickname"><i>*</i>
				닉네임</label>
			</div>			
							
			<div class="form-floating mb-3">
				<input name="id" type="email" class="form-control" id="floatingEmail" placeholder="name@example.com"> 
				<label for="floatingEmail"><i>*</i>
				이메일 계정</label>
				
				<button id="isDuplicated" type="button" class="btn btn-primary" >
				중복체크</button>
			</div>
			
			<div class="form-floating">
				<input name="password" type="password" class="form-control" id="floatingPassword" placeholder="Password"> 
				<label for="floatingPassword"><i>*</i>
				비밀번호</label>				
			</div>
			
			<div class="form-floating">
				<input name="password2" type="password" class="form-control" id="floatingPassword2" placeholder="Password"> <label for="floatingPassword2"><i>*</i>
				비밀번호 재확인</label>				
			</div>
			

			
		</form>
	</div>
	
	<!-- 간편 로그인 영역 -->
	<div class="btns">
		<button id="loginCheck" type="button">회원가입</button>
	</div>
	
</div>


<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<script>
$(document).ready(function() {
    
// ★ 입력된 비밀번호와  재확인에 입력된 내용이 서로 같은지 확인하는 내용들 ==================== >>
	$("#floatingPassword").on("keyup" ,function() {
		if ($("#floatingPassword2").val().length > 0 ) {
			if ($(this).val() == $("#floatingPassword2").val() ) {
				$("#floatingPassword2").css("backgroundColor", "white");
				$("#floatingPassword2").next().html("<i>*</i> 비밀번호가 일치합니다");
			} else {
				$("#floatingPassword2").css("backgroundColor", "yellow");
				$("#floatingPassword2").next().html("<i>*</i> 비밀번호가 다릅니다.");
			}
		}
	})
	
	$("#floatingPassword2").on("keyup" ,function() {
		if ($(this).val().length > 0 ) {
			if ( $(this).val() == $("#floatingPassword").val() ) {
				$(this).css("backgroundColor", "white");
				$(this).next().html("<i>*</i> 비밀번호가 일치합니다");
			} else {
				$(this).css("backgroundColor", "yellow");
				$(this).next().html("<i>*</i> 비밀번호가 다릅니다.");
			}
		}
	})

})// ~~ end
</script>
</html>