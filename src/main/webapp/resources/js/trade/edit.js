$(document).ready(function() {

	// 비대면 결제여부 스위칭 (분기처리: 주소선택하기 화면 슬라이딩)	  ========================================================== >>
	$("#flexSwitchCheckDefault").change(function() {
		if ($(this).is(":checked")) {
			$(".contected").slideUp(500);
		} else {
			$(".contected").slideDown(500);
		}
	});
	
	
	// ★ 물품 등록하기 버튼 눌렀을때 ( 가장 하단에 있는 작성 버튼 )  ========================================================== >>
	$("#writeBtn").click(function() {
		
		// 만약 비대면 결제여부가 off 이면서,  어디서 만날지 안정했다면 ?
		if ($("#flexSwitchCheckDefault").prop("checked") == false && $("#address").val().length == 0) {
			
			Swal.fire({
				title : '등록 실패',
				icon : 'warning',
				html : `거래하실 위치를 선택해주시거나<br>
				혹은 비대면 결제로 변경해주세요.`,
				confirmButtonText : '확인',
			})
			
		} else {
			$("#tradeForm").submit();			
		}		
	}) // ~ 버튼 이벤트 끝 !


	// ★ 등록된 사진 보기 버튼 눌렀을때 ( 가장 상단에 있는 버튼 )  ========================================================== >>	
	$(".showImg").click(function() {

		Swal.fire({

			icon: 'success',
			title: '등록된 사진 보기',
			html: `<div class="picture" style="width: 100%;    height: 400px; background-repeat: no-repeat;    background-size: contain; background-position: center;">`,
			width: 900 ,
			showCancelButton: false,
			confirmButtonText: '확인'
		})
		
        var setComtentProfilePath = getProfilePath( $(this).attr("id") );   
        var setComtentProfileTargetClass = ".picture";
        getProfileImage(setComtentProfilePath, setComtentProfileTargetClass);

	}) // ~ 사진 보여주기 버튼 이벤트 끝 -!
	
})// ~~ end
