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
	
	<!-- 아이디, 비밀번호 입력 폼 화면 -->
	<div class="form-wrapper">
		<form id="loginForm" action="login" method="POST">
		
			<div class="form-floating mb-3">
				<input name="id" type="email" class="form-control" id="floatingInput" placeholder="name@example.com"> 
				<label for="floatingInput">이메일 계정</label>
			</div>
			<div class="form-floating">
				<input name="password" type="password" class="form-control" id="floatingPassword" placeholder="Password"> 
				<label for="floatingPassword">비밀번호</label>
				<a href="#" id="forgotPassword">비밀번호가 뭐..였더라 ..? (기억안날시)</a>
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
// 			   alert("로그인 실패했습니다.");
			   
			   Swal.fire({

				    icon: 'warning',
				    title: '로그인 실패',
				    text: "아이디 혹은 비밀번호를 다시 확인해주세요",
				    showCancelButton: false,
				    confirmButtonText: '확인'
				})

// 			   location.href = "login";		// 실패알람 확인 클릭하면 로그인 페이지로 이동
		   }
		});
	}) // 로그인 버튼 처리 ~~end
	
	// 비밀번호 기억안날시 링크 클릭했을 때 처리
	$("#forgotPassword").click(function(){
		
		// 작동함
// 		var inputEmail = prompt("이메일 주소 입력 ");
// 		console.log(inputEmail);

		Swal.fire({
			  title: '이메일 주소를 입력해주세요',
			  input: 'text',
			  inputAttributes: {
			    autocapitalize: 'off'
			  },
			  showCancelButton: true,
			  confirmButtonText: '전송',
			  
// ====================== 작업 완료. 작동되는 듯? (비밀번호 찾기 => 가입여부 확인 추가) ================================
			  showLoaderOnConfirm: true,
			  preConfirm: (login) => {
			    return $.ajax({
			    	type: "POST"
			       ,data: { id: login}
			       ,url: "checkEmailExists"
			       ,dataType: "json"
		    	}).then(response => {
			        if (response.exists) {
			          return login;
			        } else {
			        	throw new Error("가입되지 않은 이메일 주소입니다.");
			        }
			      })
			      .catch(error => {
			        Swal.showValidationMessage(error.message);
			      });
			  },
			  allowOutsideClick: () => !Swal.isLoading()
			  
// 			  showLoaderOnConfirm: true,
// 			  preConfirm: (login) => {
// 			    return fetch(`//api.github.com/users/${login}`)
// 			      .then(response => {
// 			        if (!response.ok) {
// 			          throw new Error(response.statusText)
// 			        }
// 			        return response.json()
// 			      })
// 			      .catch(error => {
// 			        Swal.showValidationMessage(
// 			          `Request failed: ${error}`
// 			        )
// 			      })
// 			  },
// 			  allowOutsideClick: () => !Swal.isLoading()
// ====================== 작업 완료. 작동되는 듯? (비밀번호 찾기 => 가입여부 확인 추가) ================================
	
			}).then((result) => {
			  if (result.isConfirmed) {
			    Swal.fire({
			      title: "임시 비밀번호 이메일 발송 완료!"
// 			      title: `${result.value.login}'s avatar`,
// 			      imageUrl: result.value.avatar_url
			    })
			    
			    var email = result.value;
			    var formData = {"id":email}
			    
			    console.log(email);
			    console.log(formData);
			    
			    $.ajax({
			    	type: "POST"
			       ,data: formData
			       ,url: "sendTempPwd"
			       ,success: function(data){
			    	   console.log("success" +data);
			       }
			       ,error: function(){
			    	   console.log("error" +data);
			       }
			    });
			  }
			})
		
	});
	
	
})// ~~ end
</script>
</html>









