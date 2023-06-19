<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="../../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<!-- <style>@import 'resources/css/main.css'</style>  -->
<section>
<h1>Chatting LIST TEST</h1>

<table width = "1000" border = "1">
		<tr>
			<td>채팅번호</td>
			<td>채팅방번호</td>
			<td>게시글번호</td>
			<td>판매자번호</td>
			<td>보낸사람 닉</td>
			<td>보낸날짜</td>
			<td>메세지</td>
			<td>받는사람 닉</td>
		</tr>
		
		<c:forEach items="${list}" var = "dto">
			<tr>
				<td>${dto.identity}</td>
				<td>${dto.chattingroom}</td>
				<td>${dto.board}</td>
				<td>${dto.seller}</td>
				<td>${dto.sender}</td>
				<td>${dto.sendtime}</td>
				<td>${dto.msg}</td>
				<td>${dto.receiver}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5">
				<a href="chatCreateTest">채팅 생성 테스트</a>
				&nbsp;<a href="../chatroom/chatRoomCreateTest">채팅방 생성 테스트</a>
			</td>
		</tr>
	</table>
<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../../common/footer.jsp" %>
</body>
<script>
$(document).ready(function() {
	autosize($('textarea'));
})// ~~ end
</script>
</html>