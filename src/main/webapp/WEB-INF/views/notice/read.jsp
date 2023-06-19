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
			
			<!-- 운영자일때만 표시 -->
			<c:if test="${role == 'admin'}">
			<div class="btn-group" style="position: absolute; bottom: 20px; right: 10px; height: 40px; background-color: white;">
			  <button type="button" style="border: none; background-color: white;" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa-solid fa-gear"></i></button>
			  <ul class="dropdown-menu" style="padding: 10px;">
					<li><a class="dropdown-item" href="edit?identity=${read.identity}">수정</a></li>
					<li><a class="dropdown-item" href="delete?identity=${read.identity}">삭제</a></li>
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
$(document).ready(function() {
		
		// ★ 댓글입력화면 높이조절
		autosize($("textarea"));		
		$("textarea").on("input keydwon keyup", function() {			
			if ( $("#InputComment").height() > 160 ) {
				$(".form-floating .form-label").hide();
			} else {
				$(".form-floating .form-label").show();
			}
		})
		
		
		$.ajax({
			url : "read?identity=" + ${comment.identity} 
			method : "GET"
			success : function( data ) {
				
				console.log( "댓글갯수 => " + data.length );			
				$(".content-footer").append("<h2>댓글 " + data.length + "개</h2>");
								
				
				for (var i=0; i < data.length; i++) {
					<div class="comment-wrapper mt-5">
					
					<div class="comment">
						<div class="comment-header">

							<h4 style="display: inline;">${data[i].user}</h4>
							<sub style="display: inline; color: grey;">${data[i].created}</sub>
						</div>
// 					var nickname = ${data.user}
						
						<p>${data[i].content}</p>
						
						<!-- 댓글 작성자 본인일때만 보이게 -->
						if (${data[i].user} == true ) {
							
						<div class="comment-footer">					
							<button type="button" href="edit?identity=${identity}">수정</button>
							<button type="button" href="delete?identity=${identity}">삭제</button>
						</div>
						}
					</div>
				</div>
				}
				
				
				// 밑에 댓글 작성란
				var writeArea = `
				<div style="display: flex; justify-content: space-between; width: 100%; margin-top: 50px;">
					<div class="form-floating" style="flex: 11;">
						<textarea placeholder="댓글작성란" class="form-control" id="InputComment" style="max-height: 200px;"></textarea>
						<label for="InputComment" class="form-label">댓글 작성</label>
					</div>
					
					<!-- 댓글 작성버튼 -->
					  <button id="commentWrtieBtn" class="btn btn-primary" style="flex: 1; margin: 0 10px"><i class="fa-solid fa-check"></i>
					  </button>
				  </div>
				`;
				
				$(".content-footer").append( writeArea );
				
				
			}
		})
		
		
		
		
		
		
		// ★ 댓글 작성버튼 눌렀을때 (AJAX)
		$("#commentWrtieBtn").click(function (){
			
				/* =========== 여기에 AJAX 내용 넣어줘야함 (예시코드) =================
				
					$.ajax({
						url : "api/comment/write"
						, type : "POST"
						, data : $("InputComment").val()
						
						, success: function(data) { 
							data.append()
							매개변수로 받은 값 + 제이쿼리 append 혹은 html 메소드 사용해서 화면에 표시해주기  
						}
					})
					

					
				*/			
			
		})
	
	
})// ~~ 자바스크립트 끝남
</script>
</html>