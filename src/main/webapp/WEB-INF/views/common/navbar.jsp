<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
 
 <div class="notification" style="position: fixed; right: 40px; top: 30px; cursor: pointer; display: none;">
 	  <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-primary">
    	0
    	<span class="visually-hidden">unread messages</span>
  	</span>
 	<i class="fa-regular fa-bell animate__swing" style="font-size: x-large; "></i>
 </div>
 
 
 <!-- aws 배포할땐  앞에 /smile 빼고 배포하기 ★ -->
<nav>
  <div class="position-relative" >
      <img onclick="location.href='/smile/main/list'" src="../resources/imgs/title.png" class="title-banner mb-5" style="cursor: pointer;"> 
<!--    <img onclick="location.href='/main/list'" src="../resources/imgs/title.png" class="title-banner mb-5" style="cursor: pointer;"> -->
    <ul class="desktop-nav">
      <li><a href="/smile/main/list"><i class="fa-solid fa-house"></i> 
<!-- <li><a href="/main/list"><i class="fa-solid fa-house"></i> -->
      메인</a></li>
     <li><a href="/smile/trade/list"><i class="fa-solid fa-tag"></i>
<!-- <li><a href="/trade/list"><i class="fa-solid fa-tag"></i> -->
      중고 거래</a></li>
     <li><a href="/smile/notice/list"><i class="fa-solid fa-tower-cell"></i>
<!-- <li><a href="/notice/list"><i class="fa-solid fa-tower-cell"></i> -->
      공지사항</a></li>
     <li><a href="/smile/user/info"><i class="fa-solid fa-circle-user"></i>
<!-- <li><a href="/user/info"><i class="fa-solid fa-circle-user"></i> -->
      내 정보</a></li>
      
<!-- 
      <li><a href="/smile/admin"><i class="fa-solid fa-gear"></i>
      사이트 관리</a></li>
-->
    </ul>
  </div>
</nav>



<style> @import '../resources/css/common/navbar.css' </style>
<script src="../resources/js/common/navbar.js"></script>