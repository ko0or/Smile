$(document).ready(function() {
	
	var count = 0;

function getComponent( data ) {
      
      if ( data.contacted === "만나요" ) {          
         
         var row =  `
         <div id="${data.identity}" class="content-wrapper">      
         	<div id="${data.user}" class="userPK" style="display:none"></div>
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
         	<div id="${data.user}" class="userPK" style="display:none"></div>      
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
            
            
            // 에이잭스로 받은 data 배열과 매칭하는 숫자
            var boardDataArray = $(this).find(".title").attr("id");
            
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
                title: '상세 정보',
                showConfirmButton: false ,
                width: 900 ,
                html: `
                <div class="trade-info-wrapper">
                      <!-- feed-comment-wrappe 클래스 안에 랜더링해주기 -->           
                      
                      <div class="btn-group" style="display : ${show}; position: absolute; right: 10px;" >
                          <button type="button" data-bs-toggle="dropdown" aria-expanded="false" style="border: none; background: white; outline: none;"><i class="fa-solid fa-gear"></i></button>
                          <ul class="dropdown-menu" style="padding: 10px;">
                              <li><a class="dropdown-item" href="modify?identity=${boardIdentity}">수정</a></li>
                              <li><a class="dropdown-item" href="delete?identity=${boardIdentity}">삭제</a></li>
                          </ul>
                      </div>
                      
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
                      

                    <h4 style="display: inline-block;"><i class="fa-solid fa-camera"> 미리보기</i></h4>
                    <div class="picture" style="background-image: url('display?fileName=${data[boardDataArray].imgPath}'); width: 100%;    height: 400px; background-repeat: no-repeat;    background-size: cover; background-position: center; ">                        
                    </div>
                    <hr><br>
                    
                      
                      <div class="trade-info">
                       
                       <h4><i class="fa-solid fa-keyboard"></i> 작성내용</h4>
                       <div style="white-space: pre-line;">${data[boardDataArray].content}</div>
                    </div>
                    
                     <div class="footer-btns" style="display: flex; justify-content: space-around;">
                         <div class="insert-btn"><i class="fa-solid fa-cart-flatbed-suitcase"></i>
                          관심목록</div>                     
                          <div class="contact-btn"><i class="fa-regular fa-message"></i>
                          문의하기</div>
              		 </div>        
                      <!-- 랜더링 공간 종료 -->
              	</div>
                `
              })
              
              $(".picture").click(function() {
              
				// 현재 URL 가져오기
				var currentURL = window.location.href;
				// 변경할 주소
				var newAddress = $(this).attr("style").match(/url\(['"]?([^'"]+)['"]?\)/)[1];
				// URL 분할
				var urlParts = currentURL.split("/");
				var lastSegment = urlParts[urlParts.length - 1];
				// 변경된 주소로 조합
				var newURL = currentURL.replace(lastSegment, newAddress);
				// 새로운 URL로 이동
				window.open( newURL );
              
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