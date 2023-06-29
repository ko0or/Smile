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
<section style="display: flex; justify-content: center;">
<!-- -------------------------------------------------------------------------- -->

<div class="info-wrapper" style="width: 520px;">
	
	<!-- 회원정보 수정 배너부분 -->
	<div class="logo" style="text-align: center;">
		<h1><i class="fa-solid fa-user-pen"></i>
		회원 정보 수정</h1>
		<p>회원정보 수정 화면입니다! </p>
	</div>
		
	<!-- 아디, 비밀번호 수정 화면 -->
	<div class="form-wrapper">
		<form id="modifyForm" action="modify" method="POST">
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
			
			<div class="my-pwd-input">
				<div class="form-floating mb-3">
					<input name="password" type="password" class="form-control" id="floatingPassword_origin" placeholder="Password"> 
					<label for="floatingPassword"><i>*</i>
					현재 비밀번호</label>				
				</div>
				<button id="passwordChange" type="button" class="btn btn-primary"><i class="fa-solid fa-user-lock"></i> 
					변경하기</button>
			</div>
			
			<div class="new-password-input-area" style="display: none;">
				<input type="hidden" name="pwdChanged" value="false">
				<!-- 변경하기 버튼 눌렀을때만 보이는 영역 -->			
				<div class="form-floating mb-3">
					<input name="newPassword" type="password" class="form-control" id="floatingPassword" placeholder="Password"> 
					<label for="floatingPassword"><i>*</i>
					새 비밀번호</label>				
				</div>
				
				<div class="form-floating mb-3">
					<input name="newPassword2" type="password" class="form-control" id="floatingPassword2" placeholder="Password">
					<label for="floatingPassword2"><i>*</i>
					새 비밀번호 확인</label>				
				</div>
			</div>		

		</form>
	</div>

	<div class="modify-footer">
		<br><hr><p>입력하신 내용으로 저장하시겠습니까? </p>
	
		<div class="btns">
			<button id="modifyInfo" type="button" class="btn btn-primary"><i class="fa-solid fa-user-check"></i> 
			저장하기</button>
			<button id="goBack" type="button" class="btn btn-warning"><i class="fa-solid fa-rotate-left"></i> 
			뒤로가기</button>
		</div>
		
		<br><br>
		<h1 style="color: grey;">OR</h1>
		<a href="#" id="unregister">회원탈퇴 (클릭)</a>
	
	</div>
</div>

<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<script>
$(document).ready(function() {
	
// ====================== 비밀번호 변경 버튼 눌렀을 때 처리 ===========================================
	$("#passwordChange").click(function(){
		const target = $(".new-password-input-area");
		if ( target.css("display") == "none" ) { 
			$("input[name='pwdChanged']").val("true");
			$(this).html('<i class="fa-solid fa-user-lock"></i> 변경취소');
			target.slideDown();
		} else {
			$("input[name='pwdChanged']").val("false");
			$(this).html('<i class="fa-solid fa-user-lock"></i> 변경하기');
			target.slideUp();
		}
	})
	
// ====================== 회원정보 수정 버튼 눌렀을 때 처리 ===========================================
    $("#modifyInfo").click(function(){

		var formData = $("#modifyForm").serialize();
		
		$.ajax({
			type: "POST"
		   ,data: formData
		   ,url: "modify"
		   ,success: function(data, status){

			   Swal.fire({
				    icon: 'success',
				    title: '수정 성공',
				    text: "회원정보 수정을 완료했습니다!",
				    showCancelButton: false,
				    confirmButtonText: '확인'
				})
			   .then(function(){
			   		location.href = "/smile/user/info";
			   })
		   }
		   ,error : function(){
			   //alert("비밀번호가 일치하지 않습니다!");
			   
			   Swal.fire({
				    icon: 'warning',
				    title: '변경 실패',
				    text: "비밀번호를 다시 확인해주세요",
				    showCancelButton: false,
				    confirmButtonText: '확인'
				})
		   }
		});
    });

// ====================== 뒤로가기 버튼 눌렀을 때 처리 ===========================================
	$("#goBack").click(function(){
		history.back();
	});

// ====================== 회원탈퇴 링크 클릭했을 때 처리 ===========================================
	$("#unregister").click(function(){
		Swal.fire({
			icon: 'question' ,
			title: '회원탈퇴' ,
			text: "회원 탈퇴하시겠습니까?" ,
			confirmButtonText: '탈퇴' ,
			showCancelButton: false ,
			showDenyButton: true,
			denyButtonText: '취소'
		// 탈퇴 버튼 클릭하면 탈퇴 처리
		}).then((result) => {
			if(result.isConfirmed) {
				var formData = $("#modifyForm").serialize();
				console.log("@# ====> " + formData);
				
				$.ajax({
					type: "POST"
				   ,data: {"id":"${ user.id }"}
				   ,url: "unregister"
				   ,success: function(data){
					   console.log("success!!!!!")
					   location.href = "/smile/main/list";		// 컨트롤러의 URL 로 이동
				   }
				  , error: function(){
					  console.log("error!!!!!")
					  location.href = "login";
				  } 
				})				
			} else if(result.isDenied) {
				Swal.fire('회원탈퇴를 취소하였습니다.', '', 'info')
			}
		})
	});


	// ★ 입력된 비밀번호와  재확인에 입력된 내용이 서로 같은지 확인하는 내용들 ==================== >>
	const newPassword1 = $("input[name='newPassword']");
	const newPassword2 = $("input[name='newPassword2']");
	
	$(newPassword1).on("keyup" ,function() {
		if ($(newPassword1).val().length > 0 ) {
			if ($(this).val() == newPassword2.val() ) {
				$(newPassword1).css("backgroundColor", "white");
				$(newPassword1).next().html("<i>*</i> 새 비밀번호");
				$(newPassword2).css("backgroundColor", "white");
				$(newPassword2).next().html("<i>*</i> 새 비밀번호 확인");
			} else {
				$(newPassword1).css("backgroundColor", "yellow");
				$(newPassword1).next().html('<i class="fa-solid fa-triangle-exclamation" style="color: #6f8ab8;"></i> 새 비밀번호 확인란에 입력된 내용과 다릅니다.');
			}
		}
	})
	
	$(newPassword2).on("keyup" ,function() {
		if ($(newPassword2).val().length > 0 ) {
			if ($(this).val() == newPassword1.val() ) {
				$(newPassword1).css("backgroundColor", "white");
				$(newPassword1).next().html("<i>*</i> 새 비밀번호");
				$(newPassword2).css("backgroundColor", "white");
				$(newPassword2).next().html("<i>*</i> 새 비밀번호 확인");
			} else {
				$(newPassword2).css("backgroundColor", "yellow");
				$(newPassword2).next().html('<i class="fa-solid fa-triangle-exclamation" style="color: #6f8ab8;"></i> 새 비밀번호란에 입력된 내용과 다릅니다.');
			}
		}
	})
	

	
})// ~~ end
</script>
</html>