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
		<!-- 프로필 사진 업로드 -->
		<div style="display: none">
    		<input name="imgPath"  id="fileInputTag" type="file" class="form-control" aria-label="file example" required accept="image/*">
	  		<button id="writeBtn" type="button" class="btn btn-primary" style="padding: 20px;"><i class="fa-solid fa-check"></i>
			프로필 사진 업로드</button>
  		</div>
  		
<div class="info-wrapper">
	
	<!-- 아디, 비밀번호 표시 화면 -->
	<div class="form-wrapper">
	
		<!-- 프로필 사진 표시 -->
		<div class="profile-wrapper ">
			<div class="profile-img "></div>
			<div class="profile-info ">
				<h1>${user.nickname}</h1>
				<p>${user.id}</p>
				<label for="fileInputTag" class="btn btn-primary"><i class="fa-solid fa-camera"></i> 
				프로필 사진 변경</label>
				<button id="modifyInfo" class="btn btn-primary"><i class="fa-solid fa-user-pen"></i> 
				내 정보 변경</button>
				<button id="logOut" class="btn btn-primary"><i class="fa-solid fa-right-from-bracket"></i> 
				로그아웃</button>
			</div>
		</div>
		
		
		
		<div class="btn-group-board">
		<br><hr><p id="floatingPoint"><i class="fa-solid fa-circle-dollar-to-slot"></i> 
		거래 관련 ( <b>보유 포인트 ${user.point}원</b> ) </p>

			<button id="pointUp" class="mb-3">
			포인트 충전</button><br>
			<button class="goChattingRoom mb-5">
			대화방 보기</button><br>
					
		
		<br><hr><p><i class="fa-regular fa-keyboard"></i> 
		내가 작성한</p>
		
			<button class="goMyFeeds mb-3">
			내가 작성한 피드 보기</button><br>
			<button class="goMyTrades mb-5">
			내가 판매중인 물건들</button><br>
		
		<br><hr><p><i class="fa-regular fa-heart"></i>
		관심/좋아요</p>
		
			<button class="goMyLikeFeeds mb-3">
			내가 좋아요 누른 피드만 보기</button><br>
			<button class="goMyLikeTrades mb-3">
			내가 관심있는 물건들만 보기</button><br>
		</div>


</div>

<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<script>
	var imgPath = "${ user.imgPath }";
	var user_id = "${ user.id }"
	var user_identity = ${ user.identity }; // 숫자는 "" 안붙여도 잘 작동되서 생략
	var user_nickname = "${ user.nickname }";
</script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="../resources/js/api/easyPayment.js"></script>
<script src="../resources/js/user/info.js"></script>
</html>