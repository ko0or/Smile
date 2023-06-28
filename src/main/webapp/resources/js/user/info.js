$(document).ready(function() {
	
	//  
	
	
	
	
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
					  title: 'Multiple inputs',
					html: `
					<div id="paymentInfo"> 
						<input type="number" step="1000" id="swal-input1" class="swal2-input"> 
						<select id="swal-input2" class="swal2-input"> 
						  		<option value="card">카드 결제</option> 
						  		<option value="trans">계좌 이체</option> 
						</select>&nbsp;&nbsp;&nbsp; 
						
						<select id="swal-input3" class="swal2-input"> 
						  <option value="kakaopay">카카오페이</option> 
						  <option value="tosspayments">토스페이먼츠</option> 
						</select> 
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
						Swal.fire('Saved!', '', 'success')

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