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

<div style="overflow-y: scroll; height: 600px; width: 1400px; margin: 0 auto;">
	<table border = "1" class="table-primary">
		<tr>
			<th>채팅번호</th>
			<th>채팅방번호</th>
			<th>게시글번호</th>
			<th>판매자번호</th>
			<th>보낸사람 닉</th>
			<th>보낸날짜</th>
			<th width="200">메세지</th>
			<th>받는사람 닉</th>
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
	</table>
</div>
	
	<a href="chatCreateTest">채팅 생성 테스트</a>
	&nbsp;<a href="../chatroom/chatRoomCreateTest">채팅방 생성 테스트</a>
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