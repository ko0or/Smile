$(document).ready(function(){
   
   // 채팅 말풍선 크기에 따라 보낸 시간이 어디에 뜨게할건지 정해주는 기능 (호이스팅)
   setSendTimePosition();   
   function setSendTimePosition() {		
		$(".sendtime1, .sendtime2").each(function() {
		  var prevHeight = parseInt($(this).prev().css("height"));
		  
		  if (prevHeight > 40) {
		    $(this).addClass("tall");
		  }
		});
	}
   
   // textarea 크기 자동 조절
   autosize($('textarea'));

   
   // 스크롤 가장 하단으로 이동하기
   $('.wrap').scrollTop($('.wrap')[0].scrollHeight);
   
   
   // 메시지 입력란에서 엔터키를 누르면 fn_submit() 함수를 호출하여 메시지 전송을 처리함
	$("#text1").keydown(function(event) {
	   if ((event.shiftKey || event.ctrlKey) && event.which === 13) { return; }
	   if (event.which === 13 && $("#text1").val().length > 0) { 
	   		$("#submitBtn").click(); 
	   }
	});
   
   // 메시지 입력란 옆 전송 버튼을 눌렀을때도 fn_submit() 함수를 호출하여 메시지 전송을 처리함
   $("#submitBtn").click(function() {
         if ( $("#text1").val().length > 0 ) {
         	fn_submit();
         	
		    setTimeout(function() {
		      $("#resetBtn").click();
		      autosize.update($("#text1"));
		    }, 100); // 채팅 비우기
		     
         }
   });
   
   
   // 작성된 채팅 내용을 전송하는 기능
   function fn_submit() {
      var formData = $("#frm").serialize();
      
      $.ajax({
         type:"post"
         ,data:formData
         ,url:"../chat/write"
      });
   }
   
   
   
   
   
   //==================================================================================================================== >>
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
           return '../resources/imgs/userDefaultIcon.png';
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




   //==================================================================================================================== >>
   // 프로필 사진 표시 ( 본인 및 상대 프로필 )
   
   //=> checkContent.jsp 파일의 script 태그 안의 변수들과 이어지는 내용 ( 모델객체를 js에서 바로 못쓰기때문에,  jsp 를 거쳐서 해당 js 파일로 끌고옴 )
   var myProfileImage = getProfilePath( myImgPath );
   var opponentProfileImage = getProfilePath( opponentImgPath );
   
   //=> 기존 채팅내용에 프로필 사진 입혀주기
   getProfileImage(myProfileImage, ".me-profile");
   getProfileImage(opponentProfileImage, ".opponent-profile");
            

   //==================================================================================================================== >>
   // 0.3초마다 새로고침 (새로운 채팅이 있는지 확인, 존재한다면 해당 내용만 추가까지)
   setInterval(function() {

      
      $.ajax({
         url : "newCheck" ,
         data : { 
               "count" : count ,
               "roomNum" : roomNum			    			
         } ,
         success : function ( dto ) {
         
         
            if ( dto != "" ) {
         		tradeStatus = dto.tradeStatus;
               
               //=> (중요) 카운트를 갱신해주고
               count = dto.count;		
               
               //=> 화면에 표시하기위해 필요한 내용들을 준비
               var row = ``;
               var sendtimeFormat = dto.sendtime.substring(14, 22);
               receiverIdentity = dto.receiver;
               
               //=> 새로운 채팅 메시지 받는사람이 나라면 ?
               if ( receiverIdentity == myIdentity  ) {
                  row =`
                     <div class="chat ch1">
                     <div>
                        <div class="opponent-profile"></div>
                        <div style="margin-left: 3px ">${dto.sender}</div>
                     </div>				     
                           <div class="textbox">${dto.msg}</div>
                           <div class="sendtime1">${sendtimeFormat}</div>
                        </div>
                  `;
               
               //=> 새로운 채팅 메시지 받는사람이 상대방이라면 ?
               } else {
                  row =`
                     <div class="chat ch2">							
                        <div>
                        <div class="me-profile"></div>
                        <div style="margin-left: 3px ">${dto.sender}</div>
                     </div>	
                           <div class="textbox">${dto.msg}</div>
                           <div class="sendtime2">${sendtimeFormat}</div>
                        </div>
                  `;
               } //~ if (메시지 받는 사람에 따라 분기처리)
               
               
               //=> 리스트의 가장 마지막 위치에 받아온 데이터를 추가
               $("#chat").append( row );
               

               //=> (추가된 데이터를 포함해서) 다시 프로필 사진을 입혀주기
               getProfileImage(opponentProfileImage, ".opponent-profile");
               getProfileImage(myProfileImage, ".me-profile");

               
            } //~ if
         } //~ success
      }) //~ ajax
      

      //=> 스크롤바 이벤트 및 필요한 정보들 ( 스크롤 바를 항상 아래로 )
      const $el = document.querySelector(".wrap");
      const eh = $el.clientHeight + $el.scrollTop;
      const isScroll = $el.scrollHeight <= eh+350;
      if (isScroll) {
         $el.scrollTop = $el.scrollHeight;				
      }

	  //=> 채팅 보낸 시간 포지션 자동으로 잡아주기
      setSendTimePosition();

      $.ajax({
      	url : "statusCheck",
      	data : { "roomNum" : roomNum },
      	success : function( newStatus ) {      
      		tradeStatus = newStatus;
      		tradeStatusShow(); 
  		}
      	
      })
      
      
   }, 300);



})//~ document.ready()