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

<form id="tradeForm" action="write" method="POST" class="was-validated" enctype="multipart/form-data">
	
	<!-- 제목및 내용 -->
  <div class="mb-3">
    	<input name="imgPath" type="file" class="form-control" aria-label="file example" required accept="image/*">
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
  			<input name="tel" type="tel" class="form-control" id="floatingInput" required>
  			<label for="floatingInput">휴대폰 번호 입력</label>
  		</div>  		
  		<div class="right">
  			<button id="authentication" class="btn btn-primary" type="button">
  			확인</button>
  		</div>
	</div>
	<br><br><br><br><br><br>
	
	<div id="then" style="display: none;">
			<!-- ★ 대면 결제여부 선택 -->
			<h1>(선택사항) 구매자와 만나실 장소를 선택해주세요</h1>
			<p>원치 않으실경우 비대면결제로 진행하실 수 있습니다</p>
			<sub><i>거래는 싱글페이 포인트로 진행되며, 보유한 포인트는 현금으로 입금 요청하실 수 있습니다.</i></sub>
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
</body>
<script>
$(document).ready(function() {
	
	function smsCheck_step1() {
		
		Swal.fire({
			  icon : 'info',
			  title: '휴대폰 인증',
			  text : '인증되지 않은 휴대폰 번호입니다.',
			  showCancelButton: true,
			  confirmButtonText: '문자 인증 하기',
			  cancelButtonText : '취소',
			}).then((result) => {

			  if (result.isConfirmed) {
				//=> 휴대폰 문자 인증 요청을 클릭했다면 ( 다음 단계로 .. )
				smsCheck_step2();		  
			  }
			  
		})
	}	
	
	function smsCheck_step2() {
		
		$.ajax({
			url : "telUpdate",
			data : { 
				"telAuthentication" : "yes",
				"tel" : $("input[name='tel']").val()
			},
			method : "POST",
			success : function () {

				Swal.fire({
					  icon : 'info',
					  title: '휴대폰 인증',
					  text : '발송된 인증번호를 입력해주세요',
					  input: 'text',
					  showCancelButton: true,
					  confirmButtonText: '확인',
					  cancelButtonText : '취소',
					}).then((result) => {
						
						  if (result.isConfirmed) {
							  //=> 인증번호를 입력하고, 클릭을 했다면 ( 다음 단계로 .. )
							  smsCheck_step3(result.value);
						  }
						  
				}) // ~ swal 모달				
			} // ~ (ajax, success 콜백 함수)
			
		}) // ~ ajax
	} // ~function
	
	
	
	function smsCheck_step3(inputValue) {
		
		$.ajax({
			url : "telCheck",
			method : "GET",
			data : { "tel" : inputValue },
			success : function ( data ) {

				if ( data == true ) {
				// 문자 발송한뒤 변경된 user 테이블에 tel 컬럼 값과   입력 받은 값이 일치하다면
					 
					//=> 입력된 휴대폰 번호를 DB에 저장함 ( data에 `telAuthentication` 이 없기 때문에 받은 값 그대로 UPDATE 진행)
					$.ajax({
						url : "telUpdate",
						data : { "tel" : $("input[name='tel']").val() },
						method : "POST",
						success : function() {
							Swal.fire({
								  icon : 'success',
								  title: '휴대폰 인증',
								  text : '인증에 성공 했습니다!',
								  confirmButtonText: '확인',
							})
							$("#then").slideDown(500);
							getMap("부산광역시 연제구 중앙대로 1001");
						} // ~~ success
					}) // ~~ ajax
						
				} else {
					Swal.fire({
						  icon : 'error',
						  title: '휴대폰 인증',
						  text : '인증에 실패 했습니다',
						  confirmButtonText: '확인',
					})					
				}					
			} // ~ (ajax, success 콜백 함수)
			
		}) // ~ ajax		  
	} // ~ function
	
	
	

	
	
	// 비대면 결제여부 스위칭 (분기처리: 주소선택하기 화면 슬라이딩)	  ========================================================== >>
	$("#flexSwitchCheckDefault").change(function() {
		  if ($(this).is(":checked")) {
		    $(".contected").slideUp(500);
		  } else {
		    $(".contected").slideDown(500);
		  }
	});
	
	
	
	// ★ 휴대폰 인증하기 버튼 눌렀을때 ( 중간에 있는 버튼 ) ========================================================== >>
	$("#authentication").click(function() {
		
		$.ajax({
			url : "telCheck",
			method : "GET",
			data : { "tel" : $("input[name='tel']").val() },
			success : function( data ) {
				
				if ( data == true ) {
					Swal.fire({
						  title : '인증된 회원',
						  icon : 'success',
						  html : `본인인증에 성공했습니다 !`,
						  confirmButtonText : '확인',
						})
				  
					$("#then").slideDown(500);
					getMap("부산광역시 연제구 중앙대로 1001");
				} else { // ~ (data==true)
				
					smsCheck_step1();
					
				}
				
				
			} // ~ (ajax, success)		
		}) // ~ ajax
		
		
		

	}) // ~ 버튼 이벤트 끝 !
	
	
	
	// ★ 물품 등록하기 버튼 눌렀을때 ( 가장 하단에 있는 작성 버튼 )  ========================================================== >>
	$("#writeBtn").click(function() {
		
		// 만약 비대면 결제여부가 off 이면서,  어디서 만날지 안정했다면 ?
		if ($("#flexSwitchCheckDefault").prop("checked") == false && $("#address").val().length == 0) {
			
			Swal.fire({
				  title : '등록 실패',
				  icon : 'warning',
				  html : `거래하실 위치를 선택해주시거나<br>
				  혹은 비대면 결제로 변경해주세요.`,
				  confirmButtonText : '확인',
				})
			
		} else {
			
			$("#tradeForm").submit();
			
		}
		
	}) // ~ 버튼 이벤트 끝 !
	

	

	
})// ~~ end
</script>
</html>