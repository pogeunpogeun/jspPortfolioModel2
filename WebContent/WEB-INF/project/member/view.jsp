<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<%@ include file = "../include/_inc_top.jsp" %>
<h2>회원상세보기</h2>
<table border="1" align="center" width="50%">
	<tr>
		<td width="150" style="font-weight:bold" align="center">아이디</td>
		<td>${dto.id }</td>
	</tr>
		<tr>
		<td style="font-weight:bold" align="center">비밀번호</td>
		<td>${dto.passwd }</td>
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
</table>
<div style="border:0px solid red; padding-top: 10px; width:50%;" align="center">
	<table border="0" width="100%">
		<tr>
			<td width="150" style="font-weight:bold;">이전회원</td>
			<td>
				<c:if test="${dto.preNo > 0 }">
					<a href="#" onClick="move('member_view.do','${dto.preNo }');">${dto.preName }</a>
				</c:if>
				<c:if test="${dto.preNo <= 0 }">
					등록된 회원이 없습니다.
				</c:if>			
			</td>
		</tr>
		<tr>
			<td width="150" style="font-weight:bold;">다음회원</td>
			<td>
				<c:if test="${dto.nxtNo > 0 }">
					<a href="#" onClick="move('member_view.do','${dto.nxtNo }');">${dto.nxtName }</a>
				</c:if>
				<c:if test="${dto.nxtNo <= 0 }">
					등록된 회원이 없습니다.
				</c:if>	
			</td>
		</tr>
	</table>
</div>
<div style="border: 0px solid red; width:50%; margin-top: 10px;" align="right">
<a href="${path }/member_servlet/member_list.do">전체목록</a>
|
<a href="#" onClick="move('member_list.do','');">목록</a>
|
<a href="#" onClick="move('member_chuga.do','');">등록</a>
|
<a href="#" onClick="move('member_sujung.do','${dto.no }');">수정</a>
|
<a href="#" onClick="move('member_sakje.do','${dto.no }');">삭제</a>
|
</div>
<script>
	function move(value1, value2) {
		location.href = '${path }/member_servlet/' + value1 + '?no=' + value2 + '&${searchQuery }';
	}
</script>