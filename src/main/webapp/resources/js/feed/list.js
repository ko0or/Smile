$(document).ready(function() {


	

	/* 
	
		#. 메소드 

		 .. callBoards()
		 .. callComments(게시글번호)
		 .. setEvent()

		 .. getComponentByBoard()
		 .. getComponentByComment()

		.. 스크롤 바 이벤트


	*/
	



// ========================================================================================================== >>

	function callBoards() {		
		
		$.ajax({

			url : "getPosts" ,
			method : "GET" ,
			data : { 
				"start" : start,
				"searchByNickname" : searchByNickname,
				"searchByBoardIdentity" : searchByBoardIdentity, 
				"searchByLikeUser" : searchByLikeUser 
			} ,
			success : function( data ) {	
			
			start += 5;
		
			for( var i =0; i<data.length; i++ ) {
				// 게시글 정보
				$(".main-content").append( 
					getComponentByBoard( data[i] )
					 
				);										
        
				// 프로필 이미지 (아이콘)
				var setProfileImg = getProfilePath(data[i].imgPath);   
				var setTargetClass = ".profileImg" + data[i].identity;
				getProfileImage(setProfileImg, setTargetClass);
			}
			
			

			
			
			// 랜더링된 컴포넌트에 이벤트 등록
			setEvent();
			boardUpdate = true;
			}
			
			
		}) // ~~ ajax 끝		
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
					row += getComponentByComment( data[i] );
					
				}



				//=> 댓글 갯수만큼 모두 표시했다면? 마지막으로 댓글 작성란 보여주기

				if ( userIdentity == -1 ) {
					row += `						
						<div style="position: absolute; bottom: 0px; left: 0px; display: flex; justify-content: space-between; width: 100%;">
							<div class="form-floating" style="flex: 3;">
								<textarea disabled placeholder="댓글작성란" class="form-control" id="InputComment" style="max-height: 200px;"></textarea>
								<label for="InputComment" class="form-label">로그인 후 이용 가능합니다</label>
							</div>
							<button disabled id="commentWriteBtn" class="btn btn-primary" style="flex: 1; margin: 0 10px"><i class="fa-solid fa-check"></i>
							</button>
						</div>						  	
					`;
				} else {
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

				}
				


				//=> 보여줄 댓글이 0개라면 등록된 댓글이 없다고 표시하고, 존재할경우엔 댓글이 몇 개인지 보여주기
				if ( dataLength == 0 ) {
					$("#swal2-title").html("등록된 댓글이 없습니다.");
				} else { 
					$("#swal2-title").html("댓글 " + dataLength + "개");
				}
                        

                        //=> 게시글 하단에 좋아요 n개 댓글 n개 의 내용 바꿔주기 위해 사용
                        var originalText = $(".content-wrapper" + boardIdentity + " sub").text();
                        var myCommentCount = parseInt( originalText.split("댓글 ")[1].split("개")[0] );	
                        var replacedText = originalText.replace(/댓글 [0-9]+개/g, "댓글 "+dataLength+"개");
                        $(".content-wrapper" + boardIdentity + " sub").text( replacedText );


				//=> 위에서 만들어진 내용을 SweetAlert2 모달화면에 보여주기 @
				$(".feed-comment-wrapper").html(row);


//================================================================================================================================================

				// 프로필 이미지 (아이콘)
				for ( var c=0; c < data.length; c++ ) {

					var setCommentProfileImg = getProfilePath(data[c].imgPath);   
					var setCommentTargetClass = ".comment_profileImg" + data[c].identity;
					getProfileImage(setCommentProfileImg, setCommentTargetClass);

				}

//================================================================================================================================================

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
				$("#commentWriteBtn").off("click");
				$("#commentWriteBtn").click(function() {
					
					$.ajax({
						url : "comment/write" ,
						method : "POST" , 
						data : { 
							"board" : boardIdentity ,
							"content" : $("#InputComment").val()
						} 
						, success : function() { 					
						      // 변경된 내용을 (댓글)을 화면에 표시
						      callComments(boardIdentity); 					
							
						}
					})

				})




				//=> 댓글 수정버튼 이벤트 등록
				$(".comment-edit").off("click");
				$(".comment-edit").click(function() {					
	
                    if ( $(this).text().indexOf("수정") > -1 ) {
						
                        //=> 상태: 댓글 수정
                        $(".feed-comments" + $(this).attr("id") + " textarea").addClass("form-control");
                        $(".feed-comments" + $(this).attr("id") + " textarea").prop("readonly", false);
                        $(".feed-comments" + $(this).attr("id") + " textarea").css({"border" : "1px solid #ccc" , "outline" : "initial", "cursor" : "text"})
                        $(this).text("저장");
                        $(this).next().text("취소");
                        $(this).next().next().css("display", "none");

                    } else if ( $(this).text().indexOf("저장") > -1 ) {

                        $.ajax({
                        
                        	url : "comment/modify" ,
                        	method : "POST" , 
                        	data : { 
								"identity" : $(this).attr("id") ,
								"content" : $(this).prev().val()
							}
                        , success : function() { callComments(boardIdentity);  }
                        })

                    }

					 // ~~ 댓글 수정버튼의 글자가 '수정' or '저장' 에 따른 이벤트 처리 끝 -!
				}) // ~ 수정버튼 이벤트 종료 -!



				//=> 댓글 삭제버튼 이벤트 등록
				$(".comment-delete").off("click");
				$(".comment-delete").click(function() {

                    if ( $(this).text().indexOf("취소") > -1 ) {
						
						callComments(boardIdentity);


                    } else {

                        $.ajax({
                            url : "comment/delete" ,
                            method : "GET" ,
                            data : { "identity" : $(this).attr("id")  } ,
                            success : function() { 								
                              // 변경된 내용을 (댓글)을 화면에 표시
                              callComments(boardIdentity); 					
                            	
                            }
                        })

                    }
				}) // ~ 댓글 삭제버튼 이벤트 끝





                
                //=> 답글 버튼 이벤트 등록 (답글 작성 폼 보여주기)
				$(".comment-reply").off("click");
				$(".comment-reply").click(function() {
                    var replyTargetIdentity = $(this).attr("id");
                    $(".reply-wrapper").remove();
                    $(this).parent().append(`
                    
                        <div class="reply-wrapper">
                            <textarea name="reply" class="form form-control" style="    margin-top: 30px; margin-bottom: 15px;"></textarea>
                            <button id="${replyTargetIdentity}" class="reply-write" style="border: none; background: white; color: gray;">
                                완료</button>
                            <button class="reply-cancel" style="border: none; background: white; color: gray;">
                                취소</button>
                        </div>
                    
                    `);

                    //=> 댓글 입력란 이벤트 등록(자동 높이 조절)
				    autosize($('textarea'));

                    //=> 이벤트 등록 (답글 작성)
                    $(".reply-write").off("click");
                    $(".reply-write").click(function() {

						$.ajax({
							url : "comment/write" ,
							method : "POST" , 
							data : { 
								"board" : boardIdentity ,
								"content" : $(this).prev().val() ,
								"replyTargetIdentity" : replyTargetIdentity
							} 
							, success : function() { 
                                                // 변경된 내용을 (댓글)을 화면에 표시
                                                callComments(boardIdentity); 					
								
							}
						}) // ~~ ajax 끝
                    }) // ~~ 답글 작성 완료 버튼 이벤트 끝


                    //=> 이벤트 등록 (답글 취소)
                    $(".reply-cancel").off("click");
                    $(".reply-cancel").click(function() {
                        $(this).parent().remove();
                    })
                }) // ~~ (답글 작성 폼 보여주기)






			}
		}) // ~~ ajax 끝 -!

	}


// ========================================================================================================== >>



	// ★ 랜더링후 버튼 이벤트 등록하는 메소드
	function setEvent() {


		// ☆ 버튼 이벤트 등록 [게시글 좋아요]
		$(".like").off("click");
		$(".like").click(function() {
		
			if ( userIdentity == -1 ) {

				Swal.fire({
					icon : 'warning',
					title: '요청 실패',
					text : '해당 기능을 이용하기위해 로그인이 필요합니다',
					confirmButtonText: '확인',
					showCancelButton: false,
					cancelButtonText: `닫기`,
				  })

				  return;
			}

			// 게시글 하단에 좋아요 n개 댓글 n개 의 내용 바꿔주기 위해 사용
			var originalText = $(".content-wrapper" + $(this).attr("id") + " sub").text();
			var myLikeCount = parseInt( originalText.split("좋아요 ")[1].split("개")[0] );	
			
		
			if ( $(this).children('i').hasClass("fa-regular") ) {
				$(this).children('i').removeClass("fa-regular").addClass("fa-solid")
				var replacedText = originalText.replace(/좋아요 [0-9]+개/g, "좋아요 " + (myLikeCount + 1) + "개");
				// 그 다음 실행될 내용 =>  $(".content-wrapper" + $(this).attr("id") + " sub").text( replacedText );
								
			} else {
				$(this).children('i').removeClass("fa-solid").addClass("fa-regular")
				var replacedText = originalText.replace(/좋아요 [0-9]+개/g, "좋아요 " + (myLikeCount - 1) + "개");
				// 그 다음 실행될 내용 =>  $(".content-wrapper" + $(this).attr("id") + " sub").text( replacedText );
			}
			
			
			$.ajax({ 
				url : "like_toggle" , method : "GET" , data : { "identity" : $(this).attr("id") }
				, success : function() { 
					//callBoards(); 
				}
			}) 
				
				
			$(".content-wrapper" + $(this).attr("id") + " sub").text( replacedText );					
		})  // ~~ 버튼 이벤트 종료 [좋아요]
		
		


		// ☆ 버튼 이벤트 등록 [댓글 목록]
		$(".comment").off("click");
		$(".comment").click(function() {
			 

			//=> 댓글 목록을 SweetAlert2 모달 라이브러리로 보여주기
			Swal.fire({
					title: ' ',
					scrollbarPadding: true ,
					showConfirmButton: false ,
					width: 900 ,
					html: `
					<div id="`+$(this).attr("id")+`" class="feed-comment-wrapper" style="min-height: 70vh; padding:10px; overflow-x: hidden;">

								<!-- ★ ajax 로 랜더링되는 부분 ★ -->

				</div>
					`
			}) // ~~ swal(모달) 화면 끝 !
				
		
			callComments( $(this).attr("id") );
				





		}) // ~~ 게시글 하단에 있는 "댓글 보기" 버튼 클릭 이벤트 마무리			
	} // ~~ 게시글의 하단에 있는 "댓글 보기"및  "좋아요" 버튼 클릭 이벤트 마무리 -!




// ========================================================================================================== >>

	function getComponentByBoard( boardData ) {		

		// userIdentity 로그인된 유저 pk.    identity db에 저장된 유저 pk
		var show = userIdentity == boardData.user ? "block" : "none";


		var row = `
			<div class="content-wrapper content-wrapper${boardData.identity}">	
			<div class="content-header">
				<div class="profileImageIcon profileImg${boardData.identity}" style="	background-size: cover; background-position: center; box-shadow: 0px 0px 5px rgba(0,0,0,0.15);"></div>
				<p><b>${boardData.nickname} </b></p>
				<p><font color="grey">${boardData.created}</font> </p>
				

				<div class="btn-group" style="display : ${show}" >
					<button type="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa-solid fa-gear"></i></button>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="modify?identity=${boardData.identity}">수정</a></li>
						<li><a class="dropdown-item" href="delete?identity=${boardData.identity}">삭제</a></li>
					</ul>
				</div>
			</div>`
			
	if ( boardData.likes.indexOf(userIdentity) > -1 ) {
		row += `
			<div class="content-body">${boardData.content}</div>
			<sub style="display: flex; justify-content: right; color: gray; margin-bottom: 30px;">좋아요 ${boardData.likeCount}개 · 댓글 ${boardData.commentCount}개</sub>
			<div class="content-footer">


				<button id="${boardData.identity}" class="like"><i class="fa-solid fa-heart"></i>
				좋아요</button>
				<button id="${boardData.identity}" class="comment"><i class="fa-regular fa-comment"></i>
				댓글달기</button>
			</div>
		</div>
		`;	
	} else {
		row += `
			<div class="content-body">${boardData.content}</div>
			<sub style="display: flex; justify-content: right; color: gray; margin-bottom: 30px;">좋아요 ${boardData.likeCount}개 · 댓글 ${boardData.commentCount}개</sub>
			<div class="content-footer">
				<button id="${boardData.identity}" class="like"><i class="fa-regular fa-heart"></i>
				좋아요</button>
				<button id="${boardData.identity}" class="comment"><i class="fa-regular fa-comment"></i>
				댓글달기</button>
			</div>
		</div>
		`;
	}
		
		return row;
	}
	










	function getComponentByComment( commentData ) {

        var row =  ``;
		var marginLeftSet = (commentData.index == 0) ? "0px" : "70px";
		var targetUserNickname = (commentData.index == 0) ? "" : commentData.target_user_nickname + "님에게 답글";

        // 프로필 이미지 (아이콘)
        // var setProfileImg = myProfile(commentData.imgPath);
        
        
        // 댓글 작성자 본인일경우 (수정, 삭제 표시)
		if (  userIdentity == commentData.user  ) {
						row += `					
							<div class="feed-comments${commentData.identity} feed-comments" style="margin-bottom: 70px; text-align:left; margin-left:  ${marginLeftSet}; ">
								<div class="profileImageIcon comment_profileImg${commentData.identity}" style="box-shadow: 0px 0px 5px rgba(0,0,0,0.15);"></div>
										<h4 style="display: inline" id="${commentData.user}">${commentData.nickname}</h4>
										<sub style="color:grey">${commentData.created}</sub>	
										<small style="color: silver; display: block;">${targetUserNickname}</small>
										<textarea style="margin: 20px 0; border:none; outline:none; display: block; cursor: default; width: 100%" class="" readOnly>${commentData.content}</textarea>
										
										<button id="${commentData.identity}" class="comment-edit" style="border:none;background:white;color:grey;">
										수정</button>
										<button id="${commentData.identity}" class="comment-delete" style="border:none;background:white;color:grey;">
										삭제</button>
										<button id="${commentData.identity}" class="comment-reply" style="border:none;background:white;color:grey;">
										답글</button>
								</div>
							</div>
						`;

        // 댓글 작성자 본인이 아닐경우
        } else {
						row += `										
						    <div class="feed-comments${commentData.identity} feed-comments" style="margin-bottom: 70px; text-align:left; margin-left:  ${marginLeftSet}; ">
                              <div class="profileImageIcon comment_profileImg${commentData.identity}" style="box-shadow: 0px 0px 5px rgba(0,0,0,0.15);"></div>
								<h4 style="display: inline" id="${commentData.user}">${commentData.nickname}</h4>
								<sub style="color:grey">${commentData.created}</sub>	
								<small style="color: silver; display: block;">${targetUserNickname}</small>
								<textarea style="margin: 20px 0; border:none; outline:none; display: block; cursor: default; width: 100%" class="" readOnly>${commentData.content}</textarea>`
                        
                        // 로그인 상태일경우, 답글 버튼을 표시
                        if ( userIdentity >= 0 ) { 
                            row += `
                            <button id="${commentData.identity}" class="comment-reply" style="border:none;background:white;color:grey;">
							답글</button>`;
                        }
                row += `</div>`;
        }
		
		return row;
	}


// ========================================================================================================== >>


	var start = 0;
	var boardUpdate = true;
	callBoards();
	
	$(window).scroll(function(){
	        var scrollTop = $(this).scrollTop();
	        var outerHeight = $(this).outerHeight();
	        var scrollHeight = $(document).height();
	
	        
	        if ( boardUpdate == true && (scrollTop + outerHeight) >= scrollHeight) {
	        	boardUpdate = false;
	        	callBoards(); // ★ ==> 해당 메소드에서 ajax 로 데이터를 받아온뒤 다시 baordUpdate = true; 라고 해주기 !! 
	        	
	        }
	});


}) // 자바스크립트 끝 -!



