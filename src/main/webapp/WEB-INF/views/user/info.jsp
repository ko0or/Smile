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
	
	<!-- 아디, 비밀번호 표시 화면 -->
	<div class="form-wrapper">
	
		<!-- 프로필 사진 표시 -->
		<div class="form-control" align="center">
			<c:choose>
				<c:when test="${ user.imgPath != null }">
					<img id="imgReplace" alt="경로 이상" src="display?fileName=${ user.imgPath }" width="200" height="200">
				</c:when>
			</c:choose>
			
			<c:choose>
				<c:when test="${ user.imgPath == null }">
					<img alt="경로 이상" src="../resources/imgs/watch.jpg" width="200" height="200">
				</c:when>
			</c:choose>
		</div>
		<br>
		
		<!-- 프로필 사진 업로드 -->
		<div class="mb-3">
    		<input name="imgPath" type="file" class="form-control" aria-label="file example" required accept="image/*">
	  		<button id="writeBtn" type="button" class="btn btn-primary" style="padding: 20px;"><i class="fa-solid fa-check"></i>
			프로필 사진 업로드</button>
  		</div>
	
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
<script>
	var user_id = "${ user.id }"
	var user_identity = ${ user.identity }
</script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="../resources/js/api/easyPayment.js"></script>
<script src="../resources/js/user/info.js"></script>
<script>
$(document).ready(function() {

	
	
	
	// 프로필 사진 업로드 버튼 틀릭 시 수행
	$("#writeBtn").click(function(){
		var formData = new FormData();
		var inputFile = $("input[name='imgPath']");
		var files = inputFile[0].files;
		console.log("---------------")
		console.log(files);
		console.log("---------------")
	
		for (var i = 0; i < files.length; i++) {
			formData.append("uploadFile", files[i]);
		}
		
		$.ajax({
			url: "uploadProfile"
		   ,processData: false
		   ,contentType: false
		   ,data: formData
		   ,type: "POST"
		   ,success: function(result){
			   console.log("uploaded");
			   console.log(result);
			   
			   if(result!=null){
				   console.log("if 문까지 왔음");
				   $("#imgReplace").attr("src", "display?fileName=" +result);
			   }
			   
		   }//success
		})//ajax
	}); // click
	
});	//ready</script>

</html>