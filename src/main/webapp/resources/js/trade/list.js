$(document).ready(function() {
	
	
	function getComponent( data ) {
		
		if ( data.contacted === "만나요" ) { 			
			return  `
			<div class="content-wrapper item${data.identity}">		
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
				</div>
			</div>
			`;	
	 	
	 	} else {
	 	
			return  `
			<div class="content-wrapper item${data.identity}">		
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
				</div>
			</div>
			`;	
	 	
	 	}
	} // ~~ getComponent() 함수 종료
	
	


	$.ajax({
	
		url : "getPosts" ,
		method : "GET" ,
		
		success : function( data ) {	
		
			alert("조회갯수 => " + data.length);
		
			for( var i=0; i<data.length; i++ ) {
				$(".main-content").append( getComponent(data[i]) );		
				$(".item" + data[i].identity + " > .content-left").css("background-image", `url('display?fileName=${data[i].imgPath}')`);	
			} // ~ for 반복문 끝
	
// ================================================================================	
	
			$(".content-wrapper").click(function() {
				
				const tradeTitle = $(this).find('.title > h1 > p').text(); 
				const tradePrice = $(this).find('.price').text();
				const tradeText = $(this).find('.badge').text();
				var tradeType; 
				var tradeLocation; 
				
				if ( tradeText == "만나요"  ) { 
					tradeType = "<span class='badge bg-secondary'>만나요</span>"
					tradeLocation = $(this).find(".location").text();
					
				} else {
					tradeType = "<span class='badge bg-primary'>비대면</span>";
				}
													
														
				
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