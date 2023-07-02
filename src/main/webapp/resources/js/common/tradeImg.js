/*

		#. 사용 방법 3단계
		
			 (1) 스크립트 태그 불러오기
				<script src="../resources/js/common/tradeImg.js"></script>
			
			(2) JSP 파일에서 모델을 이용하기 (유저DTO 에 있는 imgPath 값 변수로 받고)
				var jsp파일의변수명= "${userDto.imgPath}";
	
			(3) JS 파일로 가져와서 쓰기
	   			var 리턴값 = getProfilePath( jsp파일의변수명 );   
	   			getProfileImage(리턴값, "css선택자");
   
	



*/



   // 불러온 사진이 실제 표시되는지 여부를 TRUE / FALSE (BOOLEAN 타입)으로 반환하는 기능
   function checkImageExists(src, callback) {
        var img = new Image();
        img.src = src;
        
        img.onload = function() {
          callback(true);
        }
        
        img.onerror = function() {
          callback(false);
        }
        
   }
      
   
   // 유저DTO에서 꺼내온 imgPath 변수 값을 이용해서 프로필의 실제 경로를 가져오는 기능
   function getProfilePath(imgPath) {
      // user 테이블의 imgPath 컬럼 값이 null (기본값) 이라면  기본 프로필 사진을 보여주고,  null 이 아니라면 등록된 사진을 보여줌
       if ( imgPath == null || imgPath == "") {
           return '../resources/imgs/tradeDefaultPicture.png';
       } else {
           return urlConverter('user/display?fileName='+imgPath);
       }
   }
   
   
   
   // 프로필의 실제 경로에 파일을 보여줄 수 있는지 확인하고,  없다면 기본 프로필 이미지로 대체해주는 기능
   function getProfileImage(imgPath, target) {
   
      checkImageExists(imgPath, function(exists) {
         if (exists) {
            //alert("파일 찾았다 !");
            $(target).css("background-image", "url('"+imgPath+"')");
         } else {
            //alert("파일이 있긴한데.. 내 컴퓨터엔 없음");
            var defaultProfilePath = getProfilePath(null);
            $(target).css("background-image", "url('"+defaultProfilePath+"')");
         }
      });
	}
      
      
      
   // 중고 거래 게시판용
   function setContentImage(imgPath, target) {
      checkImageExists(imgPath, function(exists) {
         if (exists) {
            //alert("파일 찾았다 !");
            $(target).css("background-image", "url('"+imgPath+"')");
         } else {
            //alert("파일이 있긴한데.. 내 컴퓨터엔 없음");
            $(target).css("background-image", "url('../resources/imgs/tradeDefaultPicture.png')");
         }
      });
    }
    
    
    
      
   