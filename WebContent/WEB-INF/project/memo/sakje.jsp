<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<h2>메모삭제</h2>
<form name="DirForm">
<input type="hidden" name="memoNo" value="${sakjeDto.memoNo }">
<table border="1" width="50%">
	<tr>
		<td style="font-weight:bold" align="center">순번</td>
		<td>${sakjeDto.memoNo }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">제목</td>
		<td>${sakjeDto.subject }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">작성자</td>
		<td>${sakjeDto.writer }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">내용</td>
		<td>${sakjeDto.content }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">등록일</td>
		<td>${sakjeDto.regiDate }</td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<button type="button" onClick="save();">삭제하기</button>
		</td>
	</tr>
</table>
</form>
<script>
	function save() {
		if(confirm('삭제하시겠습니까?')) {
			document.DirForm.action="${path }/memo_servlet/memo_sakjeProc.do";
			document.DirForm.method="post";
			document.DirForm.submit();
		}
	}
</script>