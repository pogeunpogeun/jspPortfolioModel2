<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<%@ include file = "../include/_inc_top.jsp" %>
<h2>방명록 수정(Mybatis)</h2>
<form name="DirForm">
<input type="hidden" name="no" value="${dto.no }">
<input type="hidden" name="searchGubun" value="${searchGubun }">
<input type="hidden" name="searchData" value="${searchData }">
<input type="hidden" name="pageNumber" value="${pageNumber }">
<table border="1" width="50%">
	<tr>
		<td style="font-weight:bold" align="center">이름(*)</td>
		<td><input type="text" name="name" value="${dto.name }"></td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">이메일</td>
		<td><input type="text" name="email" value="${dto.email }"></td>
	</tr>
	<tr>
		<td colspan="2">
			<textarea name="content" style="width:99%; height:200px;">${dto.content }</textarea>
		</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">비밀번호(*)</td>
		<td><input type="password" name="passwd"></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<button type="button" onClick="save();">수정하기</button>
			<button type="button" onClick="move('guestBookMybatis_list.do','');">목록으로</button>
		</td>
	</tr>
</table>
</form>
<script>
	function save() {
		if(confirm('수정하시겠습니까?')) {
			document.DirForm.action="${path }/guestBookMybatis_servlet/guestBookMybatis_sujungProc.do";
			document.DirForm.method="post";
			document.DirForm.submit();
		}
	}
	function move(value1, value2) {
		location.href = "${path}/guestBookMybatis_servlet/" + value1 + "?no=" + value2;
	}
</script>