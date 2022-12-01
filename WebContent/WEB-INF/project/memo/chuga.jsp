<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<h2>메모등록</h2>
<form name="DirForm">
<table border="1" width="50%">
	<tr>
		<td style="font-weight:bold" align="center">제목</td>
		<td><input type="text" name="subject"></td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">작성자</td>
		<td><input type="text" name="writer"></td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">내용</td>
		<td><textarea name="content" style="width:99%"></textarea></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><button type="button" onClick="save();">등록하기</button></td>
	</tr>
</table>
</form>
<script>
	function save() {
		if(confirm('등록하시겠습니까?')) {
			document.DirForm.action="${path }/memo_servlet/memo_chugaProc.do";
			document.DirForm.method="";
			document.DirForm.submit();
		}
	}
</script>
