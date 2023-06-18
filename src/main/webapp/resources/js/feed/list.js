$(document).ready(function() {

// ☆ 게시글 하나씩 그려주는 메소드  => function getComponent(nickname, date, content, id)
// ☆ 그려진 게시글에 클릭 이벤트 등록해주는 메소드 => function setEvent() {
// ☆ setEvent() 안에서  댓글목록 버튼 이벤트가 있음

// ========================================================================================================== >>

$.ajax({

	url : "getPosts" ,
	method : "GET" ,
	
	success : function( data ) {	
	
		console.log("조회갯수 => " + data.length);
	
		for( var i =0; i<data.length; i++ ) {
			$(".main-content").append( 
				getComponentByBoard( data[i].identity , data[i].nickname, data[i].created, data[i].content, data[i].user) 
			);			
		}
		
		
		// 테스트용으로 작성 (아이콘)
		$(".profileImageIcon").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");
		
		
		// 랜더링된 컴포넌트에 이벤트 등록
		setEvent();
	}

})


// ========================================================================================================== >>

function getComponentByBoard( identity , nickname, date, content, user) {
	
	var boardIdentity = identity;
	var show = userIdentity == user ? "block" : "none";

	return `
		<div class="content-wrapper">	
		<div class="content-header">
			<div class="profileImageIcon"></div>
			<p><b>${nickname} </b></p>
			<p><font color="grey">${date}</font> </p>
			

			<div class="btn-group" style="display : ${show}" >
			  <button type="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa-solid fa-gear"></i></button>
			  <ul class="dropdown-menu">
					<li><a class="dropdown-item" href="modify?identity=${identity}">수정</a></li>
					<li><a class="dropdown-item" href="delete?identity=${identity}">삭제</a></li>
			  </ul>
			</div>
		</div>
		

		<div class="content-body">${content}</div>
		<div class="content-footer">
			<button class="like"><i class="fa-regular fa-heart"></i>
			좋아요</button>
			<button id="${identity}" class="comment"><i class="fa-regular fa-comment"></i>
			댓글달기</button>
		</div>
	</div>
	`;	


}








function getComponentByComment( identity , nickname, date, content, user) {

return `

					  <!-- feed-comment-wrapper 클래스 안에 랜더링해주기 -->			  
					  <div class="feed-comments" style="margin-bottom: 70px; text-align:left;">
								
								<div class="profileImageIcon"></div>
								<h4 style="display: inline">${nickname}</h4>
								<sub style="color:grey">${date}</sub>	
								<div style="margin-top: 20px; white-space: pre;">${content}</div>
								
								<button id="${identity}" class="comment-edit" style="border:none;background:white;color:grey;">
								수정</button>
								<button id="${identity}" class="comment-delete" style="border:none;background:white;color:grey;">
								삭제</button>
							</div>
							

`

}
	
// ========================================================================================================== >>



// ★ 랜더링후 버튼 이벤트 등록하는 메소드
function setEvent() {


	// ★ 버튼 이벤트 [좋아요]
	$(".like").click(function() {
		if ( $(this).children('i').hasClass("fa-regular") ) {
			$(this).children('i').removeClass("fa-regular").addClass("fa-solid")
		} else {
			$(this).children('i').removeClass("fa-solid").addClass("fa-regular")
		}
	})  // ~~ 버튼 이벤트 종료 [좋아요]
	
	


	// ★ 버튼 이벤트 [ 댓글 목록]
		$(".comment").click(function() {
			
			Swal.fire({
				  title: ' ',
				  scrollbarPadding: true ,
				  showConfirmButton: false ,
				  width: 900 ,
				  html: `
				  <div class="feed-comment-wrapper" style="min-height: 70vh; padding:10px; overflow-x: hidden;">

								<!-- ★ ajax 로 랜더링되는 부분 ★ -->

				</div>
				  `
				})		
				
			
			var row = `기본값`;
			var dataLength = 0;
							
			$.ajax({
				url : "comment/getComments?boardIdentity=" + $(this).attr("id")
				, type : "GET"
				
				, success: function( data ) { 
					row = ``;
					dataLength = data.length;
					
					for ( var i=0; i < data.length; i ++ ) {						
						row += getComponentByComment( data[i].identity , data[i].nickname, data[i].created, data[i].content, data[i].user);
					}

					row += `						
						<div style="position: absolute; bottom: 0px; left: 0px; display: flex; justify-content: space-between; width: 100%;">
							<div class="form-floating" style="flex: 3;">
								<textarea placeholder="댓글작성란" class="form-control" id="InputComment" style="max-height: 200px;"></textarea>
								<label for="InputComment" class="form-label">댓글 작성</label>
							</div>
					  		<button class="btn btn-primary" style="flex: 1; margin: 0 10px"><i class="fa-solid fa-check"></i>
					  		</button>
					  	</div>						  	
					`;
					
					if ( dataLength == 0 ) {
						$("#swal2-title").html("등록된 댓글이 없습니다.");
					} else { 
						$("#swal2-title").html("댓글 " + dataLength + "개");
					}
					$(".feed-comment-wrapper").html(row);
					$(".feed-comments > .profileImageIcon").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");
					
				    autosize($('textarea'));
					$("#InputComment").on("input keydwon keyup", function() {			
						if ( $("#InputComment").height() > 160 ) {
							$(".feed-comment-wrapper .form-label").hide();
						} else {
							$(".feed-comment-wrapper .form-label").show();
						}
				    
				    				
					})		
				}
			}) 
				

		})
			
			
	}
})
	
	
	
