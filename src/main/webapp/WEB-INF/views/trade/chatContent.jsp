<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
<style>
	.wrap {
	    padding: 10px 0;
	    background-color: #A8C0D6;
	    overflow-y: scroll;
	    height: 600px
	}
	
	.wrap .chat {
	    display: flex;
	    align-items: flex-start;
	    padding: 10px;
	}
	
	.wrap .chat .icon {
	    position: relative;
	    overflow: hidden;
	    width: 50px;
	    height: 50px;
	    border-radius: 50%;
	    background-color: #eee;
	}
	
	.wrap .chat .icon i {
	    position: absolute;
	    top: 10px;
	    left: 50%;
	    font-size: 2.5rem;
	    color: #aaa;
	    transform: translateX(-50%);
	}
	
	.wrap .chat .textbox {
	    position: relative;
	    display: inline-block;
	    max-width: calc(100% - 70px);
	    padding: 10px;
	    margin-top: 7px;
	    font-size: 13px;
	    border-radius: 10px;
	}
	
	.wrap .chat .textbox::before {
	    position: absolute;
	    display: block;
	    top: 0;
	    font-size: 1.5rem;
	}
	
	.wrap .ch1 .textbox {
	    margin-left: 20px;
	    background-color: #ddd;
	}
	
	.wrap .ch1 .textbox::before {
	    left: -15px;
	    content: "◀";
	    color: #ddd;
	}
	
	.wrap .ch2 {
	    flex-direction: row-reverse;
	}
	
	.wrap .ch2 .textbox {
	    margin-right: 20px;
	    background-color: #F9EB54;
	}
	
	.wrap .ch2 .textbox::before {
	    right: -15px;
	    content: "▶";
	    color: #F9EB54;
	}
</style>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<!-- <style>@import 'resources/css/main.css'</style>  -->
<section>
<h1 align="center">1:1 대화</h1>
	<div class="wrap">
		<div id="chattest">
			<c:forEach items="${list}" var = "dto">
				<c:choose>
					<c:when test="${user.identity == dto.receiver}">
						<div class="chat ch1">
						<div>
							<div class="icon"><i class="fa-solid fa-user"></i></div>
							<div style="margin-left: 3px ">${dto.sender}</div>
						</div>				     
				            <div class="textbox">${dto.msg}</div>
			   		  	</div>
					</c:when>
					<c:otherwise>
						<div class="chat ch2">							
			            <div>
							<div class="icon"><i class="fa-solid fa-user"></i></div>
							<div style="margin-left: 3px ">${dto.sender}</div>
						</div>	
				            <div class="textbox">${dto.msg}</div>
			       		</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</div>
	
	<div style="display: flex; justify-content: space-around; margin: 10px">
		<form id="frm" method="post" action="#" onsubmit="return false;">
			<input type="hidden" name="board" value="${room.board}">
			<input type="hidden" name="seller" value="${room.seller}">
			<input type="hidden" name="chattingroom" value="${room.identity}">
			<input type="hidden" name="sender" value="${user.nickname}">
			<c:choose>
				<c:when test="${user.identity == room.seller}">
					<input type="hidden" name="receiver" value="${room.buyer}">
				</c:when>
				<c:otherwise>
					<input type="hidden" name="receiver" value="${room.seller}">
				</c:otherwise>
			</c:choose>
			<p>메세지&nbsp;<input id="text1" type="text" name="msg"> <input type="reset" onclick="fn_submit()" value="입력"></p>
		</form>
	</div>
    
<a href="chatCreateTest">　</a>
&nbsp;<a href="../chatroom/chatRoomCreateTest">　</a>

<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<script>
$(document).ready(function() {
	autosize($('textarea'));
})// ~~ end
</script>

<script type="text/javascript">

		function fn_submit() {
			var formData = $("#frm").serialize();
			
			$.ajax({
				type:"post"
				,data:formData
				,url:"../chat/write"
			});
		}
		
		$("#text1").keyup(function(event) {
	        if (event.which === 13) {
	        	fn_submit();
	        	$("#text1").val("");
	        	$("#frm").reset();
	        }
	    });
		
		$("#text2").keyup(function(event) {
	        if (event.which === 13) {
	        	fn_submit2();
	        	$("#text2").val("");
	        	$("#frm2").reset();
	        }
	    });
		
		$('.wrap').scrollTop($('.wrap')[0].scrollHeight);
		
		$(document).ready(function(){
			setInterval(function() {
				const $el = document.querySelector(".wrap");
				const eh = $el.clientHeight + $el.scrollTop;
			    const isScroll = $el.scrollHeight <= eh+150;
			    $("#chattest").load(window.location + ' #chattest');
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
		
</script>

</html>