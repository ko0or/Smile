<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<!-- <style>@import 'resources/css/main.css'</style>  -->
<section>
<div>
	<div style="width: 450px; float: left;">
		<h3 align="center">구매품 문의 목록</h3>
		<table class="table table-striped">
				<tr>
					<td width="300" align="center">제목</td>
					<td width="200" align="center">판매자 닉네임</td>
					<td width="50"></td>
				</tr>
				
				<c:forEach items="${list}" var = "dto">
					<c:if test="${dto.buyer == user.identity and dto.userId != user.identity}">
						<tr>
							<td align="center"><a href="../chat/chatContent?board=${dto.board}&buyer=${dto.buyer}">${dto.title}</a></td>
							<td align="center">${dto.nickname}</td>
							<td><input type="button" id="${dto.board}" class="goBoard" value="게시글"/></td>
						</tr>
					</c:if>
				</c:forEach>
		</table>
	</div>
	<div style="width: 450px; float: right;">
		<h3 align="center">판매품 문의 목록</h3>
		<table class="table table-striped">
				<tr>
					<td width="300" align="center">제목</td>
					<td width="200" align="center">구매자 닉네임</td>
					<td width="50"></td>
				</tr>
				
				<c:forEach items="${list}" var = "dto">
					<c:if test="${dto.seller == user.identity and dto.userId != user.identity}">
						<tr>
							<td align="center"><a href="../chat/chatContent?board=${dto.board}&buyer=${dto.buyer}">${dto.title}</a></td>
							<td align="center">${dto.nickname}</td>
							<td><input type="button" id="${dto.board}" class="goBoard" value="게시글"/></td>
						</tr>
					</c:if>
				</c:forEach>
		</table>
	</div>
</div>

<!-- <div> -->
<!-- 	<table class="table table-striped"> -->
<!-- 			<tr> -->
<!-- 				<td>채팅방 번호</td> -->
<!-- 				<td>채팅방 제목</td> -->
<!-- 				<td>게시글 번호</td> -->
<!-- 				<td>판매자 번호</td> -->
<!-- 				<td>구매자 번호</td> -->
<!-- 				<td>유저 번호</td> -->
<!-- 				<td>유저 닉네임</td> -->
<!-- 				<td>세션 유저</td> -->
<!-- 			</tr> -->
			
<%-- 			<c:forEach items="${list}" var = "dto"> --%>
<!-- 				<tr> -->
<%-- 					<td>${dto.roomId}</td> --%>
<%-- 					<td><a href="../chat/chatContent?board=${dto.board}&buyer=${dto.buyer}" style="background-color: aqua;">${dto.title}</a></td> --%>
<%-- 					<td>${dto.board}</td> --%>
<%-- 					<td>${dto.seller}</td> --%>
<%-- 					<td>${dto.buyer}</td> --%>
<%-- 					<td>${dto.userId}</td> --%>
<%-- 					<td>${dto.nickname}</td> --%>
<%-- 					<td>${user.identity}</td> --%>
<!-- 				</tr> -->
<%-- 			</c:forEach> --%>
<!-- 	</table> -->
<!-- </div> -->
<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<!-- 
================== 카카오 맵 API 사용  appkey  =============================================== >>

	★ AWS 배포시 => c2d1ab04a5b02c1ca16e392b9c82fd66
	
											or
	
	☆ 테스트할때 =>   149a80c17154419aa57d2cfae9d6a80d

<< ============================================================================================= 
-->
<script>
	var loginUserIdentity = ${user.identity};
</script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=149a80c17154419aa57d2cfae9d6a80d&libraries=services"></script>
<script src="../resources/js/api/kakaoMap.js"></script>
<script src="../resources/js/trade/myChatRoomList.js"></script>
<style>@import '../resources/css/trade/list.css'</style>
</html>