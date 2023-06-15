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

	<div class="content-wrapper">	
		<div class="content-header">
			<div class="profileImageIcon"></div>
			<p><b>(데이터 바인딩 필요★) 유저 닉네임 </b></p>
			<p><font color="grey">(데이터 바인딩 필요★) 게시글 작성일자</font> </p>
			
			<!-- 만약 본인이 작성한 게시글이라면 표시 -->
			<div class="btn-group" >
			  <button type="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa-solid fa-gear"></i></button>
			  <ul class="dropdown-menu">
					<li><a class="dropdown-item" href="#">수정</a></li>
					<li><a class="dropdown-item" href="#">삭제</a></li>
			  </ul>
			</div>
		</div>
		
		<div class="content-body">(데이터 바인딩 필요★) 게시글 본문내용</div>
		<div class="content-footer">
			<button class="like"><i class="fa-regular fa-heart"></i>
			좋아요</button>
			<button class="comment"><i class="fa-regular fa-comment"></i>
			댓글달기</button>
		</div>
	</div>
	
<!-- @@@ -->	
	
	<div class="content-wrapper">	
		<div class="content-header">
			<div class="profileImageIcon"></div>
			<p><b>(데이터 바인딩 필요★) 유저 닉네임 </b></p>
			<p><font color="grey">(데이터 바인딩 필요★) 게시글 작성일자</font> </p>
			
			<!-- 만약 본인이 작성한 게시글이라면 표시 -->
			<div class="btn-group" >
			  <button type="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa-solid fa-gear"></i></button>
			  <ul class="dropdown-menu">
					<li><a class="dropdown-item" href="#">수정</a></li>
					<li><a class="dropdown-item" href="#">삭제</a></li>
			  </ul>
			</div>
		</div>
		
		<div class="content-body">(데이터 바인딩 필요★) 게시글 본문내용</div>
		<div class="content-footer">
			<button class="like"><i class="fa-regular fa-heart"></i>
			좋아요</button>
			<button class="comment"><i class="fa-regular fa-comment"></i>
			댓글달기</button>
		</div>
	</div>
	
<!-- @@@ -->	

	<div class="content-wrapper">	
		<div class="content-header">
			<div class="profileImageIcon"></div>
			<p><b>(데이터 바인딩 필요★) 유저 닉네임 </b></p>
			<p><font color="grey">(데이터 바인딩 필요★) 게시글 작성일자</font> </p>
			
			<!-- 만약 본인이 작성한 게시글이라면 표시 -->
			<div class="btn-group" >
			  <button type="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa-solid fa-gear"></i></button>
			  <ul class="dropdown-menu">
					<li><a class="dropdown-item" href="#">수정</a></li>
					<li><a class="dropdown-item" href="#">삭제</a></li>
			  </ul>
			</div>
		</div>
		
		<div class="content-body">(데이터 바인딩 필요★) 게시글 본문내용</div>
		<div class="content-footer">
			<button class="like"><i class="fa-regular fa-heart"></i>
			좋아요</button>
			<button class="comment"><i class="fa-regular fa-comment"></i>
			댓글달기</button>
		</div>
	</div>
	
<!-- @@@ -->	

	<div class="content-wrapper">	
		<div class="content-header">
			<div class="profileImageIcon"></div>
			<p><b>(데이터 바인딩 필요★) 유저 닉네임 </b></p>
			<p><font color="grey">(데이터 바인딩 필요★) 게시글 작성일자</font> </p>
			
			<!-- 만약 본인이 작성한 게시글이라면 표시 -->
			<div class="btn-group" >
			  <button type="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa-solid fa-gear"></i></button>
			  <ul class="dropdown-menu">
					<li><a class="dropdown-item" href="#">수정</a></li>
					<li><a class="dropdown-item" href="#">삭제</a></li>
			  </ul>
			</div>
		</div>
		
		<div class="content-body">(데이터 바인딩 필요★) 게시글 본문내용</div>
		<div class="content-footer">
			<button class="like"><i class="fa-regular fa-heart"></i>
			좋아요</button>
			<button class="comment"><i class="fa-regular fa-comment"></i>
			댓글달기</button>
		</div>
	</div>
	

	
	
	
<!-- 랜더링 될 부분 (★종료) -->
</div>

<!-- -------------------------------------------------------------------------- -->
</section>
<!-- <%@ include file="../common/footer.jsp" %> -->
</body>
<script>
$(document).ready(function() {

	
	// 테스트용으로 작성
	$(".profileImageIcon").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");
	
	
	
	
    
	// ★ 버튼 이벤트 [좋아요]
	$(".like").click(function() {
		if ( $(this).children('i').hasClass("fa-regular") ) {
			$(this).children('i').removeClass("fa-regular").addClass("fa-solid")
		} else {
			$(this).children('i').removeClass("fa-solid").addClass("fa-regular")
		}
	})
	
	
// ★ 버튼 이벤트 [ 댓글 목록]
	$(".comment").click(function() {

		/*
		
		>>>        AJAX 통신하는 스크립트를 추가해야함 ★         <<<

		#. 예시코드 ex
		
		$.ajax({
			url : "api/comment/read"
			, type : "GET"
			
			, success: function(컨트롤러 리턴값 받는 변수이름) { 
				매개변수로 받은 값 + 제이쿼리 append 혹은 html 메소드 사용해서 화면에 표시해주기  
			}
		})
		

		
		

*/
		
		Swal.fire({
			  title: '댓글 2개',
			  showConfirmButton: false ,
			  width: 900 ,
			  html: `
			  <div class="feed-comment-wrapper" style="min-height: 70vh; position: relative; padding:10px; overflow-x: hidden;">
				
			  
			  <!-- feed-comment-wrapper 클래스 안에 랜더링해주기 -->			  
			  <div class="feed-comments" style="margin-bottom: 40px; text-align:left;">
						
						<!-- 댓글 내용 -->
						<div class="profileImageIcon"></div>
						<h4 style="display: inline">(데이터 바인딩 필요★) 작성자 이름</h4>
						<sub style="color:grey">(데이터 바인딩 필요★)2023.01.13</sub>	
						<div style="margin-top: 20px; white-space: pre;">댓글 내용이 여기에 표시</div>
						
						<!-- 작성자 본인일때만 표시 (수정, 삭제) -->
						<button style="border:none;background:white;color:grey;">
						수정</button>
						<button style="border:none;background:white;color:grey;">
						삭제</button>
					</div>
				
			  
			  <!-- feed-comment-wrapper 클래스 안에 랜더링해주기 -->			  
			  <div class="feed-comments" style="margin-bottom: 40px; text-align:left;">
						
						<!-- 댓글 내용 -->
						<div class="profileImageIcon"></div>
						<h4 style="display: inline">(데이터 바인딩 필요★) 작성자 이름</h4>
						<sub style="color:grey">(데이터 바인딩 필요★)2023.01.13</sub>	
						<div style="margin-top: 20px; white-space: pre;">댓글 내용이 여기에 표시</div>
						
						<!-- 작성자 본인일때만 표시 (수정, 삭제) -->
						<button style="border:none;background:white;color:grey;">
						수정</button>
						<button style="border:none;background:white;color:grey;">
						삭제</button>
					</div>
					

					

					
					<div style="display: flex; justify-content: space-between; position:absolute; bottom: 10px; width: 100%;">
						<div class="form-floating" style="flex: 3;">
							<textarea placeholder="댓글작성란" class="form-control" id="InputComment" style="max-height: 200px;"></textarea>
							<label for="InputComment" class="form-label">댓글 작성</label>
						</div>
				  		<button class="btn btn-primary" style="flex: 1; margin: 0 10px"><i class="fa-solid fa-check"></i>
				  		</button>
				  	</div>
			</div>
			  `
			})		
			
		
		
	    autosize($('textarea'));				
		$("#InputComment").on("input keydwon keyup", function() {			
			if ( $("#InputComment").height() > 160 ) {
				$(".feed-comment-wrapper .form-label").hide();
			} else {
				$(".feed-comment-wrapper .form-label").show();
			}
		})		
		
		

		
		
		$(".feed-comments > .profileImageIcon").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");
		
		
		
	}) //~~ 댓글 보기 버튼 끝
	
	
	
	
})// ~~ end
</script>
</html>