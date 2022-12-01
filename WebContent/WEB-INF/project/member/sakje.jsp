<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<%@ include file = "../include/_inc_top.jsp" %>
<h2>회원삭제</h2>
<form name="DirForm">
<input type="hidden" name="no" value="${dto.no }">
<table border="1" align="center" width="80%">
	<tr>
		<td width="150" style="font-weight:bold" align="center">아이디</td>
		<td>${dto.id }</td>
	</tr>
		<tr>
		<td style="font-weight:bold" align="center">비밀번호</td>
		<td><input type="password" name="passwd"></td>
	</tr>	
	<tr>
		<td style="font-weight:bold" align="center">이름</td>
		<td>${dto.name }</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">주민번호</td>
		<td>
			${dto.jumin1 }-${dto.jumin2 }******		
		</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">전화번호</td>
		<td>
			${dto.phone1 }-${dto.phone2 }-${dto.phone3 }
		</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">이메일</td>
		<td>
			${dto.email1 }@${dto.email2 }
		</td>
	</tr>
	<tr>
		<td style="font-weight:bold" align="center">주소</td>
		<td>
			${dto.postcode } ${dto.address } ${dto.detailAddress } ${dto.extraAddress }
		</td>
	</tr>
	<tr>	
		<td colspan="2" align="center" style="height:50px;">
			<button type="button" onClick="save();">삭제하기</button>
			<button type="button" onClick="move('member_list.do','');">목록으로</button>
		</td>
	</tr>
</table>
</form>
<script>
	function save() {
		if(confirm('삭제하시겠습니까?')) {
			document.DirForm.action="${path }/member_servlet/member_sakjeProc.do";
			document.DirForm.method="post";
			document.DirForm.submit();
		}
	}
	function move(value1, value2) {
		location.href = '${path }/member_servlet/' + value1 + '?no=' + value2 + '&${searchQuery }';
	}
</script>