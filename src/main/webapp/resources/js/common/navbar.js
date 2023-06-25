$(document).ready(function(){
//=============================================================================================================>

	//=> ★ URL 자동 변환기	
    function urlConverter(originalURL) {
    
    var urlCheck = window.location.href;
	    if ( urlCheck.indexOf("smile") >= 0 ) {
	    	return "http://" + window.location.host + "/smile/" + originalURL;
	    }
	    return window.location.host + "/" + originalURL;
    }
    
    
//=============================================================================================================>
	
	
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
				clearInterval( notificationCheck ); 
			}
		}) // ~~ ajax 끝					
	}

	notificationCheck();
//=============================================================================================================>	

	//=> 알람 버튼을 눌렀을때
	$(".notification").click(function() {		
		
		//=> 모달 띄우고
		Swal.fire({
		    icon: 'success',
		    title: '테스트',
		    html: `<div class="notification-wrapper">테스트중</div>`,
		    showCancelButton: false,
		    confirmButtonText: '확인'
		});
		
		
		//=> 모달에 내용 보여주기
		$.ajax({
			url : urlConverter("notification/list"),
			success : function( data ) {
				
				for (var i=0; i < data.length; i ++) {
					
				}
				$(".notification-wrapper").append("<h1>정보 가져오기 성공함</h1>");
			}
		})
		
		
		
	})	 // ~~ 알람 버튼 클릭시 이벤트 종료

	

//=============================================================================================================>

	function getComponent( data ) {
	
		return `
		
			<div></div>
		
		`;
	}
}) // ~~ 자바스크립트 끝