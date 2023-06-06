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
<h1>공지사항 작성</h1>

<form action="#" method="POST">
	<input type="text" name="title"  class="form-control" placeholder="제목을 입력해주세요 "><br>
	<textarea name="content" id="summernote"></textarea><br>
	
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