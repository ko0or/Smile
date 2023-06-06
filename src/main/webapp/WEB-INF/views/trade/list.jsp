<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/main.css'</style>
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
					물건팔아요 ~ (예시)
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
					물건팔아요 ~~~제목이 길면~ (예시)
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
<script>
$(document).ready(function() {
    
	$(".item1 > .content-left").css("background-image", "url('https://cdn.pixabay.com/photo/2014/09/24/14/29/macbook-459196_1280.jpg')")
	$(".item2 > .content-left").css("background-image", "url('https://cdn.pixabay.com/photo/2016/03/27/19/43/samsung-1283938_1280.jpg')")

})// ~~ end
</script>
<style>

.content-wrapper {
	padding: 20px;
	display: flex;
	justify-content: space-around;
	transition: all 0.3s;	
}
.content-wrapper > .content-left {  
	flex: 5;
	border-radius: 20px;	
	height: 350px;
	overflow: hidden;
	
	justify-content: center;
	align-items: center;

	background-position: center;
}
.content-wrapper::before {
  cursor: pointer;
  content: "더보기"; /* 가상 요소에 표시될 텍스트 추가 */
  display: none; /* 초기에는 가상 요소를 숨김 */
  position: absolute; /* 가상 요소의 위치를 조정하기 위해 절대 위치로 설정 */
  top: 50%; /* 가상 요소를 수직 중앙으로 위치시킴 */
  left: 50%; /* 가상 요소를 수평 중앙으로 위치시킴 */
  transform: translate(-50%, -50%); /* 가상 요소를 정중앙으로 정렬 */
  background: rgba(0, 0, 0, 0.15);
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  
  width: 100%;
  height: 100%;
}

.content-wrapper:hover::before {
  display: flex; /* 가상 요소를 활성화할 때 보이도록 변경 */
  justify-content: center;
  align-items: center;
}

.content-wrapper > .content-right { 
	border-left: 1px solid #eee;
	margin-left: 20px;
	padding-left: 20px;
	flex: 7;	
}

.content-wrapper > .content-right > .title {
	
}
.content-wrapper > .content-right > .price {
}
.content-wrapper > .content-right > .date {
	color: grey;
}



</style>
</html>