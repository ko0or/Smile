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
<script>
var show = ${show};
</script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=149a80c17154419aa57d2cfae9d6a80d&libraries=services"></script>
<script src="../resources/js/api/kakaoMap.js"></script>
<script src="../resources/js/trade/list.js"></script>
</html>