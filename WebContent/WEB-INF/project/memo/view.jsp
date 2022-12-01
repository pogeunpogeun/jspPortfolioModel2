<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<h2>메모상세보기</h2>
<table border="1" width="50%">
	<tr>
		<td style="font-weight:bold" align="center">순번</td>
		<td>${viewDto.memoNo }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">제목</td>
		<td>${viewDto.subject }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">작성자</td>
		<td>${viewDto.writer }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">내용</td>
		<td>${viewDto.content }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">등록일</td>
		<td>${viewDto.regiDate }</td>
	</tr>
</table>
<div style="border:0px solid red; padding-top: 10px; width:50%;" align="center">
	<table border="0" width="100%">
		<tr>
			<td width="150" style="font-weight:bold">이전글</td>
			<td>
				<c:if test="${viewDto.preNo > 0 }">
					<a href="#" onClick="move('memo_view.do','${viewDto.preNo }');">${viewDto.preName }</a>
				</c:if>
				<c:if test="${viewDto.preNo <= 0 }">
					이전 글이 없습니다.
				</c:if>			
			</td>
		</tr>
		<tr>
			<td width="150" style="font-weight:bold">다음글</td>
			<td>
				<c:if test="${viewDto.nxtNo > 0 }">
					<a href="#" onClick="move('memo_view.do','${viewDto.nxtNo }');">${viewDto.nxtName }</a>
				</c:if>
				<c:if test="${viewDto.nxtNo <= 0 }">
					다음 글이 없습니다.
				</c:if>	
			</td>
		</tr>
	</table>
</div>
<div style="border: 0px solid red; width:50%; margin-top: 10px;" align="right">
<a href="${path }/memo_servlet/memo_list.do">전체목록</a>
|
<a href="#" onClick="move('memo_list.do','');">목록</a>
|
<a href="#" onClick="move('memo_chuga.do','');">등록</a>
|
<a href="#" onClick="move('memo_sujung.do','${viewDto.memoNo }');">수정</a>
|
<a href="#" onClick="move('memo_sakje.do','${viewDto.memoNo }');">삭제</a>
|
</div>
<script>
	function move(value1, value2) {
		location.href = "${path }/memo_servlet/" + value1 + "?memoNo=" + value2; 
	}
</script>