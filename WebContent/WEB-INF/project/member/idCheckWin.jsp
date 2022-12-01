<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
<form name="DirForm">
	<table border="1" align="center" width="80%">
		<tr>
			<td colspan="2"><h2>아이디 찾기</h2></td>
		</tr>
		<tr>
			<td>아이디</td>
			<td>
				<input type="text" name="id" id="id" value="${id }" style="width: 100px;">
				<span id="spanMsg">${msg }</span>
				<input type="hidden" name="result" id="result" value="${imsiId }" style="width:100px;">
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" style="height:50px;">
				<button type="button" onClick="search();">검색</button>
				<c:choose>
				<c:when test="${imsiId == ''}">
					&nbsp;
				</c:when>
				<c:otherwise>
					<button type="button" onClick="apply();">적용</button>
				</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
<script>
	function search() {
		if(document.DirForm.id.value == '') {
			alert('아이디를 입력하세요');
			document.DirForm.id.focus();
			return;
		}
		if(confirm('OK?')) {
			document.DirForm.action="${path }/member_servlet/member_idCheckWinProc.do";
			document.DirForm.method="post";
			document.DirForm.submit();
		}
	}
	function apply() {
		var id = document.getElementById("result").value;
		opener.document.getElementById("id").value = id;
		opener.document.getElementById("tempId").value = id;
		opener.document.getElementById("label_id").innerHTML = "<font style='color: blue; font-size: 8px;'>hahaha</font>";
		window.close();
		
	}
</script>