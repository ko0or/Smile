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
					<td>제목</td>
					<td>판매자 닉네임</td>
				</tr>
				
				<c:forEach items="${list}" var = "dto">
					<c:if test="${dto.buyer == user.identity and dto.userId != user.identity}">
						<tr>
							<td><a href="../chat/chatContent?board=${dto.board}&buyer=${dto.buyer}" style="background-color: aqua;">${dto.title}</a></td>
							<td>${dto.nickname}</td>
						</tr>
					</c:if>
				</c:forEach>
		</table>
	</div>
	<div style="width: 450px; float: right;">
		<h3 align="center">판매품 문의 목록</h3>
		<table class="table table-striped">
				<tr>
					<td>제목</td>
					<td>구매자 닉네임</td>
				</tr>
				
				<c:forEach items="${list}" var = "dto">
					<c:if test="${dto.seller == user.identity and dto.userId != user.identity}">
						<tr>
							<td><a href="../chat/chatContent?board=${dto.board}&buyer=${dto.buyer}" style="background-color: aqua;">${dto.title}</a></td>
							<td>${dto.nickname}</td>
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
<script>
$(document).ready(function() {
	autosize($('textarea'));
})// ~~ end
</script>
</html>