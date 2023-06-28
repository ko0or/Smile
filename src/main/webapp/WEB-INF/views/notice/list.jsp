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
<!-- role이 컨트롤러 단에서 조건문안에 model에 담았던 아이가 admin(관리자)이면 -->
<c:if test="${role == 'admin'}">
<!--  +모양을 보여주고 그걸 클릭하면 write로 가라 -->
	<div class="write" onclick="location.href='write'">+ </div>
</c:if>

<div style="text-align: right; margin-bottom: 30px;">
	<form action="list"> 
		<input name="pageNum" value="1" type="hidden">
		<input name="searchKeyword"  value="${searchKeyword}" class="form-control" type="text" placeholder="제목 또는 내용으로 검색 가능합니다" aria-label="default input example" style="width: 300px; display: inline; margin-right: 10px;">
		<button type="submit" class="btn btn-primary">검색</button>
		
		
	</form>
</div>

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
<!-- 	컨트롤러단에서 ArrayList로 받았던 아이 그래서 그만큼 반복하기 위해 forEach문에 담고 var="dto"=>dto를 사용해서 담겟다 -->
	<c:forEach items="${list}" var="dto">
		<tr> 
<!-- 	${dto.title}을 누르면	${dto.identity} => identity의 값을 가지고 read를 이동 -->
			<td><a class="move_link" href="read?identity=${dto.identity}">${dto.title}</a></td>
			<td>${dto.created}</td>
			<td>${dto.view}</td>
 			<td>${dto.comments}</td> 
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
	    	<li class="page-item">
             	<a class="page-link" href="list?pageNum=${pageMaker.startPage - 1}&searchKeyword=${searchKeyword}">
      				<i class="fa-solid fa-circle-left" style="padding: 4px;"></i>
    			</a>          
            </li>
         </c:if>    
    
    <!-- 페이징 [5개] -->
    <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
    
    <li class="page-item" ${pageMaker.cri.pageNum == num ? "style='border-bottom: 2px solid blue;'" :""}>
    <a class="page-link" href="list?pageNum=${num}&searchKeyword=${searchKeyword}">
    ${num}
    </a></li>
	</c:forEach>
    
    
    
    <!-- 다음 버튼 -->
   <c:if test="${pageMaker.next}">
    <li class="page-item">
     <a class="page-link" href="list?pageNum=${pageMaker.endPage + 1}&searchKeyword=${searchKeyword}">
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

   window.onpageshow = function(event){
	      if(event.persisted || (window.performance && window.performance.navigation.type==2)){
	         location.reload(); //새로고침
	      }
	   }
	   
})// ~~ end
</script>
</html>