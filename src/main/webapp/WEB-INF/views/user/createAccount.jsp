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
		<form id="createAccount" action="createAccount" method="POST">
			
			<div class="form-floating mb-3">
				<input name="nickname" type="text" class="form-control" id="floatingNickname" placeholder="번쩍점프 장인"> 
				<label for="floatingNickname"><i>*</i>
				닉네임</label>
			</div>			
							
			<div class="form-floating mb-3">
				<input name="id" type="email" class="form-control" id="floatingEmail" formaction="isDuplicated"  placeholder="a@naver.com"> 
				<label for="floatingEmail"><i>*</i>
				이메일 계정</label>
				
				<button id="isDuplicated" type="button" class="btn btn-primary">
				중복체크</button>
			</div>
			
			<div class="form-floating mb-3" style="display:none"  id="slideDown">
				<input name="code" class="form-control" id="floatingCode">
				<label for="floatingCode"><i>*</i>
				이메일 인증번호를 입력하세요</label>
				<button id="checkCode" type="button" class="btn btn-primary">
				인증번호 확인
				</button>
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
		<button id="register" type="button" disabled>회원가입</button>
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
	
	// 이메일 중복 확인
	$("#isDuplicated").click(function(){
		console.log("actionform 으로 이동함33")
		var formData = $("#floatingEmail").serialize();
		console.log("formData ===> " + formData);
		
		$.ajax({
			type: "POST"
		   ,data: formData
		   ,url: "isDuplicated"
		   ,success: function(data, code){
				
			   // 중복확인 후 이메일 인증번호 발송동의 여부
			   Swal.fire({
				    icon: 'success',
				    title: '본인확인 필요',
				    text: "이메일 주소로 인증번호 발송하시겠습니까?",
				    confirmButtonText: '확인' ,
				    showCancelButton: true ,
			    	cancelButtonText : '취소'
			    	
			    // 동의하면 이메일로 인증번호 발송
				}).then((result) => {
				  if (result.isConfirmed) {
					  Swal.fire('인증번호 발송 완료!', '', 'success')
					  
					  var formData = $("#floatingEmail").serialize();
					  console.log("인증번호 전송 확인 눌렀음 formData ===> " + formData);
					  
					  $.ajax({
						  type: "POST"
						 ,data: formData
						 ,url: "sendCode"
						 ,success: function(data){
							 console.log("인증번호 전송 완료")
						 }
					  });
					  
					  // 인증번호 입력창 보여주기 
				      $("#slideDown").slideDown();
				      
					  // 사용자가 인증번호 입력 후 확인 누르면 인증번호 비교
				      $("#checkCode").click(function(){
				    	  var formData = $("#floatingCode").serialize();
				    	  console.log("입력한 코드를 확인하기 위해서 보내기 formData ===> " + formData);
				    	  
				    	  $.ajax({
				    		  type: "POST"
				    		 ,data: formData
				    		 ,url: "checkCode"
 			    			 // 인증 성공 시
				    		 ,success: function(data){
				    			 console.log("success222")
				    			 
								   Swal.fire({
									    icon: 'success',
									    title: '인증 완료',
									    text: "이메일 인증이 완료되었습니다!",
									    showCancelButton: false,
									    confirmButtonText: '확인'
									})
									
									
									// 비밀번호까지 모두 입력되면 회원가입 활성화
				    				function activeEvent() {
				    					switch(!(formNickname.value && formEmail.value && formPassword.value && formPassword2.value)){
					    					case true : registerButton.disabled = true; break;
					    					case false : registerButton.disabled = false; break;
				    					}
				    				}
				    				
				    				function errorEvent() {
				    					console.log("errorEvent")
				    				}
				    				
									const formNickname = document.querySelector("#floatingNickname");
				    				const formEmail = document.querySelector("#floatingEmail");
				    				const formPassword = document.querySelector("#floatingPassword");
				    				const formPassword2 = document.querySelector("#floatingPassword2");
									const registerButton = document.querySelector("#register");
				    				
				    				formNickname.addEventListener('keyup', activeEvent);
				    				formEmail.addEventListener('keyup', activeEvent);
				    				formPassword.addEventListener('keyup', activeEvent);
				    				formPassword2.addEventListener('keyup', activeEvent);
				    				registerButton.addEventListener('keyup', errorEvent);
				    				
				    				$("#register").click(function(){
				    					$("#createAccount").submit();
				    					
				    				});
				    				
				    				
				    				
				    		 }
				    		 // 인증 실패 시
				    	  	 ,error: function(){
				    			 console.log("error222")
				    			 
								   Swal.fire({
									    icon: 'error',
									    title: '인증 실패',
									    text: "이메일 인증에 실패했습니다.",
									    showCancelButton: false,
									    confirmButtonText: '확인'
									})
				    	  	 }
				    	  });
				      });
				  }
		   		})
	   		}
		   , error : function(data, status){

			   Swal.fire({
				    icon: 'warning',
				    title: '이메일 중복',
				    text: "존재하는 이메일 계정입니다.",
				    showCancelButton: false,
				    confirmButtonText: '확인'
				})
		   }
		});
	});
	
})// ~~ end
</script>
</html>