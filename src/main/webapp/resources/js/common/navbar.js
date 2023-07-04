//=> ★ URL 자동 변환기	
function urlConverter(mappingURL) {

var urlCheck = window.location.href;
    if ( urlCheck.indexOf("smile") >= 0 ) {
    	return "http://" + window.location.host + "/smile/" + mappingURL;
    }
    return "http://" + window.location.host + "/" + mappingURL;
}
//=============================================================================================================>
    
    
    
$(document).ready(function(){
	
	
	//=> ★ 실시간 알람갯수 체크	
	const notificationCheckTimer = setInterval(function() {
		notificationCheck();
	}, 1000); // ~~ 타이머 종료
	
	function notificationCheck() {
		$.ajax({
			url : urlConverter("notification/list"),
			success : function( data ) {
				if ( data.length > 0 ) { 
					$(".notification").css("display", "block");
					$(".notification > .badge").text( data.length ); 
				} else {
					$(".notification").css("display", "none");
				}
			}, // ~~ success 종료
			error : function() { 
				// 로그인 상태가 아닐경우, 타이머를 중단함
				clearInterval( notificationCheckTimer ); 
			}
		}) // ~~ ajax 끝					
	}

	notificationCheck();
//=============================================================================================================>	

	//=> ★ 알람 (종 모양) 눌렀을때 모달창 열어주기
	$(".notification").click(function() {		
		
		//=> 모달 띄우고
		Swal.fire({
		    icon: 'success',
		    title: ' ',
		    width: 900 ,
		    html: `<div class="notification-wrapper" style="max-height:300px; overflow-y: auto;">
		    
				<div class="d-flex justify-content-center loading">
				    <div class="spinner-border" role="status">
				        <span class="visually-hidden">Loading...</span>
				    </div>
				</div>
		    
		    </div>
		    `,
		    showCancelButton: false,
		    confirmButtonText: '확인'
		});
		
		
		//=> 모달에 내용 보여주기		
		callNotifications();
	})	 // ~~ 알람 버튼 클릭시 이벤트 종료

	

//=============================================================================================================>

	//=> ★ 알람창 화면에 데이터 보여주기 (데이터 바인딩)
	function callNotifications() {
	
		$.ajax({
			url : urlConverter("notification/list"),
			success : function( data ) {
				
				if ( data.length == 0 ) { $("button.swal2-confirm.swal2-styled").click(); }
				
				var row = ``;
				$(".notification-wrapper > .loading").remove();
				
				
				for (var i=0; i < data.length; i ++) {
					row += getComponent( data[i] );
				}
				
				$(".notification-wrapper").html( row );
				$("#swal2-title").text( "새로운 소식이 " + data.length + "건 조회되었습니다 : )" );
				setEvent();
				
			}
		})	
	}


	//=> ☆ 위에있는 callNotifications 가 호출하는 함수 ▼
	function getComponent( data ) {
		
		var msgType;
		console.log(data);
		if ( data.url_path.indexOf("chat/chatContent?board=") >= 0 ) {
			msgType = "님이 대화를 보냈습니다.";
		} else {
			msgType = "님이 댓글을 남겼습니다.";		
		}
		 
		
		return `
	
        		<div class="alert alert-light alert-dismissible fade show" role="alert" style="text-align:left;">
					<div class="notificationItems" id="${data.url_path}">
						<b>${data.senderNickname}</b>${msgType}<br>
						    <div style="display: block; margin-top: 10px;"><sub>${data.created}</sub></div><br>
						    <div style="white-space: pre-line; display: block;">${data.msg}</div>
					</div>
					<button id="${data.identity}" type="button" class="btn-close notification-close-btn" ></button>
				</div>	
		
		`;
	}
	
	
//=============================================================================================================>


	function setEvent() {
	
		$(".notification-close-btn").off("click");
		$(".notification-close-btn").click(function() {
		
		$.ajax({
			url : urlConverter("notification/delete"),
			method : "POST",
			data : { "identity" : $(this).attr("id") },
			success : function() {

				//=> 알람 삭제성공시, 변경된 데이터로 다시 보여주기 (랜더링)
				callNotifications();

			}
		}) // ~~ ajax 끝		
		}) // ~~ 알람 삭제 버튼 이벤트 끝
	
	
		$(".notificationItems").off("click");	
		$(".notificationItems").click(function(){
		
			var urlPath = urlConverter($(this).attr("id"));
			window.open( urlPath  );				
		})
	
	} // ~~ 이벤트 등록 끝
	
	
	
	
	

	
	
	
}) // ~~ 자바스크립트 끝