$(document).ready(function () {

	// 써머노트 에디터 입력 폼에서 제목과 내용에 대한 유효성 검사  
	$("#writeOk").click(function() {
	
	
		if ( $(".note-placeholder").css("display") == "block" )   {
			Swal.fire({
  				icon: 'warning',
  				title: '필수 입력항목 체크',
  				text: '내용을 입력해주시기 바랍니다.',
			 	confirmButtonText: '확인'
			})
			
		} 
		
		if ( $("input[name=title]").val().length == 0 ) {
			Swal.fire({
				icon: 'warning',
				title: '필수 입력항목 체크',
				text: '제목을 입력해주시기 바랍니다.',
				confirmButtonText: '확인'
			}) 
			
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