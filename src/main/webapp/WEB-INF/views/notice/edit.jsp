<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<!-- <style>@import 'resources/css/main.css'</style>  -->
<section>
<h1>공지사항 수정</h1>

<form action="modify" method="POST">
<!-- 	hidden으로 PK값을 가져옴(수정할 게시 글 번호) -->
	<input name="identity" type="hidden" value="${identity}">
<%-- 	${board.title} => 컨트롤러 단에서 model에 board 담았던 아이의 title 근데 그 board가 service단에서 params 에서 담았던 값들임  --%>
	<input type="text" name="title"  class="form-control" placeholder="제목을 입력해주세요 " value="${board.title}"><br>
<%-- 	${board.content} => 컨트롤러 단에서 model에 board 담았던 아이의 content 근데 그 board가 service단에서 params 에서 담았던 값들임  --%>
	<textarea name="content" id="summernote">${board.content}</textarea><br>
	
	<button id="writeOk" class="btn btn-primary" type="button">
	작성 완료</button>
	<button onclick="history.back()" class="btn btn-primary" type="button">
	작성 취소</button>
</form>

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