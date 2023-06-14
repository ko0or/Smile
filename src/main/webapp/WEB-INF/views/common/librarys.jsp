<%@ page session="false" pageEncoding="UTF-8" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>싱글? 싱글벙글!</title>
<!-- 파비콘: https://formito.com/tools/favicon -->
<!-- <link rel="icon" type="image/svg+xml" href="../resources/imgs/favicon.svg" /> -->
<link rel="icon" type="image/svg+xml" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 width=%22256%22 height=%22256%22 viewBox=%220 0 100 100%22><text x=%2250%%22 y=%2250%%22 dominant-baseline=%22central%22 text-anchor=%22middle%22 font-size=%2270%22>🙂</text></svg>" />

<!-- 제이쿼리  -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.0/jquery.min.js" integrity="sha512-3gJwYpMe3QewGELv8k/BX9vcqhryRdzRMxVfq6ngyWXwo03GFEzjsUm8Q7RZcHPHksttq7/GFoxjCVUjkjvPdw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<!--  폰트어썸 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<!--  부트스트랩 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<!--  애니메이트 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" integrity="sha512-c42qTSw/wPZ3/5LBzD+Bw5f7bSF2oxou6wEb+I/lqeaKV5FDIfMvvRp772y4jcJLKuGUOpbJMdg/BTl50fJYAw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<!-- 스윗모달 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<!--  오토 사이즈 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/autosize.js/6.0.1/autosize.min.js" integrity="sha512-OjjaC+tijryqhyPqy7jWSPCRj7fcosu1zreTX1k+OWSwu6uSqLLQ2kxaqL9UpR7xFaPsCwhMf1bQABw2rCxMbg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  <!-- 써머노트 (위지위그) -->
<link rel="stylesheet" href="../resources/summerNote/summernote-lite.min.css" />
<script src="../resources/summerNote/summernote-lite.min.js"></script>
<script src="../resources/summerNote/lang/summernote-ko-KR.min.js"></script>
<script src="../resources/js/common/board.js"></script>
  
  <!-- 전역 CSS 설정 -->
<style>
* {
  		margin: 0px;
  		padding: 0px;
  		text-decoration: none;  	
  		list-style: none; 		
  }
a { 
	color: black; 
	text-decoration: none;
}
section { 
	padding: 10px;
	width: 100%;  
	max-width: 960px; 
	min-height: 100vh; 
	margin: 100px auto; 
}

.profileImageIcon {
	
    width: 35px;
    height: 35px;
    background-size: contain;

    border: 2px solid white;
    border-radius: 25px;
	margin-right: 15px;
	
}

/*
body{ -ms-overflow-style: none; } 
::-webkit-scrollbar { display: none; }
*/
</style>
