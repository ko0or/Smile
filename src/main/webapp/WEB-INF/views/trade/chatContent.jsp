<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html>
<head>
	<%@ include file="../common/librarys.jsp" %>
</head>
<body>
<%@ include file="../common/navbar.jsp" %>
<!-- -------------------------------------------------------------------------- -->
<style>@import '../resources/css/trade/chatContent.css'</style> 
<section>
<h1 align="center">1:1 대화</h1>
<div class="tradeInfo">
	<div class="left">
		<h4>진행 상태 : 
			<span class="wait" style="color: black;">거래 대기중</span>
			- <span class="request" style="color: grey;">구매 요청</span>
			- <span class="proceeding" style="color: grey;">거래 진행중</span>
			- <span class="confirmed" style="color: grey;">거래 완료</span>	
		</h4>
	</div>

	<div class="right">
		<button id="next" class="btn btn-secondary" disabled>다음 단계로</button>
	</div>
</div>

<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="nowDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/></c:set>
	<div class="wrap">
		<div id="chat">
			<c:forEach items="${list}" var = "dto">
				<fmt:parseDate value="${dto.sendtime}" var="date" pattern="yyyy-MM-dd"/>
				<c:set var="date2"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/></c:set>
				<c:choose>
					<c:when test="${nowDate != date2}">
						<c:set var="nowDate" value="${date2}"></c:set>
							<h6 align="center">${fn:substring(dto.sendtime, 0, 13)}</h6>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${user.identity == dto.receiver}">   
						<div class="chat ch1">
						<div>
							<div class="opponent-profile"></div>
							<div style="margin-left: 3px ">${dto.sender}</div>
						</div>				     
				            <div class="textbox">${dto.msg}</div>
				            <div class="sendtime1">${fn:substring(dto.sendtime, 14, 22)}</div>
			   		  	</div>
					</c:when>
					<c:otherwise>
						<div class="chat ch2">							
			            <div>
							<div class="me-profile"></div>
							<div style="margin-left: 3px ">${dto.sender}</div>
						</div>	
				            <div class="textbox">${dto.msg}</div>
				            <div class="sendtime2">${fn:substring(dto.sendtime, 14, 22)}</div>
			       		</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</div>
	
	<div style="display: flex; justify-content: space-around; margin: 10px 0; width: 100%;">
		<form id="frm" method="post" action="#" onsubmit="return false;">
			<input type="hidden" name="board" value="${room.board}">
			<input type="hidden" name="seller" value="${room.seller}">
			<input type="hidden" name="chattingroom" value="${room.identity}">
			<input type="hidden" name="sender" value="${user.nickname}">
			<input type="hidden" name="senderIdentity" value="${user.identity}">
			<c:choose>
				<c:when test="${user.identity == room.seller}">
					<input type="hidden" name="receiver" value="${room.buyer}">
				</c:when>
				<c:otherwise>
					<input type="hidden" name="receiver" value="${room.seller}">
				</c:otherwise>
			</c:choose>
			
			<div class="input-area">
				<textarea id="text1" name="msg" class="form form-control" placeholder="전송: Enter &#13;&#10;줄바꿈: Shift + Enter"></textarea> 
				<button id="submitBtn" class="btn btn-primary"><i class="fa-regular fa-message"></i> </button>
				<button id="resetBtn" type="reset" style="display: none;"></button>
			</div>
<!-- 			<p>메세지&nbsp;<input id="text1" type="text" name="msg"> <input type="reset" id="submitBtn" value="입력"></p> -->
		</form>
	</div>
    
<a href="chatCreateTest">　</a>
&nbsp;<a href="../chatroom/chatRoomCreateTest">　</a>

<!-- -------------------------------------------------------------------------- -->
</section>
<%@ include file="../common/footer.jsp" %>
</body>
<script>

	// 대화중인 채팅방 정보
	var count = ${count};
	var roomNum = ${room.identity};
	var tradeBoardIdentity = ${room.board};
	var tradeStatus = "${tradeStatus}";

	
	// (우측)에는 로그인한 유저의 프로필을 보여주고
	var myIdentity = ${user.identity};
	var myImgPath = "${user.imgPath}";
	var buyerIdentity = ${room.buyer};
	var sellerIdentity = ${room.seller};
	
	// (좌측)에는 내 정보와 다른 유저 프로필 보여주기
	var opponentIdentity;
	var opponentImgPath;
	if ( myIdentity != ${room.buyer} ) { // 내가 구매자가 아니라면(판매자일땐),  왼쪽에 구매자 프로필 표시
		opponentIdentity = ${room.buyer}; 
		opponentImgPath = "${buyerImgPath}";
	} else {
		opponentIdentity = ${room.seller};// 내가 구매자라면, 왼쪽에 판매자 프로필 표시
		opponentImgPath = "${sellerImgPath}";
	}

	
	// 타이머에서 0.3초마다 최신 status 를 받아오면서, 아래 함수를 호출함 (status 상태에 따른 화면 표시)
	function tradeStatusShow() {			
		
		//=> 진행 단계 텍스트 색상 표시
		$(".wait, .request, .proceeding, .confirmed").css("color", "grey");
		if ( tradeStatus === "wait" ) { $(".wait").css("color", "black"); } 
		else if ( tradeStatus === "request" ) { $(".request").css("color", "black"); } 
		else if ( tradeStatus === "proceeding" ) { $(".proceeding").css("color", "black"); } 
		else if ( tradeStatus === "confirmed" ) { $(".confirmed").css("color", "black"); } 
		
		//=> 버튼 클릭 가능 여부 설정
		if ( tradeStatus === "wait" && (myIdentity == buyerIdentity)) {
			// 진행 단계가 "거래 대기중" 이면서,  내가 구매자일경우  다음 단계 버튼을 보여줌 ( 판매자는 x )
			$("#next").removeClass("btn-secondary");
			$("#next").addClass("btn-primary");
			$("#next").prop("disabled", false);
			
		} else if ( tradeStatus === "request" && (myIdentity == sellerIdentity)) {
			// 진행 단계가 "구매 요청" 이면서, 내가 판매자일경우 다음 단계 버튼을 보여줌 ( 구매자는 x )
			$("#next").removeClass("btn-secondary");
			$("#next").addClass("btn-primary");
			$("#next").prop("disabled", false);			
		
		} else if ( tradeStatus === "proceeding" && (myIdentity == buyerIdentity)) {
			// 진행 단계가 "거래 진행" 이면서, 내가 구매자일경우 다음 단계 버튼을 보여줌 ( 판매자는 x ) 
			$("#next").removeClass("btn-secondary");
			$("#next").addClass("btn-primary");
			$("#next").prop("disabled", false);
			
		} else if ( tradeStatus === "confirmed" ) {
			// 여기가 끝 
			$("#next").removeClass("btn-secondary");
			$("#next").addClass("btn-primary");
			$("#next").prop("disabled", false);
		
		} else {
			// 위에 해당되지 않을경우 (기본 값)
			$("#next").removeClass("btn-primary");
			$("#next").addClass("btn-secondary");
			$("#next").prop("disabled", true);
		}
	}
	

	$(document).ready(function(){
		
		
		//=> 진행 단계에서 다음 버튼을 눌렀을때 
		$("#next").click(function(){
			
			// 거래 대기 상태에서 눌렀다면 ?  == > 거래 진행상태로 변경 + 판매자에게만 다음 버튼이 보이게끔 함
			if ( tradeStatus === "wait" && (myIdentity == buyerIdentity)) {
				tradeStatus = "request";
				
				$.ajax({
					method : "POST",
					url : "tradeStatusUpdate",
					data : { 
						"status" : "request" ,
						"identity" : tradeBoardIdentity,
						"board" : tradeBoardIdentity,
						"buyer" : buyerIdentity
					}
				})
				
				
			// 진행 단계가 "구매 요청" 이면서, 내가 판매자일경우 다음 단계 버튼을 보여줌 ( 구매자는 x )
			} else if ( tradeStatus === "request" ) {
				tradeStatus = "proceeding";
			
				$.ajax({
					method : "POST",
					url : "tradeStatusUpdate",
					data : { 
						"status" : "proceeding" ,
						"identity" : tradeBoardIdentity,
						"board" : tradeBoardIdentity,
						"buyer" : buyerIdentity
					}
				})
				
			// 진행 단계가 "거래 진행" 이면서, 내가 구매자일경우 다음 단계 버튼을 보여줌 ( 판매자는 x ) 
			} else if ( tradeStatus === "proceeding" ) {
				tradeStatus = "confirmed";
				
				$.ajax({
					method : "POST",
					url : "tradeStatusUpdate",
					data : { 
						"status" : "confirmed" ,
						"identity" : tradeBoardIdentity,
						"board" : tradeBoardIdentity,
						"buyer" : buyerIdentity
					}
				})
				
			// 여기가 끝 
			// } else if ( tradeStatus === "confirmed" ) {
				
			
			// 위에 해당되지 않을경우 (기본 값)
			} else {
				alert("오류");
			}			
		}) // ~ 다음 진행 단계 버튼 이벤트 끝
		
		
		tradeStatusShow();

		
	})
	

</script>
<script src="../resources/js/trade/chatContent.js"></script>
</html>