document.write('<script src="../resources/js/api/easyPayment.js"></script>');

$(document).ready(function() {
	
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
					  /* Read more about isConfirmed, isDenied below */
					  if (result.isConfirmed) {
						Swal.fire('Saved!', '', 'success')
						
						console.log("saved까지 완료. AJAX 전단계");
						requestPay(1234, method, through, 'zihye.choi@gmail.com', amount);
						
						  // AJAX 요청 보내기
						  $.ajax({
							type: 'POST',
							url: 'pointUp',
							//dataType: "application/JSON",
							data: {
							  paymentAmount: amount ,
							  paymentMethod: method ,
							  paymentThrough: through
							},
							success: function(response) {
							  // 요청 성공 시 수행할 동작
							  console.log('요청 성공:', response);
							},
							error: function(xhr, status, error) {
							  // 요청 실패 시 수행할 동작
							  console.log('요청 실패:', error);
							}
						  }); // ~ajax 끝
					  };
					
			}); // 포인트 충전 눌렀을 때 끝
		})
		
		
			
// 		Swal.fire({
// 			icon: 'question',
// 			title: '포인트 충전 금액을 선택하세요.',
// 			input: 'number',
// 			inputAttributes: {
// 				step: 1000
// 			},
// 			confirmButtonText: '확인',
// 			showCancelButton: true,
// 			cancelButtonText: '취소',
// 		});
		
// 		Swal.fire({
// 			icon: 'question',
// 			title: '결제 방법을 선택하세요.',
// 			input: 'select',
// 			inputOptions: {
// 				cards: '카드결제',
// 				trans: '계좌이체'
// 			},
// 			confirmButtonText: '확인',
// 			showCancelButton: true,
// 			cancelButtonText: '취소',
// 		});

// 		Swal.fire({
// 			icon: 'question',
// 			title: '결제 수단을 선택하세요.',
// 			input: 'select',
// 			inputOptions: {
// 				kakaopay: '카카오페이',
// 				tosspayments: '토스페이먼츠'
// 			},
// 			confirmButtonText: '확인',
// 			showCancelButton: true,
// 			cancelButtonText: '취소',
// 		});
		
	
	
})// ~~ end