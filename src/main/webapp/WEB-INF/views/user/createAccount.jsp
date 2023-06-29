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

			<c:choose>
				<c:when test="${ id == null }">
					<div class="form-floating mb-3">
						<input name="id" type="email" class="form-control" id="floatingEmail" formaction="isDuplicated"  placeholder="a@naver.com"> 
						<label for="floatingEmail"><i>*</i> 
						이메일 계정</label>
						<button id="isDuplicated" type="button" class="btn btn-primary">
						중복체크</button>
					</div>
				</c:when>
			</c:choose>

			<c:choose>
				<c:when test="${ id != null }">
					<div class="form-floating mb-3">
						<input name="id" type="email" class="form-control" id="floatingEmail" formaction="isDuplicated" style="text-align: left" value="${ id }" readonly="readonly">
						<label for="floatingEmail"><i>*</i> 
						이메일 계정 카카오 로그인</label>
<!-- 						<button id="isDuplicated" type="button" class="btn btn-primary"> -->
<!-- 						중복체크</button> -->
					</div>
				</c:when>
			</c:choose>
	
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
	
// ====================== 일반 회원가입 처리 ==================================================
	$("#isDuplicated").click(function(){
		// 이메일 중복 확인
		// id 가 floatingEmail 인 input 태그의 데이터(사용자가 입력한 이메일 주소)를 변수로 저장
		var formData = $("#floatingEmail").serialize();
		
		$.ajax({
			type: "POST"
		   ,data: formData
		   ,url: "isDuplicated"
		   ,success: function(data, code){
			   // 중복확인 버튼 클릭 후 이메일 인증번호 발송동의 여부를 묻는 스왈창
			   Swal.fire({
				    icon: 'success',
				    title: '본인확인 필요',
				    text: "이메일 주소로 인증번호 발송하시겠습니까?",
				    confirmButtonText: '확인' ,
				    showCancelButton: true ,
			    	cancelButtonText : '취소'
			    	
				}).then((result) => {
			      // "확인" 클릭으로 이메일 인증 동의하면 인증번호 발송
				  if (result.isConfirmed) {
					  Swal.fire('인증번호 발송 완료!', '', 'success')
					  
					  // "다시" id 가 floatingEmail 인 input 태그의 데이터(사용자가 입력한 이메일 주소)를 변수로 저장
					  var formData = $("#floatingEmail").serialize();
					  
					  $.ajax({
						  type: "POST"
						 ,data: formData
						 ,url: "sendCode"
					  });
					  
					  // 인증번호 입력창 보여주기 
				      $("#slideDown").slideDown();
				      
					  // 사용자가 인증번호 입력 후 확인 누르면 인증번호 비교
				      $("#checkCode").click(function(){
		  		    	  // id 가 floatingCode 인 input 태그의 데이터(사용자가 입력한 인증번호)를 변수로 저장
				    	  var formData = $("#floatingCode").serialize();
				    	  
				    	  $.ajax({
				    		  type: "POST"
				    		 ,data: formData
				    		 ,url: "checkCode"
 			    			 // 인증 성공 시
				    		 ,success: function(data){
								   Swal.fire({
									    icon: 'success',
									    title: '인증 완료',
									    text: "이메일 인증이 완료되었습니다!",
									    showCancelButton: false,
									    confirmButtonText: '확인'
									// 인증 성공하면 이메일 계정 입력부분 비활성화 및 색상변경(grey)
									}).then(function(){
											$("#floatingEmail").prop("readonly", true);
											$("#floatingEmail").css("color", "grey");
											$("input[name='nickname']").prop("disabled", true);
											$("input[name='id']").prop("disabled", true);
											$("input[name='code']").prop("disabled", true);
											
											$("#isDuplicated").removeClass("btn-primary");
											$("#isDuplicated").addClass("btn-secondary");
											$("#isDuplicated").prop("disabled", true);
											
											$("#checkCode").removeClass("btn-primary");
											$("#checkCode").addClass("btn-secondary");
											$("#checkCode").prop("disabled", true);

											$("#register").addClass("btn btn-primary")
									});
									
									// 비밀번호까지 모두 입력되면 회원가입 버튼 활성화
				    				function activeEvent() {
				    					switch(!(formNickname.value && formEmail.value && formPassword.value && formPassword2.value)){
					    					case true :  registerButton.disabled = true; break;
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
				    				
				    				// 회원가입 처리
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
				    	  }); // ajax (checkCode)
				      });	// click (checkCode)
				  }	// 이메일 주소로 인증번호 발송하시겠습니까? "확인" 버튼 클릭했을 시
		   		})// then(result)
	   		} // 이메일 중복확인 성공하는 경우
		   , error : function(data, status){
			   Swal.fire({
				    icon: 'warning',
				    title: '이메일 중복',
				    text: "존재하는 이메일 계정입니다.",
				    showCancelButton: false,
				    confirmButtonText: '확인'
				})
		   }
		}); // ajax (isDuplicated)
	}); // click (isDuplicated)
})// ~~ end


//====================== 카카오 회원가입 처리 ==================================================
// 카카오 로그인으로 회원가입 화면으로 넘어온 경우에 input 태그에 모두 입력되면 회원가입 버튼 활성화
if(${ id != null }){
	$(document).ready(function() {
		
	    // input 태그에 입력된 값들이 null 이 아닌 경우 true 반환하는 함수
	    function checkInputFields() {
	        var nickname = $("#floatingNickname").val();
	        var password = $("#floatingPassword").val();
	        var password2 = $("#floatingPassword2").val();

	        if (nickname !== "" && password !== "" && password2 !== "") {
	            return true;
	        }
	        return false;
	    }

	    // checkInputFields() 함수의 리턴값에 따라 회원가입버튼(register)을 활성화/비활성화
	    function toggleRegistrationButton() {
	        var allFieldsFilled = checkInputFields();
	        if (allFieldsFilled) {
	            $("#register").prop("disabled", false);
	        } else {
	            $("#register").prop("disabled", true);
	        }
	    }

	    // keyup 으로    Event handlers for keyup events on the input fields
	    $("#floatingNickname, #floatingPassword, #floatingPassword2").on("keyup", function() {
	        toggleRegistrationButton();
	    });

	    // Event handler for the registration button click
	    $("#register").on("click", function() {
	        if (checkInputFields()) {
	            // Submit the form or perform any desired action
	            $("#createAccount").submit();
	        }
	    });
	});
}

</script>
</html>