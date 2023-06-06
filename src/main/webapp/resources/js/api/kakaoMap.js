function getMap(addr) {

	mapDefaultOption = { 
	    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	    level: 3 
	};   
	
	var mapContainer = document.getElementById('map'); 
	var map = new kakao.maps.Map(mapContainer, mapDefaultOption); // 지도를 생성합니다
	
	
	
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	// 주소로 좌표를 검색합니다
	geocoder.addressSearch( addr , function(result, status) {
	
	    // 정상적으로 검색이 완료됐으면 
	     if (status === kakao.maps.services.Status.OK) {
	
	        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	
	        // 마커 위치지정 + 생성
	        var position =  new kakao.maps.LatLng( coords ); // + 마커 위치지정    
	        var marker = new kakao.maps.Marker({
	            position: coords,
	            clickable: true 
	        });
	        marker.setMap(map);
	
			// ==> 지정된 위치에 아래의 양식으로 생성
	        var infowindow = new kakao.maps.InfoWindow({
	            content : '<div style="padding:10px; width: 300px; text-align: center;">아래 마커를 클릭해보세요 : )</div>',
	            removable : true
	        }); infowindow.open(map, marker);     	             
	
	        // 마커를 클릭했을 때 네이버 지도 검색결과 ( 새 창 열기 )
	        kakao.maps.event.addListener(marker, 'click', function() {		   	        	
	        	window.open( 'https://map.naver.com/v5/search/' + addr );
			});
	
	
    	// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
    	map.setCenter(coords);

		} 
	});    

}