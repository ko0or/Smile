<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/main.css'</style>
<section style="max-height: 100vh">

<!-- 글 쓰기 버튼 -->
<div class="write" onclick="location.href='write'">+</div>

<!-- 랜더링 될 부분 (★시작) -->
<div class="main-content">

	<!--  AJAX 응답 값으로 해당 부분을 랜더링 해야함 ★ -->

	
	
	
<!-- 랜더링 될 부분 (★종료) -->
</div>

<!-- -------------------------------------------------------------------------- -->
</section>
<!-- <%@ include file="../common/footer.jsp" %> -->
</body>
<script>

// UserDto dto = devUtils.getUserInfo(session);
// model.addAttribute("userIdentity", dto.getIdentity()  );
var userIdentity = ${userIdentity};
var searchByNickname = ${searchByNickname};
var searchByBoardIdentity = ${searchByBoardIdentity};
var imgPath = "${ user.imgPath }";
</script>
<script src="../resources/js/feed/list.js"></script>
</html>