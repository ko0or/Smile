$(document).ready(function() {

	callBoards();

	/* 
	
		#. 메소드 

		 .. callBoards()
		 .. callComments(게시글번호)
		 .. setEvent()

		 .. getComponentByBoard()
		 .. getComponentByComment()




	*/

// ========================================================================================================== >>

	function callBoards() {
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
	}


		
// ========================================================================================================== >>

	function callComments(boardIdentity) {

		//=> 화면에 띄워진 SweetAlert2 모달로  댓글내용 보여주기
		var row = `기본값`;
		var dataLength = 0;
						
		$.ajax({
			url : "comment/getComments?boardIdentity=" + boardIdentity ,
			type : "GET" ,
			
			success: function( data ) { 
				row = ``;
				dataLength = data.length;
				
				//=> 해당 게시글에 존재하는 댓글 갯수만큼 반복하면서, 댓글 내용들을 표시
				for ( var i=0; i < data.length; i ++ ) {						
					row += getComponentByComment( data[i].identity , data[i].nickname, data[i].created, data[i].content, data[i].user);
				}



				//=> 댓글 갯수만큼 모두 표시했다면? 마지막으로 댓글 작성란 보여주기
				row += `						
					<div style="position: absolute; bottom: 0px; left: 0px; display: flex; justify-content: space-between; width: 100%;">
						<div class="form-floating" style="flex: 3;">
							<textarea placeholder="댓글작성란" class="form-control" id="InputComment" style="max-height: 200px;"></textarea>
							<label for="InputComment" class="form-label">댓글 작성</label>
						</div>
						<button id="commentWriteBtn" class="btn btn-primary" style="flex: 1; margin: 0 10px"><i class="fa-solid fa-check"></i>
						</button>
					</div>						  	
				`;
				


				//=> 보여줄 댓글이 0개라면 등록된 댓글이 없다고 표시하고, 존재할경우엔 댓글이 몇 개인지 보여주기
				if ( dataLength == 0 ) {
					$("#swal2-title").html("등록된 댓글이 없습니다.");
				} else { 
					$("#swal2-title").html("댓글 " + dataLength + "개");
				}



				//=> 위에서 만들어진 내용을 SweetAlert2 모달화면에 보여주기 @
				$(".feed-comment-wrapper").html(row);
				$(".feed-comments > .profileImageIcon").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");
				


				//=> 댓글 입력란 이벤트 등록(자동 높이 조절)
				autosize($('textarea'));
				$("#InputComment").on("input keydwon keyup", function() {			
					if ( $("#InputComment").height() > 160 ) {
						$(".feed-comment-wrapper .form-label").hide();
					} else {
						$(".feed-comment-wrapper .form-label").show();
					}					
				}) // ~~ 댓글 입력란 자동 높이 조절 이벤트 끝 -!		



				//=> 댓글 작성버튼 이벤트 등록
				$("#commentWriteBtn").click(function() {
					
					$.ajax({
						url : "comment/write" ,
						method : "POST" , 
						data : { 
							"board" : boardIdentity ,
							"content" : $("#InputComment").val()
						} 
						, success : function() { callComments(boardIdentity); }
					})

				})




				//=> 댓글 수정버튼 이벤트 등록
				$(".comment-edit").click(function() {					
	
                    if ( $(this).text().indexOf("수정") > -1 ) {
						
                        //=> 상태: 댓글 수정
                        $(".feed-comments" + $(this).attr("id") + " textarea").addClass("form-control");
                        $(".feed-comments" + $(this).attr("id") + " textarea").prop("readonly", false);
                        $(".feed-comments" + $(this).attr("id") + " textarea").css({"border" : "1px solid #ccc" , "outline" : "initial", "cursor" : "text"})
                        $(this).text("저장");
                        $(this).next().text("취소");

                    } else if ( $(this).text().indexOf("저장") > -1 ) {

                        alert("수정내용 저장하는것도 처리해야징");

                    }

					 // ~~ 댓글 수정버튼의 글자가 '수정' or '저장' 에 따른 이벤트 처리 끝 -!
				}) // ~ 수정버튼 이벤트 종료 -!



				//=> 댓글 삭제버튼 이벤트 등록
				$(".comment-delete").click(function() {

                    if ( $(this).text().indexOf("취소") > -1 ) {
						
						callComments(boardIdentity);


                    } else {

                        $.ajax({
                            url : "comment/delete" ,
                            method : "GET" ,
                            data : { "identity" : $(this).attr("id")  } ,
                            success : function() { callComments(boardIdentity); }
                        })

                    }


				})







			}
		}) // ~~ ajax 끝 -!

	}


// ========================================================================================================== >>



	// ★ 랜더링후 버튼 이벤트 등록하는 메소드
	function setEvent() {


		// ☆ 버튼 이벤트 등록 [게시글 좋아요]
		$(".like").click(function() {
			if ( $(this).children('i').hasClass("fa-regular") ) {
				$(this).children('i').removeClass("fa-regular").addClass("fa-solid")
			} else {
				$(this).children('i').removeClass("fa-solid").addClass("fa-regular")
			}
		})  // ~~ 버튼 이벤트 종료 [좋아요]
		
		


		// ☆ 버튼 이벤트 등록 [댓글 목록]
		$(".comment").click(function() {
			

			//=> 댓글 목록을 SweetAlert2 모달 라이브러리로 보여주기
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
			}) // ~~ swal(모달) 화면 끝 !
				
			


			callComments( $(this).attr("id") );
				





		}) // ~~ 게시글 하단에 있는 "댓글 보기" 버튼 클릭 이벤트 마무리			
	} // ~~ 게시글의 하단에 있는 "댓글 보기"및  "좋아요" 버튼 클릭 이벤트 마무리 -!




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
						<div class="feed-comments${identity} feed-comments" style="margin-bottom: 70px; text-align:left;">
									
									<div class="profileImageIcon"></div>
									<h4 style="display: inline">${nickname}</h4>
									<sub style="color:grey">${date}</sub>	
									<textarea style="margin: 20px 0; border:none; outline:none; display: block; cursor: default;" class="" readOnly>${content}</textarea>
									
									<button id="${identity}" class="comment-edit" style="border:none;background:white;color:grey;">
									수정</button>
									<button id="${identity}" class="comment-delete" style="border:none;background:white;color:grey;">
									삭제</button>
								</div>
								

	`

	}



}) // 자바스크립트 끝 -!



