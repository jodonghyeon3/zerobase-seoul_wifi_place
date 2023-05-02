<%@ page import="Controller.FindNearWifiInfo" %>
<%@ page import="Dto.Row" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.WifiDetail" %>
<%@ page import="Controller.WifiHistory" %>
<%@ page import="Controller.BookMarkGroupAddController" %>
<%@ page import="Dto.BookMarkGroup" %>
<%@ page import="Controller.BookMarkGroupAddController" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023/04/23
  Time: 9:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Row row = (Row) request.getAttribute("row");
    WifiHistory save = new WifiHistory();
    save.save(row);
    BookMarkGroupAddController bookMarkGroupAddController = new BookMarkGroupAddController();
    List<BookMarkGroup> bookMarkGroupList = bookMarkGroupAddController.select();
%>


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

    #customers tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    #customers tr:hover {
        background-color: #ddd;
    }

    #customers th {
        padding-top: 12px;
        padding-bottom: 12px;
        text-align: center;
        background-color: #04AA6D;
        color: white;
    }
</style>
<html>
<head>
    <title>Title</title>
    <h1>와이파이 정보 구하기</h1>
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">즐겨 찾기 보기</a> |
    <a href="bookmark-group.jsp">즐겨 찾기 그룹 관리</a> <br>
    <form action="bookMarkAdd" method="post">
        <select name="bookName">
            <option>즐겨찾기 그룹 이름 선택</option>
            <%for (int i = 0; i < bookMarkGroupList.size(); i++) {%>
            <option value="<%=bookMarkGroupList.get(i).getName()%>">
                <%=bookMarkGroupList.get(i).getName()%>
            </option>
            <%}%>
        </select>
        <input type="submit" value="즐겨찾기 추가하기" onclick="alert('즐겨찾기 정보를 추가하였습니다.')">
        <input type="hidden" name="wifiName" value="<%=row.getX_SWIFI_MAIN_NM()%>">
    </form>
</head>
<body>
<table id="customers">
    <tr>
        <th>거리(Km)</th>
        <td><%=row.getDISTANCE()%>
        </td>
    </tr>
    <tr>
        <th>관리번호</th>
        <td><%=row.getX_SWIFI_MGR_NO()%>
        </td>
    </tr>
    <tr>
        <th>자치구</th>
        <td><%=row.getX_SWIFI_WRDOFC()%>
        </td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td><a href=""><%=row.getX_SWIFI_MAIN_NM()%>
        </a></td>
    </tr>
    <tr>
        <th>도로명주소</th>
        <td><%=row.getX_SWIFI_ADRES1()%>
        </td>
    </tr>
    <tr>
        <th>상세주소</th>
        <td><%=row.getX_SWIFI_ADRES2()%>
        </td>
    </tr>
    <tr>
        <th>설치위치(층)</th>
        <td><%=row.getX_SWIFI_INSTL_FLOOR()%>
        </td>
    </tr>
    <tr>
        <th>설치유형</th>
        <td><%=row.getX_SWIFI_INSTL_TY()%>
        </td>
    </tr>
    <tr>
        <th>설치기관</th>
        <td><%=row.getX_SWIFI_INSTL_MBY()%>
        </td>
    </tr>
    <tr>
        <th>서비스구분</th>
        <td><%=row.getX_SWIFI_SVC_SE()%>
        </td>
    </tr>
    <tr>
        <th>망종류</th>
        <td><%=row.getX_SWIFI_CMCWR()%>
        </td>
    </tr>
    <tr>
        <th>설치년도</th>
        <td><%=row.getX_SWIFI_CNSTC_YEAR()%>
        </td>
    </tr>
    <tr>
        <th>실내외구분</th>
        <td><%=row.getX_SWIFI_INOUT_DOOR()%>
        </td>
    </tr>
    <tr>
        <th>X좌표</th>
        <td><%=row.getLNT()%>
        </td>
    </tr>
    <tr>
        <th>Y좌표</th>
        <td><%=row.getLAT()%>
        </td>
    </tr>
    <tr>
        <th>작업일자</th>
        <td><%=row.getLAT()%>
        </td>
    </tr>
    <tr>
        <th>자치구</th>
        <td><%=row.getWORK_DTTM()%>
        </td>
    </tr>


</table>
</body>
</html>