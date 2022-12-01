<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<h2>메모수정</h2>
<form name="DirForm">
<input type="hidden" name="memoNo" value="${sujungDto.memoNo }">
<table border="1" width="50%">
	<tr>
		<td style="font-weight:bold" align="center">순번</td>
		<td>${sujungDto.memoNo }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">제목</td>
		<td><input type="text" name="subject" value="${sujungDto.subject }"></td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">작성자</td>
		<td>${sujungDto.writer }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">내용</td>
		<td><textarea name="content">${sujungDto.content }</textarea></td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">등록일</td>
		<td>${sujungDto.regiDate }</td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<button type="button" onClick="save();">수정하기</button>
		</td>
	</tr>
</table>
</form>
<script>
	function save() {
		if(confirm('수정하시겠습니까?')) {
			document.DirForm.action="${path }/memo_servlet/memo_sujungProc.do";
			document.DirForm.method="post";
			document.DirForm.submit();
		}
	}
</script>
