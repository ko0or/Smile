$(document).ready(function() {

      
      
   // ======================================================================================================================= >
   // callComments 함수에
         function callComments() {
         
            $.ajax({
               
               //read에 identityurl을 값을 가지고 와서 보여줌 = boardIdentity( jsp파일에서 만들었던 변수)
               //url이 read인 이유는 댓글을 달아도 그자리에 있어서
               url : "comment/read?identity="+boardIdentity ,
               method : "GET" ,
               success : function( data ) {
                  
                  console.log( "댓글갯수 => " + data.length );			
                  var row = `<h2>댓글 ${data.length}개</h2>`;
                              
                  
                  for (var i=0; i < data.length; i++) {
               
                     //row에 밑에서 썻던 getComponent 함수에 data의 값을 하나씩 추가해서 넣어 준다 
                     
                     //로그인한 유저 :userIdentity
                     //댓글작성자 번호 :data[i].user(data[i] =>댓글들 user => 작성자)
   
                     
                     //지금 로그인한 유저와 댓글 작성자가 같냐
                     if ( userIdentity == data[i].user ) {
   
                     //그럼 row에 그 댓글에 수정/삭제를 보여줘라
                        row += getComponent(data[i], "block");
                     } else {
   
                     //아니면 없에라
                        row += getComponent(data[i], "none");
                     }
                  }
                  
                  // 밑에 댓글 작성란 row에 넣어준다 왜냐하면 row에 data의 값을 하나씩 넣어 주기 때문에 댓글도 하나씩 넣어 줘야 한다.
                  row += `
                  <div style="display: flex; justify-content: space-between; width: 100%; margin-top: 50px;">
                     <div class="form-floating" style="flex: 11;">
                        <textarea placeholder="댓글작성란" class="form-control" id="InputComment" style="max-height: 200px;"></textarea>
                        <label for="InputComment" class="form-label">댓글 작성</label>
                     </div>
                     
                     <!-- 댓글 작성버튼 -->
                       <button id="commentWrtieBtn" class="btn btn-primary" style="flex: 1; margin: 0 10px"><i class="fa-solid fa-check"></i>
                       </button>
                    </div>
                  `;
               
                  //html==append랑 같은건데 밑에 getComponent 함수의 모든 div값을 넣은 것 이다
                  $(".content-footer").html( row );


                  // 댓글 프로필 사진 표시
                  for (var c = 0; c < data.length; c ++) {
                    var setProfileTargetClass = ".profileImg" + data[c].identity;
                    var setProfilePath = getProfilePath( data[c].imgPath );   
                    getProfileImage(setProfilePath, setProfileTargetClass);
                  }

   
                  
                  // 댓글 수정버튼 눌렀을때 작동할 내용
                  $(".comment-modify").click(function() {
                  //내 자신".comment-modify"text가 indexOf(인덱스)-1보다 큰것이 수정이냐
                  if($(this).text().indexOf("수정")>-1){
                  
                  //addClass("form-control"):아웃라인을 만들어라
                  //.comment =>댓글
                  $("#commentIdentity" + $(this).attr("id")).find("textarea").addClass("form-control");
                  
                   //prop("readonly", false) :읽기 전용을 없에라
                  $("#commentIdentity" + $(this).attr("id")).find("textarea").prop("readonly", false);
                       
                       $("#commentIdentity" + $(this).attr("id")).find("textarea").css({"border" : "1px solid #ccc" , "outline" : "initial", "cursor" : "text"});
   
                  //내 자신의 text(타입)에 저장을 만들어라
                  $(this).text("저장");
                  
                  //그 다음의 text(타입)에 취소를 만들어라
                        $(this).next().text("취소");
                        
                        //내 자신".comment-edit"text가 indexOf(인덱스)-1보다 큰것이 삭제이냐
                  } else if ( $(this).text().indexOf("저장") > -1 ) {
               
                     var identity = $(this).attr("id");
                     
                     
                     $.ajax({
                     
                        url : "comment/edit?identity="+identity ,
                        method : "POST" ,
                        data :{
                        "identity" : $(this).attr("id") ,
                        //commentIdentity 가지고 있는 자신.수정버튼이 가지고 있는 id(한마디로 pk값)+" 자식요소가textarea"인=>그래서 앞에 한칸을 띄움 값을 content에 넣어라.
                        "content"  : $("#commentIdentity" + $(this).attr("id")).find("textarea").val()
                        }
                        ,success : function() {
                           callComments();
                        }
                     }) // ~ ajax 끝 !
                     }
                  }) // ~ 댓글 수정버튼 이벤트 끝 !
                  
   
   
                  
                  
                  $(".comment-delete").click(function() {
   
                     // 댓글 삭제버튼 눌렀을때 작동할 내용 attr("id") => id의 속성 값을 가지고 오는 거임
                     var identity = $(this).attr("id");
                     if($(this).text().indexOf("취소")>-1){
                     
                           callComments();
                     }else{ $.ajax({
   
                     //url이 delete인이유는 컨트롤러에 있는 매칭되는 delete를 실행시키는거임 하지만 ajax이기때문에 눈에 보이진 않는 거임
                        url : "comment/delete?identity="+identity ,
                        method : "GET" ,
                        
                        success : function() {
   
                           callComments();
   
                                 // 댓글 삭제에 성공했으면, 변경된 내용을 다시 받아와서 화면에 표시함
                        }
                     }) // ~ ajax 끝 !
                     }
                                       
                  }) // ~ 댓글 삭제버튼 이벤트 끝 !
   
                  
   
   
   
                  // ★ 댓글 작성버튼 눌렀을때 (AJAX)
                  $("#commentWrtieBtn").click(function (){
                     
                           $.ajax({
   
                              //url이 write인이유는 컨트롤러에 있는 매칭되는 write를 실행시키는거임 하지만 ajax이기때문에 눈에 보이진 않는 거임
                              url : "comment/write?board=" + boardIdentity
                              , type : "POST"
                              
                              //val은 input타입에 입력되어 있는 값을 사용할때(뭐라 적혀있는 지 알고 싶을 때)
                              , data : { "content" : $("#InputComment").val() }
                              
                              , success: function() {
   
                                 // 댓글 작성에 성공했으면, 변경된 내용을 다시 받아와서 화면에 표시함
                                 callComments();
                              }
                           })
                           
                     
                  }) // ~ 댓글 작성버튼 이벤트 끝 !
   
                  
   
   
   
   
                  //=> 답글 버튼 이벤트 등록 (답글 작성 폼 보여주기)
                  $(".comment-reply").off("click");

                  //답글버튼을 눌렀을때
                  $(".comment-reply").click(function() {

                     //만약 답글버튼을 두개 눌럿으면 전에 누른건 사라지게 하기 위해서 remove시킴
                        $(".reply-wrapper").remove();

                        //commentIdentity에 답글 버튼에 있는 identity값을 넣어줌
                        var commentIdentity = $(this).attr("id");

                        $(this).parent().append(`
                        
                           <div class="reply-wrapper">
                              <textarea name="reply" class="form form-control" style="    margin-top: 30px; margin-bottom: 15px;"></textarea>
                              <button id="${commentIdentity}" class="reply-write" style="border: none; background: white; color: gray;">
                                 완료</button>
                              <button class="reply-cancel" style="border: none; background: white; color: gray;">
                                 취소</button>
                           </div>
                        
                        `);
   
                        //=> 댓글 입력란 이벤트 등록(자동 높이 조절)
                        autosize($('textarea'));
                        // $(this).attr("id") => $(".comment-reply") 이게 this가 comment-reply(답글버튼)임 그리고 거기에 id값이 identity임 그걸 identity에 넣어 다시 가지고 옴 
                        var identity = $(this).attr("id");
   
                        //=> 이벤트 등록 (답글 작성)
                        $(".reply-write").off("click");
                        //완료버튼을 눌럿을때
                        $(".reply-write").click(function() {
                           
                           $.ajax({
                           //이 identity는 완료버튼의 id 값임
                           url:"comment/write?identity="+identity
                           ,type :"POST"
                           //data는 컨트롤러 단에 전송 하려고 하는 거임
                           //content는 완료버튼의 이전(textarea)의 값 
                           //identity는  완료 버튼에 identity 결국 답글 버튼에 identity랑 같은 거임 왜냐하면 내가 답글달기를 누르고 답글을 단 뒤에 완료버튼을 누를꺼기 떄문에
                           //board는 게시글에 있는 identity값
                           ,data :{ "content" : $(this).prev().val(),"identity": $(this).attr("id"), "board":boardIdentity   }
                           ,success : function( ) {
                               callComments();
                           }
                        }) // ~~ajax 끝   
                     })
                     
                     
                     
                           
                        //=> 이벤트 등록 (답글 취소)
                        $(".reply-cancel").off("click");
                        //취소버튼을 눌렀을떄
                        $(".reply-cancel").click(function() {
                           //취소버튼의 부모(div에"reply-wrapper")의 값을 없엔다
                           $(this).parent().remove();
                        })
                     }) // ~~ (답글 작성 폼 보여주기)
   
   
                  // ★ 댓글입력화면 높이조절
                  autosize($("textarea"));		
                  $("textarea").on("input keydwon keyup", function() {			
                     if ( $("#InputComment").height() > 160 ) {
                        $(".form-floating .form-label").hide();
                     } else {
                        $(".form-floating .form-label").show();
                     }
                  }) // ~ 댓글 입력화면 자동 높이조절 끝 !
                  
               }
            })		
         
         }
         
         
   // ======================================================================================================================= >		
   
   //	댓글 =>getComponent 함수에 data 매게변수를 넣어줌
   //	show=>위에서 받은 display가 block 또는 none임
         function getComponent( data, show ) {
            
            var row = ``;
            
            
            //data.index == 0 는 대댓글이 없는거임 그래서 삼항연산자를 사용하여 참(대댓글이 없는)일경우 0px 거짓(대댓글이 있는)일 경우 50px로 들여쓰기
            var marginLeftSet = ( data.index == 0 ) ? "0px" : "50px"; 
            
            //마찬 가지로 data.index == 0 는 대댓글이 없는거임 그래서 삼항연산자를 사용하여 참(대댓글이 없는)일경우 아무것도 없고 
            //거짓(대댓글이 있는)일 경우 해당 닉네임 xml에서 엘리야스로 만든것(dto에 넣어줌)을 넣어줌
            var targetUserNickname = ( data.index == 0 ) ? "" : data.target_user_nickname + "님에게 답글" ;


            //row에 저 변수를 넣어 줫으니  거기에 값을 더해서 넣어준다
            //style="margin-left: ${marginLeftSet}" 삼항연산자 변수로 받은 아이            
            row +=  `
            <div id="commentIdentity${data.identity}" style="margin-left: ${marginLeftSet}" class="comment-wrapper mt-5">
            <div class="comment">
               <div class="comment-header" style="display: flex;">
                  <div class="profileImageIcon profileImg${data.identity}" style="	background-size: cover; background-position: center; position: relative; bottom: 9px; box-shadow: 0px 0px 5px rgba(0,0,0,0.15);"></div>
                  <div class="profileInfo">
                        <h4 style="display: inline;">${data.nickname}</h4>
                        <sub style="display: inline; color: grey;">${data.created}</sub>
                  </div>
               </div>
               
               <small style="color: silver;">${targetUserNickname}</small>
               <textarea
                  id = "${data.identity}" 
                  class = "modify-textarea"  
                  readonly 
                  style="margin: 20px 0; border:none; outline:none; display: block; cursor: default; width: 100%" >${data.content}</textarea>
                  <div class="comment-footer">
               `;	
               
               if ( userIdentity == -1) {
                  // 비 로그인 상태일때
   
   
               } else if ( userIdentity == data.user ) {
                  // 로그인 + 작성자 본인일때
                  row += `
                        <button type="button" id="${data.identity}" class="comment-modify">
                        수정</button>
                        <button type="button" id="${data.identity}" class="comment-delete">
                        삭제</button>
                        <button id="${data.identity}" class="comment-reply" style="border:none;background:white;color:grey;">
                        답글</button>
                  `;
   
               } else {
                  // 로그인 + 작성자가 아닐때
                  row += `
                     <button id="${data.identity}" class="comment-reply" style="border:none;background:white;color:grey;">
                     답글</button>
                  `;
   
               }
   
   
   
   
                  row += `</div></div></div>`;
                  return row;
         }	
         
   
   
   // ======================================================================================================================= >
   
      // ★ : 페이지 로딩되면 최초 1회 댓글목록 불러오기 ( 이후 댓글 작성버튼 눌러서 추가된 이후에도 다시 호출해서 변경내용 보여줘야함 )
      callComments();
   
   })// ~~ 자바스크립트 끝남