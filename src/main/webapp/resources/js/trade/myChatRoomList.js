$(document).ready(function() {
	
	autosize($('textarea'));
	

	$(".goBoard").click(function(){
	    Swal.fire({
	        title : '상세 정보'
	        , width : 900
	        , showConfirmButton : false
	        , html: `<div class="trade-info-wrapper"></div>`
	     })	
	     
	     callContent( $(this).attr("id") );
	})

	
	//var childWindow = window.open( urlConverter(`trade/list?searchByBoardIdentity=${dto.board}`) );
	
	function callContent(boardIdentity) {

	    var row = ``;

	    $.ajax({
	    url : urlConverter("trade/content_view") ,
	    data : { "identity" : boardIdentity },
	    success : function( data ) {
	    	
	    if ( data.user == loginUserIdentity ) {
	    	row += `
			    <div class="btn-group" style="display : block; position: absolute; right: 10px;">
			        <button type="button" data-bs-toggle="dropdown" aria-expanded="false"
			            style="border: none; background: white; outline: none;"><i class="fa-solid fa-gear"></i></button>
			        <ul class="dropdown-menu" style="padding: 10px;">
			            <li><a class="dropdown-item" href="../trade/modify?identity=${data.identity}">수정</a></li>
			            <li><a class="dropdown-item" href="../trade/delete?identity=${data.identity}">삭제</a></li>
			        </ul>
			    </div>
		    `
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
	
		        </div>
        `



        if ( data.contacted === "만나요" ) {
	        row += `<div class="trade-location">
			            <h4 style="display: inline-block;"><i class="fa-solid fa-map-location-dot mb-3">
			                    거래장소</i></h4> ${data.address}<br>
			            <div id="map" style="width: 100%; height: 300px;"></div>
			            <hr><br>
			        </div>
	        `
        }


        
        row += `
	        <h4 style="display: inline-block;"><i class="fa-solid fa-camera"> 미리보기</i></h4>
	        <div class="picture"
	            style="background-image: url('../trade/display?fileName=${data.imgPath}'); width: 100%;    height: 400px; background-repeat: no-repeat;    background-size: cover; background-position: center; ">
	        </div>
	        <hr><br>
	
	        
	        
	        <div class="trade-info">
	            <h4><i class="fa-solid fa-keyboard"></i> 작성내용</h4>
	            <div style="white-space: pre-line;">${data.content}</div>
	        </div>
        `
		

        if ( loginUserIdentity > 0 && loginUserIdentity != data.user) {	
	        var insertBtnSet = ``;
	        if ( data.likes.indexOf(loginUserIdentity) >= 0 ) {
		        // 만약 관심 목록에 추가한 사람이라면
		
		        insertBtnSet = `
		        <div class="insert-btn"><i class="fa-solid fa-clipboard-check"></i>
		            관심목록에서 삭제</div>
		        `;
		
	        } else {
	        // 아니라면
		
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
		        </div>
		    `
        }


        //=> 위에서 적어놨던 태그들이 화면에 표시되는 순간
        $(".trade-info-wrapper").html(row);

        //=> 표시된 태그들중에서, 관심목록 버튼에 대한 클릭시 효과 넣어주기
        $(".insert-btn").off("click");
        $(".insert-btn").click(function() {
	
	        var targetText = $(".content-wrapper" + boardIdentity + " sub");
	        var originalText = targetText.text();
	        var myLikeCount = parseInt( originalText );
	
	        if ( $(this).text().indexOf("관심목록에 추가") >= 0 ) {
	        	// "관심목록에 추가" 버튼을 눌렀을땐, 글자를 "관심목록에서 삭제"로 변경
		        $(this).html(`<i class="fa-solid fa-clipboard-check"></i> 관심목록에서 삭제`);
		        targetText.text( " " + (myLikeCount+1) );
		
	        } else {
		        // "관심목록에서 삭제" 버튼을 눌렀을땐, 글자를 "관심목록에 추가" 로 변경
		        $(this).html(`<i class="fa-solid fa-clipboard-list"></i> 관심목록에 추가`);
		        targetText.text( " " + (myLikeCount-1) );
	        }
	
	
	        $.ajax({
			    url : urlConverter("trade/like_toggle") ,
                method : "POST" ,
                data : { "identity" : boardIdentity }
	        }) // ~ ajax


       	}) // ~ insert-btn click fnc




        if ( data.contacted === "만나요" ) {
	        // 카카오맵 표시
	        getMap(data.address);
        }


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

        $("#go-chat").click(function () {
	        window.open('../chatroom/write?board='+data.identity+'&seller='+data.user)
        })




      } // ~~ success 콜백 함수 끝


    }) // ~~ ajax 종료 끝
   } // ~~ 이벤트 등록 함수 끝
	
	
	
})// ~~ end