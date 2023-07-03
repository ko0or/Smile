$(document).ready(function() {

	/*
		# 함수들
		.. callBoards(): 게시글들 표시 (스크롤 페이징)
		.. callContent(): 게시글 클릭시 세부 내용 표시 + 세부내용 안에서도 지도 등 각종 필요 이벤트 등록
		.. getComponentByBoard(): 게시글 디자인 만들어주는 함수
		.. setEvent(): 이벤트 등록하는 함수
		.. 스크롤 바 이벤트
	*/
		  
		var count = 0;
		
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
				  
						for( var i=0; i<data.length; i++ ) {
							$(".main-content").append( getComponentByBoard(data[i]) );

                            // 물건 사진 표시                            
                            var setProfilePath = getProfilePath( data[i].imgPath );   
                            var setProfileTargetClass = ".content-wrapper" + data[i].identity + " > .content-left";
                            getProfileImage(setProfilePath, setProfileTargetClass);

						} // ~ for 반복문 끝
		   					  
						$(".content-wrapper").off("click");
						setEvent();
						boardUpdate = true;
		
					} // ~ success 콜백함수 끝   
			}) // ~ ajax 끝 	
		} // ~ callBoards 함수 끝
		// ========================================================================================================== >>
		
		function callContent(boardIdentity) {
		
			var row = ``;
		
			$.ajax({
				url : "content_view" ,
				data : { "identity" :  boardIdentity},
				method : "GET" ,
				success : function( data ) {
		
					if ( data.user == user ) {
						row +=  `               
						<div class="btn-group" style="display : block; position: absolute; right: 10px;" >
							<button type="button" data-bs-toggle="dropdown" aria-expanded="false" style="border: none; background: white; outline: none;"><i class="fa-solid fa-gear"></i></button>
							<ul class="dropdown-menu" style="padding: 10px;">
								<li><a class="dropdown-item" href="modify?identity=${data.identity}">수정</a></li>
								<li><a class="dropdown-item" href="delete?identity=${data.identity}">삭제</a></li>
							</ul>
						</div>`
					}

					row += `
						<div class="trade-header">
							<h1>${data.title}</h1>
							거래유형 : `

					if ( data.contacted === "비대면") {
						row += `<div class="badge bg-primary">`;
					} else {
						row += `<div class="badge bg-secondary">`;
					}

					row +=`${data.contacted}</div><br>
						판매가격 : ${data.price}원<br>
						판매자명 : ${data.nickname}<br>
						<hr><br>
						</div>`	

					if ( data.contacted === "만나요" ) {
						row +=  `
						<div class="trade-location">
							<h4 style="display: inline-block;"><i class="fa-solid fa-map-location-dot mb-3"> 
							거래장소</i></h4> ${data.address}<br>
							<div id="map" style="width: 100%; height: 300px;"></div>                    
							<hr><br>
						</div>`
					} 		
					
					row += `
					<h4 style="display: inline-block;"><i class="fa-solid fa-camera"> 미리보기</i></h4>
						<div class="picture" 
								 style=" width: 100%;    height: 400px; background-repeat: no-repeat;    background-size: contain; background-position: center; ">
						</div>
					<hr><br>
					
					<div class="trade-info">
						<h4><i class="fa-solid fa-keyboard"></i> 작성내용</h4>
						<div style="white-space: pre-line;">${data.content}</div>
					</div>`

					if ( user > 0 && user != data.user) {        
				
						var insertBtnSet = ``;
						// 만약 관심 목록에 추가한 사람이라면
						if ( data.likes.indexOf(user) >= 0 ) {
						
							insertBtnSet = `
							<div class="insert-btn"><i class="fa-solid fa-clipboard-check"></i> 
							관심목록에서 삭제</div>
							`;
						
						// 아니라면
						} else {
						
							insertBtnSet = `
							<div class="insert-btn"><i class="fa-solid fa-clipboard-list"></i>
							관심목록에 추가</div>
							`;
						}
					
						row += `
						<div class="footer-btns" style="display: flex; justify-content: space-around;">
						${insertBtnSet}                  
						<div class="contact-btn" id="go-chat"><i class="fa-regular fa-message"></i>
        		       문의하기</div>
						</div>`        
					}


					//=> 위에서 적어놨던 태그들이 화면에 표시되는 순간
					$(".trade-info-wrapper").html(row);
                    var setComtentProfilePath = getProfilePath( data.imgPath );   
                    var setComtentProfileTargetClass = ".picture";
                    //getProfileImage(setComtentProfilePath, setComtentProfileTargetClass);
                    setContentImage(setComtentProfilePath, setComtentProfileTargetClass);



					//=> 표시된 태그들중에서,  관심목록 버튼에 대한 클릭시 효과 넣어주기
					$(".insert-btn").off("click");
					$(".insert-btn").click(function() {
				   
						var targetText = $(".content-wrapper" + boardIdentity + " sub");
						var originalText = targetText.text();
						var myLikeCount = parseInt( originalText );	
						
						// "관심목록에 추가" 버튼을 눌렀을땐,  글자를   "관심목록에서 삭제"로 변경
						if ( $(this).text().indexOf("관심목록에 추가") >= 0 ) {
							$(this).html(`<i class="fa-solid fa-clipboard-check"></i> 관심목록에서 삭제`);
							targetText.text( " " + (myLikeCount+1) );
						
						// "관심목록에서 삭제" 버튼을 눌렀을땐,  글자를 "관심목록에 추가" 로 변경
						} else {
							$(this).html(`<i class="fa-solid fa-clipboard-list"></i> 관심목록에 추가`);
							targetText.text( " " + (myLikeCount-1) );
						}
						$.ajax({
							url : "like_toggle" ,
							method : "POST" ,
							data : { "identity" : boardIdentity }  
						}) // ~ ajax 
					})

					if ( data.contacted === "만나요" ) {
						// 카카오맵 표시
						getMap(data.address);
					}

					$(".picture").click(function() {
						// 현재 URL 가져오기
						var currentURL = window.location.href;
						// 변경할 주소
						
						if ( $(this).attr("style").indexOf("display?fileName=") >= 0 ) {
							var newAddress = $(this).attr("style").match(/url\(['"]?([^'"]+)['"]?\)/)[1];
							window.open( newAddress );
							return;
						}
						
						var newAddress = $(this).attr("style").match(/url\(['"]?([^'"]+)['"]?\)/)[1];
						// URL 분할
						var urlParts = currentURL.split("/");
						var lastSegment = urlParts[urlParts.length - 1];
						// 변경된 주소로 조합
						var newURL = currentURL.replace(lastSegment, newAddress);
						// 새로운 URL로 이동
						window.open( newURL );
					})
					
					$("#go-chat").click(function () {
						var productPrice = parseInt(data.price.replace(/,/g, ""));
						 if ( userPoint >= productPrice ) {
	       				 	window.open('../chatroom/write?board='+data.identity+'&seller='+data.user)
	       				 } else {
	       				 	alert("포인트가 부족합니다");
	       				 }
	    			 })
					
				} // ~~ success 콜백 함수 끝
			}) // ~~ ajax 종료 끝   
		} // ~~ 이벤트 등록 함수 끝
		
		
		// ========================================================================================================== >>
		function setEvent( data ) {
		
			$(".content-wrapper").click(function() {
					
				const tradeTitle = $(this).find('.title > h1 > p').text(); 
				const tradePrice = $(this).find('.price').text();
				const tradeText = $(this).find('.badge').text();
				var tradeType; 
				var tradeLocation; 

				// 실제 데이터의 pk값
				var boardIdentity = $(this).attr("id");

				if ( tradeText == "만나요"  ) { 
					tradeType = "<span class='badge bg-secondary'>만나요</span>"
					tradeLocation = $(this).find(".location").text();

				} else {
					tradeType = "<span class='badge bg-primary'>비대면</span>";
				}

				var authorIdentity = $(this).find(".userPK").attr("id");            
				var show = (user == authorIdentity ) ? "block" : "none";
				console.log(authorIdentity);
				console.log(show);
			  		
				Swal.fire({
					title : '상세 정보'
					, width : 900 
					, showConfirmButton : false
					, html: `<div class="trade-info-wrapper"></div>`          
				})
				callContent(boardIdentity);		

			}) // ~ 버튼 이벤트 끝   
		} // ~~ setEvent 함수 끝
		
		// ========================================================================================================== >>
		function getComponentByBoard( data ) {
			  
			if ( data.contacted === "만나요" ) {          
			  
				var row =  `
				<div id="${data.identity}" class="content-wrapper content-wrapper${data.identity}">      
					<div id="${data.user}" class="userPK" style="display:none"></div>
					<div class="content-left"></div>      
					<div class="content-right">
					<div class="title">
						<h1>
							<span class="badge bg-secondary">${data.contacted}</span>
							<p>${data.title}</p>
						</h1>
					</div>
					<div class="price"><h2>${data.price}원</h2></div>
					<div class="location">
						<p><i class="fa-solid fa-map-location-dot"></i> 
						${data.address}</p></div>                  
					<div class="date"><p>${data.created}</p></div>
					<div style="position: absolute; right: 30px; bottom: 30px; font-size: x-large; color: darkseagreen;">
						<sub class="fa-solid fa-clipboard-check"> ${data.likeCount}</sub>
					</div>
				</div>
				`;   
		
				return row;
			
			} else {
			
				var row =  `
				<div id="${data.identity}" class="content-wrapper content-wrapper${data.identity}">      
					<div id="${data.user}" class="userPK" style="display:none"></div>      
					<div class="content-left"></div>      
					<div class="content-right">
					<div class="title">
						<h1>
							<span class="badge bg-primary">${data.contacted}</span>
							<p>${data.title}</p>
						</h1>					   
					</div>
					<div class="price"><h2>${data.price}원</h2></div>
					<div class="date"><p>${data.created}</p></div>
					<div style="position: absolute; right: 30px; bottom: 30px; font-size: x-large; color: darkseagreen;">
						<sub class="fa-solid fa-clipboard-check"> ${data.likeCount}</sub>
					</div>
				</div>
				`;   
				
				return row;    
			}
		} // ~~ getComponentByBoard() 함수 종료
		
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
				callBoards();							
			}
		});					
})