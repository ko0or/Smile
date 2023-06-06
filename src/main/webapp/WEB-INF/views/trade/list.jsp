<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/trade/list.css'</style>
<section>
<!-- -------------------------------------------------------------------------- -->
<div class="write" onclick="location.href='write'">+</div>


<!-- 랜더링 될 부분 -->
<div class="main-content">

	<!--  AJAX 응답 값으로 해당 부분을 랜더링 해야함 ★ -->

	<div class="content-wrapper item1">		
		<div class="content-left">	 <!-- 제이쿼리 .css() 메소드로 해당 부분에 사진넣어주기 --></div>		
		<div class="content-right">
			<div class="title">
				<h1>
					<span class="badge bg-secondary">만나요</span>
					<p>물건팔아요 ~ (예시)</p>
				</h1>
				
				<!--  만약 거래방식이 "만나요" 라면  location (주소) 를 화면에 보여줘야함 ★ -->
				<div class="location">
					<p><i class="fa-solid fa-map-location-dot"></i> 
					부산 해운대구 중동2로 11</p></div>						
			</div>
			<div class="price"><h2>500,000,000원</h2></div>
			<div class="date"><p>2072-08-01일 작성</p></div>
		</div>
	</div>

	<div class="content-wrapper item2">		
		<div class="content-left">	 <!-- 제이쿼리 .css() 메소드로 해당 부분에 사진넣어주기 --></div>		
		<div class="content-right">
			<div class="title">
				<h1>
					<span class="badge bg-primary">비대면</span>
					<p>물건팔아요 ~~~제목이 길면~ (예시)</p>
				</h1>
			</div>
			<div class="price"><h2>500,000,000원</h2></div>
			<div class="date"><p>2072-08-01일 작성</p></div>			
		</div>
	</div>


	
	
	
	
</div>
<!-- -------------------------------------------------------------------------- -->
</section>
<!-- <%@ include file="../common/footer.jsp" %>  -->
</body>
<!-- 
================== 카카오 맵 API 사용  appkey  =============================================== >>

	★ AWS 배포시 => c2d1ab04a5b02c1ca16e392b9c82fd66
	
											or
	
	☆ 테스트할때 =>   149a80c17154419aa57d2cfae9d6a80d

<< ============================================================================================= 
-->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=149a80c17154419aa57d2cfae9d6a80d&libraries=services"></script>
<script src="../resources/js/api/kakaoMap.js"></script>
<script>
$(document).ready(function() {
    
	// ★ 이런식으로 사진 넣어주는게 필요
	$(".item1 > .content-left").css("background-image", "url('https://cdn.pixabay.com/photo/2014/09/24/14/29/macbook-459196_1280.jpg')")
	$(".item2 > .content-left").css("background-image", "url('https://cdn.pixabay.com/photo/2016/03/27/19/43/samsung-1283938_1280.jpg')")
	
	
	$(".content-wrapper").click(function() {
		
		const tradeTitle = $(this).find('.title > h1 > p').text(); 
		const tradePrice = $(this).find('.price').text();
		const tradeText = $(this).find('.badge').text();
		var tradeType; 
		var tradeLocation; 
		
		if ( tradeText == "만나요"  ) { 
			tradeType = "<span class='badge bg-secondary'>만나요</span>"
			tradeLocation = $(this).find(".location").text();
			
		} else {
			tradeType = "<span class='badge bg-primary'>비대면</span>";
		}
											
												
		
		Swal.fire({
			  title: '상세 정보',
			  showConfirmButton: false ,
			  width: 900 ,
			  html: `
			  <div class="trade-info-wrapper">
			  		<!-- feed-comment-wrappe 클래스 안에 랜더링해주기 -->			  
			  		
			  		<div class="trade-header">
			  				<h1>` + tradeTitle  + `</h1>
			  				거래유형 : ` + tradeType + ` <br>
			  				판매가격 : ` + tradePrice + ` <br>
			  				<hr><br>
			  		</div>
			  		
			  		<div class="trade-location">
			  			<h4 style="display: inline-block;"><i class="fa-solid fa-map-location-dot mb-3"> 
			  			거래장소</i></h4> 
			  			` +  tradeLocation + `<br>
			  			<div id="map" style="width: 100%; height: 300px;"></div>			  			
			  			<hr><br>
		  			</div>
			  		
			  		
			  		<div class="trade-info">
					
			  		
			  		
					</div>
					
			  		<div class="contact-btn"><i class="fa-regular fa-message"></i>
			  		문의하기</div>
			  		
			  		<!-- 랜더링 공간 종료 -->
			</div>
			  `
			})		
		if ( tradeText == "만나요" ) { getMap(tradeLocation); 
		} else { 
			$(".trade-location").remove();
		}
	}) // ~ 버튼 이벤트 종료
	
	

})// ~~ end
</script>
</html>