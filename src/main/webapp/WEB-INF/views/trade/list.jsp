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


<!-- 
	<div class="content-wrapper item1">		
		<div class="content-left"></div>		
		<div class="content-right">
			<div class="title">
				<h1>
					<span class="badge bg-secondary">만나요</span>
					<p>물건팔아요 ~ (예시)</p>
				</h1>

				<div class="location">
					<p><i class="fa-solid fa-map-location-dot"></i> 
					부산 해운대구 중동2로 11</p></div>						
			</div>
			<div class="price"><h2>500,000,000원</h2></div>
			<div class="date"><p>2072-08-01일 작성</p></div>
		</div>
	</div>

	<div class="content-wrapper item2">		
		<div class="content-left"></div>		
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

	<div class="content-wrapper item3">		
		<div class="content-left"></div>		
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
 
 
 -->

	
	
	
	
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
<script src="../resources/js/trade/list.js"></script>
<script>
$(document).ready(function() {
    
	// ★ 이런식으로 사진 넣어주는게 필요
	//$(".item1 > .content-left").css("background-image", "url('https://cdn.pixabay.com/photo/2014/09/24/14/29/macbook-459196_1280.jpg')")
	//$(".item2 > .content-left").css("background-image", "url('https://cdn.pixabay.com/photo/2016/03/27/19/43/samsung-1283938_1280.jpg')")
	
	//$(".item3 > .content-left").css("background-image", "url('display?fileName=C:/upload/temp3/pic_5.jpg')")
	// $(".item3 > .content-left").css("background-image", "url('${dto.imgPath}')")
	
	
	

	
	

})// ~~ end
</script>
</html>