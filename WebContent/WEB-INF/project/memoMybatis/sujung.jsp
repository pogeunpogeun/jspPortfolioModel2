<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h2>메모수정(Mybatis)</h2>
<form name="DirForm">
<input type="hidden" name="searchGubun" value="${searchGubun }">
<input type="hidden" name="searchData" value="${searchData }">
<input type="hidden" name="memoNo" value="${dto.memoNo }">
<table border="1" width="50%">
	<tr>
		<td style="font-weight:bold" align="center">제목</td>
		<td><input type="text" name="subject" value="${dto.subject }"></td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">작성자</td>
		<td>${dto.writer }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">내용</td>
		<td><textarea style="width:99%" name="content">${dto.content }</textarea></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<button type="button" onClick="save();">수정하기</button>
		<button type="button" onClick="move('memoMybatis_list.do','');">목록으로</button>
		</td>
	</tr>
</table>
</form>
<script>
	function save() {
		if(confirm('수정하시겠습니까?')) {
			document.DirForm.action="${path }/memoMybatis_servlet/memoMybatis_sujungProc.do";
			document.DirForm.method="";
			document.DirForm.submit();
		}
	}
	function move(value1, value2) {
		location.href = "${path }/memoMybatis_servlet/" + value1 + "?memoNo=" + value2 + "&${searchQuery}"
	}
</script>