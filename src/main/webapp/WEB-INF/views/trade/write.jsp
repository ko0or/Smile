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
<h1>중고거래 등록하기</h1>

<form action="#" method="POST" class="was-validated">
	
	<!-- 제목및 내용 -->
  <div class="mb-3">
    	<input type="file" class="form-control" aria-label="file example" required accept="image/*">
    	<div class="invalid-feedback">미리보기용 사진을 선택해주세요</div>
  	</div>

	<input type="text" name="title"  class="form-control" placeholder="제목을 입력해주세요 " required><br>
	<textarea name="content" id="summernote"></textarea><br>
	<input type="text" name="price"  class="form-control" placeholder="가격을 숫자로 입력해주세요(단위: 원) " required><br>
	<br><br><br><br><br><br>
	
	<!-- 본인인증하기 -->
	<h1>연락처 등록</h1>
	<p>구매자와 연락 가능한 번호를 입력해주세요 (최초 1회 본인인증 필요)</p>
	
	<div class="input-tel mb-3">
		<div class="left form-floating">
  			<input type="tel" class="form-control" id="floatingInput" required>
  			<label for="floatingInput">휴대폰 번호 입력</label>
  		</div>  		
  		<div class="right">
  			<button class="btn btn-primary" type="button">
  			확인</button>
  		</div>
	</div>
	<br><br><br><br><br><br>
	
	
	<!-- ★ 대면 결제여부 선택 -->
	<h1>(선택사항) 구매자와 만나실 장소를 선택해주세요</h1>
	<p>원치 않으실경우 비대면결제로 진행하실 수 있습니다</p>
	<sub><i>* 비대면 결제시 싱글페이 포인트가 충전되며, 충전된 포인트는 현금으로 입금 요청하실 수 있습니다.</i></sub>
	<div class="form-check form-switch mt-5">
  		<input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
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
</form>

<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<script>
$(document).ready(function() {
	
	// 카카오맵 API 불러오기
	getMap("부산광역시 연제구 중앙대로 1001");
	
	// 비대면 결제여부 스위칭 (분기처리: 주소선택하기 화면 슬라이딩)	
	$("#flexSwitchCheckDefault").change(function() {
		  if ($(this).is(":checked")) {
		    $(".contected").slideUp(500);
		  } else {
		    $(".contected").slideDown(500);
		  }
	});
	
	
	// ★ 물품 등록하기 버튼 눌렀을때 ( 가장 하단에 있는 작성 버튼 )
	$("#writeBtn").click(function() {
		
		// 만약 비대면 결제여부가 off 이면서,  어디서 만날지 안정했다면 ?
		if ($("#flexSwitchCheckDefault").prop("checked") == false && $("#address").val().length == 0) {
			alert("대면 거래를 위해 만나실 주소가 선택되지않았습니다.");
		}
		
	}) // ~ 버튼 이벤트 끝 !
	

})// ~~ end
</script>
</html>