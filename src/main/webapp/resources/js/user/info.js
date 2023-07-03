$(document).ready(function() {
	
	$(".goChattingRoom").click(function(){
   		//=> 대화방 보기 눌렀을때 
		window.open( urlConverter(`chatroom/myChatRoomList?userId=${user_identity}`) );
	   
	})
	$(".goMyFeeds").click(function(){
	   //=> 내가 작성한 피드 보기 눌렀을때
		window.open( urlConverter(`main/list?searchByNickname="${user_nickname}"`) );	
	   
	})
	$(".goMyTrades").click(function(){
	   //=> 내가 판매중인 물건들 눌렀을때
		window.open( urlConverter(`trade/list?searchByNickname="${user_nickname}"`) );
	   
	})
	$(".goMyLikeFeeds").click(function(){
	   //=> 내가 좋아요 누른 피드만 보기 눌렀을때
	   window.open( urlConverter(`main/list?searchByLikeUser=${user_identity}`) );
	})
	$(".goMyLikeTrades").click(function(){
	   //=> 내가 관심있는 물건들만 보기 눌렀을때
		window.open( urlConverter(`trade/list?searchByLikeUser=${user_identity}`) );
	   
	})
	
	
	    
	// user 테이블의 imgPath 컬럼 값이 null (기본값) 이라면  기본 프로필 사진을 보여주고,  null 이 아니라면 등록된 사진을 보여줌
    var setProfilePath = getProfilePath( imgPath );   
    getProfileImage(setProfilePath, ".profile-img");

	
	// 프로필 이미지 눌렀을때, 새 창으로 열기  
	$(".profile-img").click(function() {
		window.open( urlConverter('user/display?fileName='+imgPath) );
	})
	
	
	// 프로필 이미지 수정 버튼누르고 + 파일 첨부 했을때 발생
	$("input[name='imgPath']").change(function() {
	   Swal.fire({
	        icon : 'question',
	        title: '프로필 사진 변경',
	        text : '선택하신 사진으로 프로필을 변경하시겠습니까?',
	        showCancelButton: true,
	        confirmButtonText: '변경',
	        cancelButtonText : '취소',
	      }).then((result) => {
	        if (result.isConfirmed) {
	
	         var formData = new FormData();
	         var inputFile = $("input[name='imgPath']");
	         files = inputFile[0].files;
	         console.log("---------------")
	         console.log(files);
	         console.log("---------------")
	
	         for (var i = 0; i < files.length; i++) {
	            formData.append("uploadFile", files[i]);
	         }
	         
	         $.ajax({
	            url: "uploadProfile"
	            ,processData: false
	            ,contentType: false
	            ,data: formData
	            ,type: "POST"
	            ,success: function(result){
	               console.log("uploaded");
	               console.log(result);
	               
	               if(result!=null){
	                  console.log("if 문까지 왔음");
	                  location.reload();
	               }			   
	            }//success
	         })//ajax
	
	      } // ~ (end) 확인 버튼 눌럿을때
	        
	   }) // ~ (end) swal 닫을때
	})  // ~ (end) 프로필 수정 이벤트 감지



	// 회원정보 수정 버튼 눌렀을 때 회원정보 수정 화면으로 이동
    $("#modifyInfo").click(function(){
    	location.href = "modify";
    });
	
	// 로그아웃 버튼 눌렀을 때 세션 종료 후 메인 게시판으로 이동
	$("#logOut").click(function(){
		location.href = "logOut";
	});

	// 포인트 충전 버튼 눌렀을 때
	$("#pointUp").click(function(){
		// 		console.log("충전");

				var amount = "";
				var method = "";
				var through = "";

				const { value: formValues } = Swal.fire({
					  title: '포인트 충전',
					html: `
					<div id="paymentInfo"> 
						<select id="swal-input2" class="swal2-input" style="width: 300px; margin: 10px 0px;"> 
						  		<option value="card">카드 결제</option> 
						  		<option value="trans">계좌 이체</option> 
						</select>
						
						<select id="swal-input3" class="swal2-input" style="width: 300px; margin: 10px 0px;"> 
						  <option value="kakaopay">카카오페이</option> 
						  <option value="tosspayments">토스페이먼츠</option> 
						</select>
						 
						<input type="number" min="0" value="1000" step="1000" id="swal-input1" class="swal2-input" style="width: 300px; margin: 10px 0px;"> 
					</div>`,

					confirmButtonText: '확인',
					showCancelButton: true,
					cancelButtonText: '취소',
					preConfirm: () => {
					  return [
						amount = document.getElementById('swal-input1').value,
						method = document.getElementById('swal-input2').value,
						through = document.getElementById('swal-input3').value
					  ]
					}
				  }).then((result) => {
					  if (result.isConfirmed) {

				        // 주문번호 만들기(사용자 identity + 현재시간 밀리초) 
				        var date = new Date();
				        var dateNum = date.getTime();
				       	var orderId = user_identity + "" + dateNum

						// 이메일 주소 받기
						console.log(user_id);
						
						// easyPayment.js 에 정의된 requestPay 메소드 호출하여 카카오페이 or 토스페이먼츠 연결 
						requestPay(dateNum, method, through, user_id, amount);
					  };
					
			}); // 포인트 충전 눌렀을 때 끝
		})
		
})// ~~ end