<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/inc_header.jsp" %>
<%@ include file = "../include/_inc_top.jsp" %>
<style>
	input:focus {outline:none;}
	textarea:focus {outline:none;}
	input:
</style>
<h2>게시물 등록</h2>
<form name="DirForm">
<table border="1" width="50%">
	<tr>
		<td align="center" style="font-weight:bold;">작성자</td>
		<td><input style="border:none; width:99%" type="text" name="writer"></td>
	</tr>
	<tr>
		<td align="center" style="font-weight:bold;">이메일</td>
		<td><input style="border:none; width:99%" type="text" name="email"></td>
	</tr>
	<tr>
		<td align="center" style="font-weight:bold;">제목</td>
		<td><input style="border:none; width:99%" type="text" name="subject"></td>
	</tr>
	<tr>
		<td colspan="2">
		<textarea style="border:none; resize:none; width:99%; height:200px;" name="content"></textarea></td>
	</tr>
	<tr>
		<td align="center" style="font-weight:bold;">비밀번호</td>
		<td><input style="border:none; width:99%" type="password" name="passwd"></td>
	</tr>
	<tr>
		<td align="center" style="font-weight:bold;">파일첨부</td>
		<td><input style="border:none; width:99%" type="file" name="file1"></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<button type="button" onClick="save();">등록하기</button>
		</td>
	</tr>
</table>
</form>