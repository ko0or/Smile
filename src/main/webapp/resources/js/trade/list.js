$(document).ready(function() {
	
	var count = 0;

	function getComponent( data ) {
		
		if ( data.contacted === "만나요" ) { 			
			
			var row =  `
			<div id="${data.identity}" class="content-wrapper">		
				<div class="content-left"></div>		
				<div class="content-right">
					<div id="${count}" class="title">
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
				</div>
			</div>
			`;	

			count ++;
			return row;
	 	
	 	} else {
	 	
			var row =  `
			<div id="${data.identity}" class="content-wrapper">		
				<div class="content-left"></div>		
				<div class="content-right">
					<div id="${count}" class="title">
						<h1>
							<span class="badge bg-primary">${data.contacted}</span>
							<p>${data.title}</p>
						</h1>
						
					</div>
					<div class="price"><h2>${data.price}원</h2></div>
					<div class="date"><p>${data.created}</p></div>
				</div>
			</div>
			`;	
	 	
			count ++;
			return row;

	 	}
	} // ~~ getComponent() 함수 종료
	
	


	$.ajax({
	
		url : "getPosts" ,
		method : "GET" ,
		
		success : function( data ) {	
		
			alert("조회갯수 => " + data.length);
		
			for( var i=0; i<data.length; i++ ) {
				$(".main-content").append( getComponent(data[i]) );		
				$("#" + data[i].identity + " > .content-left").css("background-image", `url('display?fileName=${data[i].imgPath}')`);	
			} // ~ for 반복문 끝
	
// ================================================================================	
	
			$(".content-wrapper").click(function( ) {
				
				const tradeTitle = $(this).find('.title > h1 > p').text(); 
				const tradePrice = $(this).find('.price').text();
				const tradeText = $(this).find('.badge').text();
				var tradeType; 
				var tradeLocation; 
				
				var boardIdentity = $(this).find(".title"). attr("id");

				if ( tradeText == "만나요"  ) { 
					tradeType = "<span class='badge bg-secondary'>만나요</span>"
					tradeLocation = $(this).find(".location").text();
					
				} else {
					tradeType = "<span class='badge bg-primary'>비대면</span>";
				}
													
				console.log(data);										
				console.log(boardIdentity);
				console.log(data[boardIdentity].content); // 배열은 0부터 4까지 ( 총 5개 ) 있는데,    boardIdentity 는 20 , 21 .. 이렇게 pk 값이라서 서로 안맞음
				

				Swal.fire({
					  title: '상세 정보',
					  showConfirmButton: false ,
					  width: 900 ,
					  html: `
					  <div class="trade-info-wrapper">
					  		<!-- feed-comment-wrappe 클래스 안에 랜더링해주기 -->			  
					  		
					  		<div class="trade-header">
					  				<h1>` + tradeTitle  + `</h1>
					  				거래유형 : ` + tradeType + ` <br>
					  				판매가격 : ` + tradePrice + ` <br>
					  				<hr><br>
					  		</div>
					  		
					  		<div class="trade-location">
					  			<h4 style="display: inline-block;"><i class="fa-solid fa-map-location-dot mb-3"> 
					  			거래장소</i></h4> 
					  			` +  tradeLocation + `<br>
					  			<div id="map" style="width: 100%; height: 300px;"></div>			  			
					  			<hr><br>
				  			</div>
					  		
					  		
					  		<div class="trade-info">
								
								<h4><i class="fa-solid fa-keyboard"></i> 작성내용</h4>
								<div style="white-space: pre-line;">${data[boardIdentity].content}</div>
							</div>
							
					  		<div class="contact-btn"><i class="fa-regular fa-message"></i>
					  		문의하기</div>
					  		
					  		<!-- 랜더링 공간 종료 -->
					</div>
					  `
					})		
				if ( tradeText == "만나요" ) { getMap(tradeLocation); 
				} else { 
					$(".trade-location").remove();
				}
			}) // ~ 버튼 이벤트 종료	
	
// =================================================================================	
		} // ~ success 콜백함수 끝	
	}) // ~ ajax 끝 
	
	
	
})