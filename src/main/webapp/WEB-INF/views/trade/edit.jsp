<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<!-- 다음 맵 API  (주소찾기) -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="../resources/js/api/daumPostCode.js"></script>
<!-- 
================== 카카오 맵 API 사용  appkey  =============================================== >>

	★ AWS 배포시 => c2d1ab04a5b02c1ca16e392b9c82fd66
	
											or
	
	☆ 테스트할때 =>   149a80c17154419aa57d2cfae9d6a80d

<< ============================================================================================= 
-->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=149a80c17154419aa57d2cfae9d6a80d&libraries=services"></script>
<script src="../resources/js/api/kakaoMap.js"></script>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/trade/write.css'</style>
<section>
<h1>수정하기</h1>

<form id="tradeForm" action="modify" method="POST" class="was-validated" enctype="multipart/form-data">	
	<input name="identity" value="${board.identity}" type="hidden">
	<input name="user" value="${userIdentity}" type="hidden">
	
  	<button id="${board.imgPath}" class="btn btn-primary showImg">기존 등록된 사진 보기</button>
  	<label for="imgPath" class="btn btn-primary">등록된 사진 변경하기</label>  	
   	<input id="imgPath" value="${board.imgPath}" style="display: none" 
   		name="imgPath" type="file" class="form-control" aria-label="file example" required accept="image/*">
  	
	

	<input value="${board.title}" type="text" name="title" class="form-control" placeholder="제목을 입력해주세요 " required style="margin-top: 50px"><br>
	<textarea name="content" id="summernote">${board.content}</textarea><br>
	<input type="text" value="${board.price}" name="price"  class="form-control" placeholder="가격을 숫자로 입력해주세요(단위: 원) " required><br>
	<br><br><br><br><br><br>
	

	
	
	<div id="then">
			<!-- ★ 대면 결제여부 선택 -->
			<h1>(선택사항) 구매자와 만나실 장소를 선택해주세요</h1>
			<p>원치 않으실경우 비대면결제로 진행하실 수 있습니다</p>
			<sub><i>* 비대면 결제시 싱글페이 포인트가 충전되며, 충전된 포인트는 현금으로 입금 요청하실 수 있습니다.</i></sub>
			<div class="form-check form-switch mt-5">
		  		<input name="contacted" class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
		  		<label class="form-check-label" for="flexSwitchCheckDefault">비대면 결제여부</label>
			</div>
			
			
			<!--  대면결제일경우, 주소 선택  -->
			<div class="contected">
					<button onclick="execDaumPostcode()" class="btn btn-primary mt-3" type="button">
		  			주소 선택하기</button>
			
					<div id="map" class="mt-2" >
							<input type="text" id="address" name="address" placeholder="선택된 주소가 없습니다." required readonly>
					</div>
			</div>
			
			<div style="width: 100%; height: 500px; display: flex; justify-content: center; align-items: center;" >
			
				<button id="writeBtn" type="button" class="btn btn-primary" style="padding: 20px;"><i class="fa-solid fa-check"></i>
				물품 등록하기</button>
			
			</div>
	</div>
</form>

<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
<script>
$(document).ready(function() {
	
	getMap("${board.address}");
	$("#address").val("${board.address}");
	
	var contacted = "${board.contacted}";
	if ( contacted === "만나요" ) {
		
		$(".contected").slideDown(500);
		
	} else {
		
		$(".contected").slideUp(500);
		$("#flexSwitchCheckDefault").click();
		
	}
	
})
</script>
<script src="../resources/js/common/tradeImg.js"></script>
<script src="../resources/js/trade/edit.js"></script>
</body>
</html>