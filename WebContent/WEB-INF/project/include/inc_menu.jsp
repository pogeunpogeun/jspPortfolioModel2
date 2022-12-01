<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<table border="0" align="center" width="80%">
	<tr>
		<td colspan="13" style="padding: 0px 0px 0px 20px;">
			<%-- Location : project > ${folderName } > ${fileName } --%>
			접속IP : ${ip }
		</td>
		<td colspan="6" style="padding:0px 10px 10px 10px;" align="right">	
			<c:choose>
			<c:when test="${sessionScope.sessionNo == null || sessionScope.sessionNo == '' || sessionScope.sessionNo == 0}">
				<a href="${path }/noLogin_servlet/noLogin_login.do">로그인</a>
			</c:when>
			<c:otherwise>
				${sessionScope.sessionName }님
				&nbsp;
				<a href="${path }/member_servlet/member_sujung.do?no=${sessionNo }">회원정보수정</a>
				&nbsp;
				<a href="${path }/member_servlet/member_sakje.do">회원탈퇴</a>
				&nbsp;
				<a href="${path }/noLogin_servlet/noLogin_logout.do">로그아웃</a>
			</c:otherwise>
		</c:choose>				
		</td>
	</tr>
	<tr align="center">
		<td style="padding:0px 10px;" id="home">
			<a href="${path }/dashBoard_index.do">HOME</a>
		</td>
		<td style="padding:0px 10px;" id="member">
			<a href="${path }/member_servlet/member_list.do">회원관리</a>
		</td>
		<td style="padding:0px 10px;" id="memo">
			<a href="${path }/memo_servlet/memo_list.do">메모장</a>
		</td>
		<td style="padding:0px 10px;" id="memoMybatis">
			<a href="${path }/memoMybatis_servlet/memoMybatis_list.do"><br>메모장<br>(Mybatis)</a>
		</td>
		<td style="padding:0px 10px;" id="guestBook">
			<a href="#">방명록</a>
		</td>
		<td style="padding:0px 10px;" id="guestBookMybatis">
			<a href="${path }/guestBookMybatis_servlet/guestBookMybatis_list.do"><br>방명록<br>(Mybatis)</a>
		</td>
		<td style="padding:0px 10px;" id="board">
			<a href="${path }/board_servlet/board_list.do">게시판</a>
		</td>
		<td style="padding:0px 10px;" id="shopProduct">
			<a href="#"><br>Mall<br>(상품관리)</a>
		</td>
		<td style="padding:0px 10px;" id="shopVendor">
			<a href="#"><br>Mall<br>(제조사)</a>
		</td>
		<td style="padding:0px 10px;" id="shopMall">
			<a href="#"><br>Mall<br>(쇼핑몰)</a>
		</td>
		<td style="padding:0px 10px;" id="chart">
			<a href="#">Chart</a>
		</td>
	</tr>	
</table>
<br>