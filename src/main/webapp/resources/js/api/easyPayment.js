/* =========================================================================================


 	#. 아래와 같이 스크립트 태그 2개 넣어주기 
		<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
		<script src="../resources/js/api/easyPayment.js"></script>


	#. 사용 에시는 맨 아래 적어둠 : )

========================================================================================= */		


IMP.init("imp80056106"); 
function requestPay(orderId, payType, pgValue, buyerEmailAddr, amount) {

    IMP.request_pay({
        pg : pgValue, 
        pay_method : payType, 
        merchant_uid: "order_id_" + orderId,  
        name : '[싱글벙글] 포인트 충전', 
        amount : amount, // => 가격: 원 단위
        buyer_email : buyerEmailAddr,
        
        // 불필요한건 주석 처리했음
        //buyer_name : '구매자 닉네임',
        //buyer_tel : '연락처',
        //buyer_addr : '주소',
        //buyer_postcode : '우편번호'

    }, function (rsp) { // callback
        
        if (rsp.success) {
            console.log('결제성공');
            console.log(rsp.imp_uid);
            console.log(amount);
			
						  // AJAX 요청 보내기
						  $.ajax({
							type: 'POST',
							url: 'pointUp',
							data: { 
							  amount: amount
							},
							success: function(response) {
							  // 요청 성공 시 수행할 동작
							  console.log('요청 성공:', response);
							  // 일단 바로 보이는 화면에서도 수정사항 반영되게 => 그 이후 새로고침(F5) 해도 세션으로 반영되어 있음!
							  $("#floatingPoint").html(`<i class="fa-solid fa-circle-dollar-to-slot"></i> 거래 관련 ( 보유 포인트 <b>${response}원</b> ) `);
							   
							},
							error: function(xhr, status, error) {
							  // 요청 실패 시 수행할 동작
							  console.log('요청 실패:', error);
							}
						  }); // ~ajax 끝
						  
        } else {
            console.log('결제시도했었음');
            console.log(rsp);
        }
    });
}



/*  


	
	
	#. 간단 설명
  		pay_method : card(카드), trans(계좌이체)
  		pgValue : kakaopay 혹은 tosspayments 사용하기 
  		order_id_ 는 필수로 붙어있어야되고, 뒤에 랜덤한 숫자가 필요! (해당 uid로 이미 결제된 적 있으면 결제창안뜸)

  	
  	#. 메소드 매개변수
  		requestPay(랜덤숫자번호, 결제방법(카드or계좌이체), 결제사(카카오페이or토스), 구매자이멜주소, 결제금액);
  		
  	
  	#. 결제 방법  	
  		카드 : card
  		계좌이체 : trans
  		
  		
  	#. 결제사
  		카카오 : kakaopay
  		토스 : tosspayments
  		
  	
  	#. 예시
  	
  	function requestPay(orderId, payType, pgValue, buyerEmailAddr, amount)
  	
  	function requestPay(중고거래dto에있는PK값, 'card', 'tosspayments', 유저dto에있는이멜주소, 중고거래dto에있는price가격)
  	function requestPay(중고거래dto에있는PK값, 'trans', 'tosspayments', 유저dto에있는이멜주소, 중고거래dto에있는price가격)
  	
  	  	
  	function requestPay(중고거래dto에있는PK값, 'card', 'kakaopay', 유저dto에있는이멜주소, 중고거래dto에있는price가격)
  	function requestPay(중고거래dto에있는PK값, 'trans', 'kakaopay', 유저dto에있는이멜주소, 중고거래dto에있는price가격)
  
 */