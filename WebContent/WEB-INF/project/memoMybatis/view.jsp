<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<%@ include file = "../include/_inc_top.jsp" %>
<h2>메모상세보기(Mybatis)</h2>
<table border="1" width="50%">
	<tr>
		<td align="center" style="font-weight:bold">순번</td>
		<td align="center" style="font-weight:bold">제목</td>
		<td align="center" style="font-weight:bold">작성자</td>
		<td align="center" style="font-weight:bold">내용</td>
		<td align="center" style="font-weight:bold">등록일</td>
		<td align="center" style="font-weight:bold">비고</td>
	</tr>
	<tr>
		<td align="center">${dto.memoNo }</td>
		<td>${dto.subject }</td>
		<td align="center">${dto.writer }</td>
		<td align="center">${dto.content }</td>
		<td align="center">${dto.regiDate }</td>
		<td align="center"><a href="#" onClick="move('memoMybatis_sujung.do','${dto.memoNo }');">수정</a> / <a href="#" onClick="move('memoMybatis_sakje.do','${dto.memoNo }');">삭제</a></td>
	</tr>
</table>
<div style="border: 0px solid red; width:50%; margin-top: 10px;" align="right">
<a href="${path }/memoMybatis_servlet/memoMybatis_list.do">전체목록</a>
|
<a href="#" onClick="move('memoMybatis_list.do','');">목록</a>
|
<a href="#" onClick="move('memoMybatis_chuga.do','');">등록</a>
|
</div>
<script>
	function move(value1, value2) {
		location.href = "${path }/memoMybatis_servlet/" + value1 + "?memoNo=" + value2;
	}
</script>