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
	
	// (우측)에는 로그인한 유저의 프로필을 보여주고
	var myIdentity = ${user.identity};
	var myImgPath = "${user.imgPath}";
	
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




</script>
<script src="../resources/js/trade/chatContent.js"></script>
</html>