<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<%@ include file = "../include/_inc_top.jsp" %>
<h2>회원관리</h2>
<div style="border:0px solid red; width:80%; margin-top:10px;" align="left">
* 전체목록 : ${(totalRecord) }건 <br>
<c:choose>
	<c:when test = "${searchData == '' }">
		* 검색어 ""으/로 검색된 목록 : ${totalRecord } 건
	</c:when>
	<c:otherwise>
		* 검색어 "<font style="color: blue; font-weight:bold;">${searchData }</font>"으/로 검색된 목록 : ${totalRecord } 건
	</c:otherwise>
</c:choose>
(${pageNumber }/${map.totalPage })
</div>
<table border="1" align="center" width="80%">
	<tr>
		<th>순번</th>
		<th>아이디</th>
		<th>비밀번호</th>
		<th>이름</th>
		<th>생년월일</th>
		<th>전화번호</th>
		<th>가입일</th>
	</tr>
	<c:if test="${totalRecord == 0 }">
	<tr>
		<td colspan="7" height="200" align="center">등록된 회원이 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var = "memberDto" items = "${list }">
	<tr>
		<td align="center">${memberDto.no }</td>
		<td>
			<a href="#" onClick="move('member_view.do','${memberDto.no}');">${memberDto.id }</a>
		</td>
		<td align="center">${memberDto.passwd }</td>
		<td align="center">${memberDto.name }</td>
		<td align="center">${memberDto.jumin1 }-${memberDto.jumin2 }******</td>
		<td align="center">${memberDto.phone1 }-${memberDto.phone2 }-${memberDto.phone3 }</td>
		<td align="center">${memberDto.regiDate }</td>
	</tr>
	</c:forEach>
</table>
<div style="border: 0px solid red; width:80%; margin-top: 10px;" align="right">
<a href="${path }/member_servlet/member_list.do">전체목록</a>
|
<a href="#" onClick="move('member_list.do','');">목록</a>
|
<a href="#" onClick="move('member_chuga.do','');">등록</a>
|
</div>
<div style="border:0px solid red; width:80%; margin-top:10px;" align="center">
<form name="searchForm">
<select name="searchGubun" id="searchGubun">
<c:choose>
	<c:when test="${searchGubun == 'id' }">
		<option value="">--선택--</option>
		<option value="id" selected>아이디</option>
		<option value="name">이름</option>
		<option value="id_name">아이디+이름</option>
	</c:when>
	<c:when test="${searchGubun == 'name' }">
		<option value="">--선택--</option>
		<option value="id">아이디</option>
		<option value="name" selected>이름</option>
		<option value="id_name">아이디+이름</option>
	</c:when>
	<c:when test="${searchGubun == 'id_name' }">
		<option value="">--선택--</option>
		<option value="id">아이디</option>
		<option value="name">이름</option>
		<option value="id_name" selected>아이디+이름</option>
	</c:when>
	<c:otherwise>
		<option value="" selected>--선택--</option>
		<option value="id">아이디</option>
		<option value="name">이름</option>
		<option value="id_name">아이디+이름</option>
	</c:otherwise>
</c:choose>	
</select>
&nbsp;
<input type="text" name="searchData" id="searchData" value="${searchData }" style="width:150px">
&nbsp;
<button type="button" onClick="search();">검색</button>
</form>
</div>
<c:if test="${totalRecord > 0 }">
<div style="boder:0px solid red; width: 80%; margin-top: 10px; align="center">
	<!-- ------------------------------------------------------------------------------ -->
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
			<a href="#" onClick="goPage('${i }');">${i }</a>
		</c:if>
		&nbsp;
	</c:forEach>
	<c:if test="${map.lastPage < map.totalPage }">
		<a href="#" onClick="goPage('${map.startPage + map.blockSize }');">[다음10개]</a>
	</c:if>
	<c:if test="${map.lastPage >= map.totalPage }">
		[다음10개]
	</c:if>
	&nbsp;
	<a href="#" onClick="goPage('${map.totalPage }');">[끝페이지]</a>
	<!-- ------------------------------------------------------------------------------ -->
</div>
</c:if>
<script>
	function move(value1, value2) {
		location.href = '${path }/member_servlet/' + value1 + '?no=' + value2 + '&${searchQuery }';
	}
	function search() {
		if(confirm('검색하시겠습니까?')) {
			document.searchForm.action="${path }/member_servlet/member_search.do";
			document.searchForm.method="post";
			document.searchForm.submit();
		}
	}
	function goPage(value1) {
		location.href = "${path }/member_servlet/member_list.do?pageNumber=" + value1 + "&${searchQuery }";
	}
</script>