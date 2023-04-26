<%@ page import="Db.Db" %>
<%@ page import="Dto.Root" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023/04/20
  Time: 3:18 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Db db = new Db();
    int total = db.save();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1><%=total%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
<a href="index.jsp">홈으로</a>
</body>
</html>
