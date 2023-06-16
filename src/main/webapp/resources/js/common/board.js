$(document).ready(function () {

	// 메인(피드) 게시판  ======================================================================================== >
	$("#writeFeed").click(function() {
	
	
		if ( $(".note-placeholder").css("display") == "block" )   {
			Swal.fire({
  				icon: 'warning',
  				title: '필수 입력항목 체크',
  				text: '내용을 입력해주시기 바랍니다.',
			 	confirmButtonText: '확인'
			})
			
		}  else {
		
			$("body > section > form").submit();
		
		}
	
	});
	
	
	
	
	// 공지사항 게시판  ======================================================================================== >
	$("#writeOk").click(function() {
	
	
		if ( $(".note-placeholder").css("display") == "block" )   {
			Swal.fire({
  				icon: 'warning',
  				title: '필수 입력항목 체크',
  				text: '내용을 입력해주시기 바랍니다.',
			 	confirmButtonText: '확인'
			})
			
		} else 	if ( $("input[name=title]").val().length == 0 ) {
			Swal.fire({
				icon: 'warning',
				title: '필수 입력항목 체크',
				text: '제목을 입력해주시기 바랍니다.',
				confirmButtonText: '확인'
			}) 
			
		}  else {
		
			$("body > section > form").submit();
		
		}
	
	});

	$('#summernote').summernote({
	
		codemirror: {
	    	mode: 'text/html',
	    	htmlMode: false,
	    	lineNumbers: false,
	    	theme: 'monokai'
	  	},
	
		lang: "ko-KR",
		placeholder: '이곳에 내용을 입력해주세요 :)',
	    height: 300,
	    focus: false,
		 toolbar: [
			    ['fontname', ['fontname']],
			    ['fontsize', ['fontsize']],
			    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
			    ['color', ['forecolor','color']],
			    ['para', ['ul', 'ol', 'paragraph']],
			    ['height', ['height']],
			    ['insert',['picture','link','video']],
			    ['view', ['codeview', 'help']]
		 ],
		
		fontNames: ['맑은 고딕', '굴림','돋움체','바탕체'],
		fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']  		
	    	
	});
	
})