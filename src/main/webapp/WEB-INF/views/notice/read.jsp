<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="../common/librarys.jsp"%>
</head>
<body>
<%@ include file="../common/navbar.jsp"%>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/notice/read.css'</style>
<section>
<!-- -------------------------------------------------------------------------- -->

	<div class="content-wrapper">	
		<div class="content-header">
			<div class="user-icon"></div>
			<p><b>${board.title}</b></p>
			<p><font color="grey">
			${board.created}</font></p>
			
			<c:if test="${role == 'admin'}">
			<!-- 운영자일때만 표시 -->
			<div class="btn-group" style="position: absolute; bottom: 20px; right: 10px; height: 40px; background-color: white;">
			  <button type="button" style="border: none; background-color: white;" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa-solid fa-gear"></i></button>
			  <ul class="dropdown-menu" style="padding: 10px;">
					<li><a class="dropdown-item" href="edit?identity=${board.identity}">수정</a></li>
					<li><a class="dropdown-item" href="delete?identity=${board.identity}">삭제</a></li>
			  </ul>
			</div>
			</c:if>
		</div>
		
		<div class="content-body">${board.content}</div><hr>
		
		
		<!-- 댓글영역 ★ -->
		<div class="content-footer">

				
				<!--  ajax 랜더링되는 공간 -->
			
			 			
		</div>
	</div>


<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp"%>
</body>
<script>
var boardIdentity = ${board.identity};

</script>
<script src="../resources/js/notice/comment/list.js"></script>
</html>