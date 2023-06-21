$(document).ready(function() {
// ======================================================================================================================= >

// callComments 함수에
		function callComments() {
		
			$.ajax({
				
				//read에 identityurl을 값을 가지고 와서 보여줌 = boardIdentity( jsp파일에서 만들었던 변수)
				//url이 read인 이유는 댓글을 달아도 그자리에 있어서
				url : "comment/read?identity="+boardIdentity ,
				
				method : "GET" ,
				success : function( data ) {
					
					console.log( "댓글갯수 => " + data.length );			
					var row = `<h2>댓글 ${data.length}개</h2>`;
									
					
					for (var i=0; i < data.length; i++) {
				
						//row에 밑에서 썻던 getComponent 함수에 data의 값을 하나씩 추가해서 넣어 준다 
						row += getComponent(data[i]);
				
						//$(".content-footer").append( getComponent(data[i]) );
						
					}
	
					
					
					// 밑에 댓글 작성란 row에 넣어준다 왜 냐하면 row에 data의 값을 하나씩 넣어 주기 때문에 댓글도 하나씩 넣어 줘야 한다.
					row += `
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
				
					//html==append랑 같은건데 밑에 getComponent 함수의 모든 div값을 넣은 것 이다
					$(".content-footer").html( row );





					
					$(".comment-edit").click(function() {
				
						// 댓글 수정버튼 눌렀을때 작동할 내용
						var identity = $(this).attr("id");
						alert("댓글수정 ajax URL 예시, comment/edit?identity=" + identity );
						
						
						$.ajax({
						
							url : "comment/edit?identity="+identity ,
							method : "GET" ,
							success : function() {

								// alert("수정성공, 그리고 callComments() 함수 호출 안했을때");
								alert("수정성공, 그리고 callComments() 함수 사용하면?");
								callComments();
							}
						}) // ~ ajax 끝 !
					}) // ~ 댓글 수정버튼 이벤트 끝 !
					


					
					
					$(".comment-delete").click(function() {

						// 댓글 삭제버튼 눌렀을때 작동할 내용 attr("id") => id의 속성 값을 가지고 오는 거임
						var identity = $(this).attr("id");
						alert("댓글삭제 ajax URL 예시, comment/delete?identity=" + identity );
						
						$.ajax({

						//url이 delete인이유는 컨트롤러에 있는 매칭되는 delete를 실행시키는거임 하지만 ajax이기때문에 눈에 보이진 않는 거임
							url : "comment/delete?identity="+identity ,
							method : "GET" ,
							
							success : function() {

								// alert("삭제성공, 그리고 callComments() 함수 호출 안했을때");
								alert("삭제성공, 그리고 callComments() 함수 사용하면?");

										// 댓글 삭제에 성공했으면, 변경된 내용을 다시 받아와서 화면에 표시함
								callComments();
							}
						}) // ~ ajax 끝 !
												
					}) // ~ 댓글 삭제버튼 이벤트 끝 !





					// ★ 댓글 작성버튼 눌렀을때 (AJAX)
					$("#commentWrtieBtn").click(function (){
						
								$.ajax({

									//url이 write인이유는 컨트롤러에 있는 매칭되는 write를 실행시키는거임 하지만 ajax이기때문에 눈에 보이진 않는 거임
									url : "comment/write?board=" + boardIdentity
									, type : "POST"
									
									//val은 input타입에 입력되어 있는 값을 사용할때(뭐라 적혀있는 지 알고 싶을 때)
									, data : { "content" : $("#InputComment").val() }
									
									, success: function() {

										// 댓글 작성에 성공했으면, 변경된 내용을 다시 받아와서 화면에 표시함
										callComments();
									}
								})
								
						
					}) // ~ 댓글 작성버튼 이벤트 끝 !





					// ★ 댓글입력화면 높이조절
					autosize($("textarea"));		
					$("textarea").on("input keydwon keyup", function() {			
						if ( $("#InputComment").height() > 160 ) {
							$(".form-floating .form-label").hide();
						} else {
							$(".form-floating .form-label").show();
						}
					}) // ~ 댓글 입력화면 자동 높이조절 끝 !
					
				}
			})		
		
		}
		
		
// ======================================================================================================================= >		

//	댓글 =>getComponent 함수에 data 매게변수를 넣어줌

		function getComponent( data ) {
			
			console.log(data);
			
			return `
			
			<div class="comment-wrapper mt-5">
			
			<div class="comment">
				<div class="comment-header">
					<h4 style="display: inline;">${data.nickname}</h4>
					<sub style="display: inline; color: grey;">${data.created}</sub>
				</div>
				
				<p style="white-space: pre-line;">${data.content}</p>

					
				<div class="comment-footer">					
					<button type="button" id="${data.identity}" class="comment-modify">수정</button>
					<button type="button" id="${data.identity}" class="comment-delete">삭제</button>
				</div>
			</div>
		</div>
			
			`;
		}	
		


// ======================================================================================================================= >

	// ★ : 페이지 로딩되면 최초 1회 댓글목록 불러오기 ( 이후 댓글 작성버튼 눌러서 추가된 이후에도 다시 호출해서 변경내용 보여줘야함 )
	callComments();

})// ~~ 자바스크립트 끝남