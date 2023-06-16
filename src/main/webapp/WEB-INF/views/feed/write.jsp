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
<h1>새로운 피드 작성</h1>

<form action="write" method="POST">

	<textarea name="content" id="summernote" rows="30"></textarea><br>
	
	<button id="writeFeed" class="btn btn-primary" type="button">
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