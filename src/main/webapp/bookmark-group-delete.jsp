<%@ page import="Controller.WifiHistory" %>
<%@ page import="Dto.History" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.BookMarkController" %>
<%@ page import="Dto.BookMark" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

%>
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
</head>

<body>


<h1>즐겨 찾기 그룹 관리</h1>
<a href="index.jsp">홈</a> |
<a href="history.jsp">위치 히스토리 목록</a> |
<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
<a href="">즐겨 찾기 보기</a> |
<a href="bookmark-group.jsp">즐겨 찾기 그룹 보기</a>
<br>
<input type="button" value="즐겨찾기 그룹 추가" onclick="move()">
<table id="customers">
  <tr>
    <th>ID</th>
    <th>북마크 이름</th>
    <th>와이파이명</th>
    <th>등록일자</th>
    <th>비고</th>
  </tr>
  <%int idx = 0;%>
  <% for (int i = 0; i < bookMarkList.size(); i++) {%>
  <tr>
    <% idx = i;%>
    <td><%=bookMarkList.get(i).getId()%></td>
    <td><%=bookMarkList.get(i).getName()%></td>
    <td><%=bookMarkList.get(i).getTurn()%></td>
    <td><%=bookMarkList.get(i).getDate()%></td>
    <td><%=bookMarkList.get(i).getDateNew()%></td>
    <td>
      <input type="hidden" name="change">
      <input type="submit" value="수정" onclick="moveChange('<%=bookMarkList.get(i).getName()%>', '<%=bookMarkList.get(i).getTurn()%>', '<%=bookMarkList.get(i).getId()%>')">

      <input type="hidden" name="delete" >
      <input type="submit" value="삭제" onclick="moveDelete('<%=bookMarkList.get(i).getName()%>', '<%=bookMarkList.get(i).getTurn()%>')">
    </td>
  </tr>
  <% } %>
</table>

</body>
</html>
<script>
  function move() {
    window.location.href = "bookmark-group-add.jsp";
  }
  function moveChange(value, value1, value2) {
    window.location.href = "bookmark-group-change.jsp?name=" + value + "&turn=" + value1 + "&id=" + value2;
  }
  function moveDelete(value, value1) {
    window.location.href = "bookmark-group-delete.jsp?name=" + value + "&turn=" + value1;
  }
</script>