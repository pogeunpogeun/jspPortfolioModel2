<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<%@ include file = "../include/_inc_top.jsp" %>
<h2>메모삭제(Mybatis)</h2>
<form name="DirForm">
<input type="hidden" name="memoNo" value="${dto.memoNo }">
<table border="1" width="50%">
	<tr>
		<td style="font-weight:bold" align="center">순번</td>
		<td style="font-weight:bold" align="center">제목</td>
		<td style="font-weight:bold" align="center">작성자</td>
		<td style="font-weight:bold" align="center">내용</td>
		<td style="font-weight:bold" align="center">등록일</td>
	</tr>
	<tr>
		<td align="center">${dto.memoNo }</td>
		<td>${dto.subject }</td>
		<td align="center">${dto.writer }</td>
		<td align="center">${dto.content }</td>
		<td align="center">${dto.regiDate }</td>
	</tr>
	<tr>
		<td colspan="5" align="center">
		<button type="button" onClick="save();">삭제하기</button>
		<button type="button" onClick="move('memoMybatis_list.do','');">목록으로</button>
		</td>
	</tr>
</table>
</form>
<script>
	function save() {
		if(confirm('삭제하시겠습니까?')) {
			document.DirForm.action="${path }/memoMybatis_servlet/memoMybatis_sakjeProc.do";
			document.DirForm.method="";
			document.DirForm.submit();
		}
	}
	function move(value1, value2) {
		location.href = "${path }/memoMybatis_servlet/" + value1 + "?memoNo=" + value2;
	}
</script>