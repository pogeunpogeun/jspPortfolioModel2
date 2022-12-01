<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<h2>메모목록</h2>
<div style="border:0px solid red; width:50%; margin-top:10px;" align="left">
* 전체목록 : ${(totalRecord) }건<br>
<c:choose>
	<c:when test="${searchData == '' }">
		* 검색어 "" 으/로 검색된 목록 : ${totalRecord } 건
	</c:when>
	<c:otherwise>
		* 검색어 "<font style="color: blue; font-weight:bold;">${searchData }</font>"으/로 검색된 목록 : ${totalRecord  } 건
	</c:otherwise>
</c:choose>
(${pageNumber }/${map.totalPage })
</div>
<table border="1" width="50%">
	<tr>
		<th>순번</th>
		<th>제목</th>
		<th>작성자</th>
		<th>등록일</th>
	</tr>
	<c:if test="${totalRecord == 0 }">
	<tr>
		<td colspan="4" height="200" align="center">등록된 메모가 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var = "memoDto" items = "${list }">
	<tr>
		<td align="center">${memoDto.memoNo }</td>
		<td><a href="#" onClick="move('memo_view.do','${memoDto.memoNo}');">${memoDto.subject }</a></td>
		<td align="center">${memoDto.writer }</td>
		<td align="center">${memoDto.regiDate }</td>
	</tr>
	</c:forEach>
</table>
<div style="border: 0px solid red; width:50%; margin-top: 10px;" align="right">
<a href="${path }/memo_servlet/memo_list.do">전체목록</a>
|
<a href="#" onClick="move('memo_list.do','');">목록</a>
|
<a href="#" onClick="move('memo_chuga.do','');">등록</a>
|
</div>
<div style="border:0px solid red; width:50%; margin-top:10px;" align="center">
<form name="searchForm">
<select name="searchGubun" id="searchGubun">
<c:choose>
	<c:when test="${searchGubun == 'subject' }">
		<option value="">--선택--</option>
		<option value="subject" selected>제목</option>
		<option value="content">내용</option>
		<option value="subject_content">제목+내용</option>
	</c:when>
	<c:when test="${searchGubun == 'content' }">
		<option value="">--선택--</option>
		<option value="subject" >제목</option>
		<option value="content" selected>내용</option>
		<option value="subject_content">제목+내용</option>
	</c:when>
	<c:when test="${searchGubun == 'subject_content' }">
		<option value="">--선택--</option>
		<option value="subject">제목</option>
		<option value="content">내용</option>
		<option value="subject_content" selected>제목+내용</option>
	</c:when>
	<c:otherwise>
		<option value="" selected>--선택--</option>
		<option value="subject">제목</option>
		<option value="content">내용</option>
		<option value="subject_content">제목+내용</option>
	</c:otherwise>
</c:choose>
</select>
&nbsp;
<input type="text" name="searchData" id="searchData" value="${searchData }" style="width:150px">
&nbsp;
<button type="button" onClick="search();">검색하기</button>
</form>
</div>
<c:if test="${totalRecord > 0 }">
<div style="boder:0px solid red; width: 50%; margin-top: 10px; align="center">
<!-- ----------------------------------------------------------------------------- -->
	<a href="#" onClick="goPage('1');">[첫페이지]</a>
	&nbsp;
	<c:if test="${map.startPage > map.blockSize }">
			<a href="#" onClick="goPage('${map.startPage - map.blockSize}');">[이전10개]</a>
		</c:if>
		<c:if test="${map.startPage <= map.blockSize }">
			[이전10개]
		</c:if>
		&nbsp;
	<c:forEach var="i" begin="${map.startPage }" end="${map.lastPage }" step="1">
			<c:if test="${i == pageNumber }">
				[${i }]
			</c:if>
		<c:if test="${i != pageNumber }">
			<a href="#" onClick="goPage('${i}');">${i }</a>
		</c:if>
		&nbsp;
	</c:forEach>
	<c:if test="${map.lastPage < map.totalPage }">
		<a href="#" onClick="goPage('${map.stargPage + map.blockSize}');">[다음10개]</a>
	</c:if>
	<c:if test="${map.lastPage >= map.totalPage }">
		[다음10개]
	</c:if>
	&nbsp;
	<a href="#" onClick="goPage('${map.totalPage}');">[끝페이지]</a>
	<!-- ----------------------------------------------------------------------------- -->
</div>
</c:if>
<script>
	function move(value1, value2) {
		location.href = "${path }/memo_servlet/" + value1 + "?memoNo=" + value2 + "&${searchQuery }";
	}
	function search() {
		if(confirm('검색하시겠습니까?')) {
			document.searchForm.action="${path }/memo_servlet/memo_search.do";
			document.searchForm.method="";
			document.searchForm.submit();
		}
	}
	function goPage(value1) {
		location.href = "${path }/memo_servlet/memo_list.do?pageNumber=" + value1 + "&${searchQuery }";
	}
</script>