<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<!-- <style>@import 'resources/css/main.css'</style>  -->
<section>
<h1>Chatting Room Create TEST</h1>

<table width="500" border="1">
		<form id="frm" method="post" action="chattingroom">
			<tr>
				<td>게시글번호</td>
				<td>
					<input type="number" name="board" size="10">
				</td>
			</tr>
			<tr>
				<td>판매자번호</td>
				<td>
					<input type="number" name="seller" size="10">
				</td>
			</tr>
			<tr>
				<td>구매자번호</td>
				<td>
					<input type="number" name="buyer" size="10">
				</td>
			</tr>
			<tr>
				<td colspan="2">
<!-- 					<input type="submit" value="입력"> -->
					<input type="button" onclick="fn_submit()" value="입력">
					&nbsp;&nbsp;
					<a href="chatRoomListTest">목록보기</a>
				</td>
			</tr>
		</form>
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
	<script type="text/javascript">
			function fn_submit() {
				var formData = $("#frm").serialize();
				
				$.ajax({
					type:"post"
					,data:formData
					,url:"write"
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