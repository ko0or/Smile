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
<h1>Chatting Room LIST TEST</h1>
	<table width = "500" border = "1">
		<form id="frm" method="post" action="#">
			<tr>
				<td>채팅방 번호</td>
				<td>게시글 번호</td>
				<td>판매자 번호</td>
				<td>구매자 번호</td>
				<td>삭제</td>
			</tr>
			
			<c:forEach items="${list}" var = "dto">
				<tr>
<%-- 					<td>${dto.identity}</td> --%>
					<td><a href="../chat/chatContentTest?board=${dto.board}&buyer=${dto.buyer}" style="background-color: aqua;">${dto.identity}</a></td>
					<td>${dto.board}</td>
					<td>${dto.seller}</td>
					<td>${dto.buyer}</td>
					<input type="hidden" name="board" value="${dto.board}">
					<input type="hidden" name="seller" value="${dto.seller}">
					<td><input type="button" onclick="fn_submit()" value="삭제"></td>
				</tr>
			</c:forEach>
		</form>
	</table>
	<a href="chatRoomCreateTest">채팅방 생성 테스트</a>
	&nbsp;<a href="../chat/chatCreateTest">채팅 생성 테스트</a>
<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../../common/footer.jsp" %>
</body>
<script>
$(document).ready(function() {
	autosize($('textarea'));
})// ~~ end
</script>
	<script type="text/javascript">
			function fn_submit() {
				var formData = $("#frm").serialize();
				
				$.ajax({
					type:"post"
					,data:formData
					,url:"delete"
					,success: function(data) {
						location.href="chatRoomListTest";
					}
					,error: function() {
						alert("오류발생");
					}
				});
			}
	</script>
</html>