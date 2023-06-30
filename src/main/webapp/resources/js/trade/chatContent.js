	// user 테이블의 imgPath 컬럼 값이 null (기본값) 이라면  기본 프로필 사진을 보여주고,  null 이 아니라면 등록된 사진을 보여줌
	function myProfile(imgPath) {
	    if ( imgPath == null || imgPath == "") {
	        return '../resources/imgs/userDefaultIcon.png';
	    } else {
	        return urlConverter('user/display?fileName='+imgPath);
	    }
	}
	
	
	function fn_submit() {
		var formData = $("#frm").serialize();
		
		$.ajax({
			type:"post"
			,data:formData
			,url:"../chat/write"
		});
	}
	
	
	
		
	function checkImageExists(src, callback) {
		  var img = new Image();
		  
		  img.onload = function() {
		    callback(true);
		  }
		  
		  img.onerror = function() {
		    callback(false);
		  }
		  
		  img.src = src;
	}
	
	
	
	
	
	
	
//==================================================================================================================== >>
	
	
	
	
	
	
	
$(document).ready(function(){

	var buyProfile = myProfile( buyerImgPath );
	var sellerProfile = myProfile( sellerImgPath );
	
	
	
	$("#text1").keyup(function(event) {
	       if (event.which === 13) {
	       	fn_submit();
	       	$("#text1").val("");
	       	$("#frm").reset();
	       }
	   });
	
	$('.wrap').scrollTop($('.wrap')[0].scrollHeight);
	
	
	
	


//==================================================================================================================== >>


			
			// var setProfileImg = myProfile(data.imgPath);
			// <div class="profileImageIcon" style="background-image: url('${setProfileImg}');"></div>

			// var list = ${list}; // 대화내용들
			

			 
			checkImageExists(buyProfile, function(exists) {
			  if (exists) {
				  if(userIdentity == receiverIdentity) {
						$(".opponent-profile").css("background-image", "url('"+buyProfile+"')");
						$(".me-profile").css("background-image", "url('"+sellerProfile+"')");			
					} else {
						$(".opponent-profile").css("background-image", "url('"+sellerProfile+"')");
						$(".me-profile").css("background-image", "url('"+buyProfile+"')");			
					}
			  } else {
				  if(userIdentity == receiverIdentity) {
						$(".opponent-profile").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");
						$(".me-profile").css("background-image", "url('"+sellerProfile+"')");			
					} else {
						$(".opponent-profile").css("background-image", "url('"+sellerProfile+"')");
						$(".me-profile").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");			
					}
			  }
			});
			
			checkImageExists(sellerProfile, function(exists) {
			  if (exists) {
				  if(userIdentity == receiverIdentity) {
						$(".opponent-profile").css("background-image", "url('"+buyProfile+"')");
						$(".me-profile").css("background-image", "url('"+sellerProfile+"')");			
					} else {
						$(".opponent-profile").css("background-image", "url('"+sellerProfile+"')");
						$(".me-profile").css("background-image", "url('"+buyProfile+"')");			
					}
			  } else {
				  if(userIdentity == receiverIdentity) {
						$(".opponent-profile").css("background-image", "url('"+buyProfile+"')");
						$(".me-profile").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");			
					} else {
						$(".opponent-profile").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");
						$(".me-profile").css("background-image", "url('"+buyProfile+"')");			
					}
			  }
			});
			
			if(userIdentity == receiverIdentity) {
				$(".opponent-profile").css("background-image", "url('"+buyProfile+"')");
				$(".me-profile").css("background-image", "url('"+sellerProfile+"')");			
			} else {
				$(".opponent-profile").css("background-image", "url('"+sellerProfile+"')");
				$(".me-profile").css("background-image", "url('"+buyProfile+"')");			
			}
						
			
			
			
			
			setInterval(function() {
				const $el = document.querySelector(".wrap");
				const eh = $el.clientHeight + $el.scrollTop;
			    const isScroll = $el.scrollHeight <= eh+150;
			    
			    $.ajax({
			    	url : "newCheck" ,
			    	data : { 
			    			"count" : count ,
			    			"roomNum" : roomNum			    			
		    		} ,
			    	method : "GET" ,
			    	success : function ( dto ) {
			    		if ( dto != "" ) {
			    			
			    			//=> 카운트를 갱신해주고
			    			count = dto.count;		
			    			
			    			//=> 필요한 내용을 준비
			    			var row = ``;
			    			var sendtimeFormat = dto.sendtime.substring(14, 22);
			    			receiverIdentity = dto.receiver;
			    			
			    			if ( receiverIdentity == userIdentity ) {
			    				//=> 동일하다면 ..
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
			    			} else {
			    				//=> 아니라면 ..
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
			    			}		    			
			    			
			    			
			    			//=> 리스트의 가장 마지막 위치에 받아온 데이터를 추가
			    			$("#chat").append( row );
			    			

			checkImageExists(buyProfile, function(exists) {
			  if (exists) {
				  if(userIdentity == receiverIdentity) {
						$(".opponent-profile").css("background-image", "url('"+buyProfile+"')");
						$(".me-profile").css("background-image", "url('"+sellerProfile+"')");			
					} else {
						$(".opponent-profile").css("background-image", "url('"+sellerProfile+"')");
						$(".me-profile").css("background-image", "url('"+buyProfile+"')");			
					}
			  } else {
				  if(userIdentity == receiverIdentity) {
						$(".opponent-profile").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");
						$(".me-profile").css("background-image", "url('"+sellerProfile+"')");			
					} else {
						$(".opponent-profile").css("background-image", "url('"+sellerProfile+"')");
						$(".me-profile").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");			
					}
			  }
			});
			
			checkImageExists(sellerProfile, function(exists) {
			  if (exists) {
				  if(userIdentity == receiverIdentity) {
						$(".opponent-profile").css("background-image", "url('"+buyProfile+"')");
						$(".me-profile").css("background-image", "url('"+sellerProfile+"')");			
					} else {
						$(".opponent-profile").css("background-image", "url('"+sellerProfile+"')");
						$(".me-profile").css("background-image", "url('"+buyProfile+"')");			
					}
			  } else {
				  if(userIdentity == receiverIdentity) {
						$(".opponent-profile").css("background-image", "url('"+buyProfile+"')");
						$(".me-profile").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");			
					} else {
						$(".opponent-profile").css("background-image", "url('../resources/imgs/userDefaultIcon.png')");
						$(".me-profile").css("background-image", "url('"+buyProfile+"')");			
					}
			  }
			});
			
			if(userIdentity == receiverIdentity) {
				$(".opponent-profile").css("background-image", "url('"+buyProfile+"')");
				$(".me-profile").css("background-image", "url('"+sellerProfile+"')");			
			} else {
				$(".opponent-profile").css("background-image", "url('"+sellerProfile+"')");
				$(".me-profile").css("background-image", "url('"+buyProfile+"')");			
			}





			    			
			    		} //~ if
			    	} //~ success
			    }) //~ ajax
			    
// 			    $("#chat").load(window.location + ' #chat');
			    if (isScroll) {
			      $el.scrollTop = $el.scrollHeight;				
				}
			}, 300);
			$("#text1").keyup(function(event) {
		        if (event.which === 13) {
		        	fn_submit();
		        }
		    });
		})