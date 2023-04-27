<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        #customers {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #customers td, #customers th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #customers tr:nth-child(even){background-color: #f2f2f2;}

        #customers tr:hover {background-color: #ddd;}

        #customers th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #04AA6D;
            color: white;
        }
    </style>
    <script>
        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                        alert(position.coords.latitude + ' ' + position.coords.longitude);
                        var lat = position.coords.latitude;
                        document.getElementById("latid").value = lat ;
                        var lnt = position.coords.longitude;
                        document.getElementById("lntid").value = lnt;
                    },
                    function (error) {
                        console.error(error);
                    },
                    {
                        enableHighAccuracy: false,
                        maximumAge: 0,
                        timeout: Infinity
                    });
            } else {
                alert('GPS를 지원하지 않습니다');
            }
        }

    </script>
</head>
<body>


<h1>와이파이 정보 구하기</h1>
<a href="index.jsp">홈</a> |
<a href="">위치 히스토리 목록</a> |
<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
<br>
<div>
    <form action="/location.do" method="post">
        <label for="latid">LAT:</label>
        <input type="text" id="latid" name="latname" value="${latname}">


        <label for="lntid">, LNT:</label>
        <input type="text" id="lntid" name="lntname" value="${lntname}">

        <input type="button" value="내 위치 가져오기" onclick="getLocation()">
        <input type="submit" value="근처 WIPI 정보 보기">

    </form>
</div>


<table id="customers">
    <tr>
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>

</table>

</body>
</html>