<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="../common/librarys.jsp"%>
<style type="text/css">
      .div_page ul{
         display: flex;
         list-style: none;
      }
   </style>
</head>
<body>
<%@ include file="../common/navbar.jsp"%>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/notice/list.css'</style>
<section>
<!-- -------------------------------------------------------------------------- -->

<!-- 글 쓰기 버튼 ( ★ 운영자 권한 가진 사람만 볼 수 있게 해야함 ) -->
<c:if test="${role == 'admin'}">
	<div class="write" onclick="location.href='write'">+ (운영자만 볼 수 있도록 변경해야함 ★)</div>
</c:if>

<table class="table table-responsive table-borderless">
	<thead>
		<tr>
			<th style="width: 60%">공지사항 제목</th>
			<th style="width: 20%">작성일시</th>
			<th style="width: 10%">조회</th>
			<th style="width: 10%">댓글</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${list}" var="dto">
		<tr> 
			<td><a class="move_link" href="read?identity=${dto.identity}">${dto.title}</a></td>
			<td>${dto.created}</td>
			<td>${dto.view}</td>
			<td>${commnetCount}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>



<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
     <div class="div_page">
     
      <ul>
<!-- 이전 버튼 -->
         <c:if test="${pageMaker.prev}">
	    	<li class="page-item disabled">
             	<a class="page-link" href="${pageMaker.startPage - 1}">
      				<i class="fa-solid fa-circle-left" style="padding: 4px;"></i>
    			</a>          
            </li>
         </c:if>    
    
    <!-- 페이징 [5개] -->
    <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
    
    <li class="page-item" ${pageMaker.cri.pageNum == num ? "style='border-bottom: 2px solid blue;'" :""}>
    <a class="page-link" href=" ${num}">
    ${num}
    </a></li>
	</c:forEach>
    
    
    
    <!-- 다음 버튼 -->
   <c:if test="${pageMaker.next}">
    <li class="page-item">
     <a class="page-link" href="${pageMaker.endPage + 1}">
     <i class="fa-solid fa-circle-right" style="padding: 4px;"></i></a>
         </li>
         </c:if> 
  </ul>
</nav>
<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp"%>
</body>
<script>
	$(document).ready(function() {

	})// ~~ end
</script>
</html>