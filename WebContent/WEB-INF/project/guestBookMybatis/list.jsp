<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<%@ include file = "../include/_inc_top.jsp" %>
<h2>방명록(Mybatis)</h2>
<div style="border:0px solid red; width:50%; margin-top:10px;" align="left">
* 전체목록 : <font style="color: blue; font-weight:bold;">${(totalRecord) }</font>건 <br>
<c:choose>
	<c:when test = "${searchData == '' }">
		* 검색어 ""으/로 검색된 목록 : <font style="color: blue; font-weight:bold;">${totalRecord }</font> 건
	</c:when>
	<c:otherwise>
		* 검색어 "<font style="color: blue; font-weight:bold;">${searchData }</font>"으/로 검색된 목록 : ${totalRecord } 건
	</c:otherwise>
</c:choose>
(${pageNumber }/${map.totalPage })
</div>
<table border="0" width="50%">
	<c:if test="${totalRecord == 0 }">
		<tr>
			<td colspan="4" height="200" align="center"><font style="color: blue; font-weight:bold;">등록된 방명록이 없습니다.</font></td>
		</tr>
	</c:if>
	<c:forEach var = "guestBookMybatisDto" items="${list }">
	<tr>
		<td>
			<table border="1" width="100%">
				<tr>
					<td style="font-weight:bold" align="center">이름</td>
					<td align="center">${guestBookMybatisDto.name }</td>
					<td style="font-weight:bold" align="center">날짜</td>
					<td align="center">${guestBookMybatisDto.regiDate }</td>
				</tr>
				<tr>
					<td style="font-weight:bold" align="center">이메일</td>
					<td colspan="3">&nbsp;${guestBookMybatisDto.email }</td>
				</tr>
				<tr>
					<td colspan="4" height="100px">
						${fn:replace(guestBookMybatisDto.content, newLineChar, "<br/>") }
						<br><br><br><br><br>
						<hr>
						<div style="float:right; margin-bottom: 5px;">
						<a href="#" onClick="move('guestBookMybatis_sujung.do','${guestBookMybatisDto.no }');">수정</a>
						/
						<a href="#" onClick="move('guestBookMybatis_sakje.do','${guestBookMybatisDto.no }');">삭제</a>
						&nbsp;
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>	
</c:forEach>
</table>
<div style="border: 0px solid red; width:50%; margin-top: 10px;" align="right">
<a href="${path }/guestBookMybatis_servlet/guestBookMybatis_list.do">전체목록</a>
|
<a href="#" onClick="move('guestBookMybatis_chuga.do','');">등록</a>
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
<br>
<form name="searchForm">
<select name="searchGubun" id="searchGubun">
<c:choose>
	<c:when test="${searchGubun == 'name' }">
		<option value="">--선택--</option>
		<option value="name" selected>작성자</option>
		<option value="content">내용</option>
		<option value="name_content">작성자+내용</option>
	</c:when>
	<c:when test="${searchGubun == 'content' }">
		<option value="">--선택--</option>
		<option value="name" >작성자</option>
		<option value="content" selected>내용</option>
		<option value="name_content">작성자+내용</option>
	</c:when>
	<c:when test="${searchGubun == 'name_content' }">
		<option value="">--선택--</option>
		<option value="name">작성자</option>
		<option value="content">내용</option>
		<option value="name_content" selected>작성자+내용</option>
	</c:when>
	<c:otherwise>
		<option value="" selected>--선택--</option>
		<option value="name">작성자</option>
		<option value="content">내용</option>
		<option value="name_content">작성자+내용</option>
	</c:otherwise>
</c:choose>
</select>
&nbsp;
<input type="text" name="searchData" id="searchData" value="${searchData }" style="width:150px">
&nbsp;
<button type="button" onClick="search();">검색하기</button>
</form>
<script>
	function move(value1, value2) {
		location.href = "${path}/guestBookMybatis_servlet/" + value1 + "?no=" + value2 + "&${searchQuery }";
	}
	function search() {
		if(confirm('검색하시겠습니까?')) {
			document.searchForm.action="${path }/guestBookMybatis_servlet/guestBookMybatis_search.do";
			document.searchForm.method="";
			document.searchForm.submit();
		}
	}
	function goPage(value1) {
		location.href = "${path }/guestBookMybatis_servlet/guestBookMybatis_list.do?pageNumber=" + value1 + "&${searchQuery2 }";
	}
</script>