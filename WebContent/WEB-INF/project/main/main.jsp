<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
<table border="1" align="center" width="90%">
	<tr>
		<td align="center" style="padding:20px 20px;">
			<jsp:include page = "../include/inc_menu.jsp"></jsp:include>
		</td>
	</tr>
	<tr>
		<td align="center" style="padding:20px 20px 50px 20px;">
			<jsp:include page = "../${folderName }/${fileName }.jsp"></jsp:include>
		</td>
	</tr>
	<tr>
		<td align="center" style="padding:20px 20px;">
			<jsp:include page = "../include/inc_bottom.jsp"></jsp:include>
		</td>
	</tr>
	
</table>

</body>
</html>