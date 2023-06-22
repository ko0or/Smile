/* =========================================================================================


 	#. 사용하는 곳에서 아래 스크립트 태그도 함께 사용해야함
		<script src="https://cdn.iamport.kr/v1/iamport.js"></script>



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
  		
  		
  	
  	
  
 */