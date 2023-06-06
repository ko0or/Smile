<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="../common/librarys.jsp"%>
</head>
<body>
<%@ include file="../common/navbar.jsp"%>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/notice/list.css'</style>
<section>
<!-- -------------------------------------------------------------------------- -->

<!-- 글 쓰기 버튼 ( ★ 운영자 권한 가진 사람만 볼 수 있게 해야함 ) -->
<div class="write" onclick="location.href='write'">+ (운영자만 볼 수 있도록 변경해야함 ★)</div>


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
		<tr> 
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>

		<!-- 실제 데이터 넣을땐 아래 내용 다 없애버리기 -->
		<tr>
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>
		<tr>
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>
		<tr>
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>
		<tr>
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>
		<tr>
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>
		<tr>
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>
		<tr>
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>
		<tr>
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>
		<tr>
			<td><a href="read">개발중입니다 ..</a></td>
			<td>2023-06-01 (예시)</td>
			<td>1 (예시)</td>
			<td>0 (예시)</th>
		</tr>
		
	</tbody>
</table>



<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    
    <!-- 이전 버튼 -->
    <li class="page-item disabled">
      <a class="page-link"><i class="fa-solid fa-circle-left" style="padding: 4px;"></i></a>
    </li>
    
    <!-- 페이징 [5개] -->
    <li class="page-item disabled"><a class="page-link" href="#">1</a></li>
    <li class="page-item"><a class="page-link" href="#">2</a></li>
    <li class="page-item"><a class="page-link" href="#">3</a></li>
    <li class="page-item"><a class="page-link" href="#">4</a></li>
    <li class="page-item"><a class="page-link" href="#">5</a></li>
    
    <!-- 다음 버튼 -->
    <li class="page-item">
      <a class="page-link" href="#"><i class="fa-solid fa-circle-right" style="padding: 4px;"></i></a>
    </li>
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