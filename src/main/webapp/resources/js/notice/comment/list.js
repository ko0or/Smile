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
						
						//로그인한 유저 :userIdentity
						//댓글작성자 번호 :data[i].user(data[i] =>댓글들 user => 작성자)
						alert( "현재 표시중인 댓글DTO의 Identity 값: " + data[i].identity + ",댓글 작성자 번호는 => " + data[i].user + ",지금 로긴한 유저 번호는 => " + userIdentity );
						
						//지금 로그인한 유저와 댓글 작성자가 같냐
						if ( userIdentity == data[i].user ) {
							alert("본인이라서 수정/삭제 가능해야함");
						//그럼 row에 그 댓글에 수정/삭제를 보여줘라
							row += getComponent(data[i], "block");
						} else {
							alert("본인아니라서 수정/삭제 불가");
						//아니면 없에라
							row += getComponent(data[i], "none");
						}
				
						
						
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





					
						// 댓글 수정버튼 눌렀을때 작동할 내용
					$(".comment-modify").click(function() {
					//내 자신".comment-modify"text가 indexOf(인덱스)-1보다 큰것이 수정이냐
					if($(this).text().indexOf("수정")>-1){
					
					//addClass("form-control"):아웃라인을 만들어라
					//.comment =>댓글
					$("#commentIdentity" + $(this).attr("id")).find("textarea").addClass("form-control");
					
					 //prop("readonly", false) :읽기 전용을 없에라
					$("#commentIdentity" + $(this).attr("id")).find("textarea").prop("readonly", false);
                    
                    $("#commentIdentity" + $(this).attr("id")).find("textarea").css({"border" : "1px solid #ccc" , "outline" : "initial", "cursor" : "text"});

					//내 자신의 text(타입)에 저장을 만들어라
					$(this).text("저장");
					
					//그 다음의 text(타입)에 취소를 만들어라
                     $(this).next().text("취소");
                     
                     //내 자신".comment-edit"text가 indexOf(인덱스)-1보다 큰것이 삭제이냐
					} else if ( $(this).text().indexOf("저장") > -1 ) {
				
						var identity = $(this).attr("id");
						
						
						$.ajax({
						
							url : "comment/edit?identity="+identity ,
							method : "POST" ,
							data :{
							"identity" : $(this).attr("id") ,
							//commentIdentity 가지고 있는 자신.수정버튼이 가지고 있는 id(한마디로 pk값)+" 자식요소가textarea"인=>그래서 앞에 한칸을 띄움 값을 content에 넣어라.
							"content"  : $("#commentIdentity" + $(this).attr("id")).find("textarea").val()
							}
							,success : function() {
								callComments();
							}
						}) // ~ ajax 끝 !
						}
					}) // ~ 댓글 수정버튼 이벤트 끝 !
					


					
					
					$(".comment-delete").click(function() {

						// 댓글 삭제버튼 눌렀을때 작동할 내용 attr("id") => id의 속성 값을 가지고 오는 거임
						var identity = $(this).attr("id");
						if($(this).text().indexOf("취소")>-1){
						
								callComments();
						}else{ $.ajax({

						//url이 delete인이유는 컨트롤러에 있는 매칭되는 delete를 실행시키는거임 하지만 ajax이기때문에 눈에 보이진 않는 거임
							url : "comment/delete?identity="+identity ,
							method : "GET" ,
							
							success : function() {

								callComments();

										// 댓글 삭제에 성공했으면, 변경된 내용을 다시 받아와서 화면에 표시함
							}
						}) // ~ ajax 끝 !
						}
												
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
//	show=>위에서 받은 display가 block 또는 none임
		function getComponent( data, show ) {
			
			console.log(data);
			
			return `
			
			<div id="commentIdentity${data.identity}" class="comment-wrapper mt-5">
			
			<div class="comment">
				<div class="comment-header">
					<h4 style="display: inline;">${data.nickname}</h4>
					<sub style="display: inline; color: grey;">${data.created}</sub>
				</div>
				
				<textarea
					id = "${data.identity}" 
					class = "modify-textarea"  
					readonly 
					style="margin: 20px 0; border:none; outline:none; display: block; cursor: default; width: 100%" >${data.content}</textarea>

					<!--display를 조건문에 썻기 때문에 그 조건에 맞게 보여주고 숨김-->
				<div class="comment-footer" style="display:${show}">					
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