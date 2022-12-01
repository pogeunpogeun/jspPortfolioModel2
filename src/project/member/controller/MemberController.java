package project.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.common.Util;
import project.member.model.dao.MemberDAO;
import project.member.model.dto.MemberDTO;


@WebServlet("/member_servlet/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doProc(request, response);
	}
	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		request.setCharacterEncoding("UTF-8");

	      Util util = new Util();
	      
	      String[] serverInfo = util.getServerInfo(request);
	      String referer = serverInfo[0];
	      String path = serverInfo[1];
	      String url = serverInfo[2];
	      String uri = serverInfo[3];
	      String ip = serverInfo[4];
	      String ip6 = serverInfo[5];
	      String folderName = serverInfo[6];
	      String fileName = serverInfo[7];
	      
	      String[] sessionArray = util.getSessionChk(request);
	      int sessionNo = Integer.parseInt(sessionArray[0]);
	      String sessionId = sessionArray[1];
	      String sessionName = sessionArray[2];
	      
	      if(sessionNo <= 0) { //로그인 안한 상태
	    	  //response.sendRedirect(path + "/noLogin_servlet/noLogin_login.do");
	    	  
	    	  response.setContentType("text/html; charset=utf-8");
	    	  PrintWriter out = response.getWriter();
	    	  out.println("<script>");
	    	  out.println("alert('로그인 후 이용하세요.');");
	    	  out.println("location.href='" + path + "';");
	    	  out.println("</script>");
	    	  out.flush();
	    	  out.close();
	    	  return;
	      }
	      
	      request.setAttribute("fileName", fileName);
	      request.setAttribute("folderName", folderName);
	      request.setAttribute("path", path);
	      request.setAttribute("ip", ip);
	      request.setAttribute("referer", referer);
	      
	      String pageNumber_ = request.getParameter("pageNumber");
	      int pageNumber = util.getNumberCheck(pageNumber_, 1);
	      request.setAttribute("pageNumber", pageNumber);
	      
	      String searchGubun = request.getParameter("searchGubun");
    	  String searchData = request.getParameter("searchData");
    	  
    	  String imsiSearchYN = "O";
    	  searchGubun = util.getNullBlankCheck(searchGubun);
    	  searchData = util.getNullBlankCheck(searchData);
    	  if (searchGubun.equals("") || searchData.equals("")) {
    		  imsiSearchYN = "X";
    		  searchGubun = "";
    		  searchData = "";    		  
    	  }
    	  searchGubun = URLDecoder.decode(searchGubun, "UTF-8");
    	  searchData = URLDecoder.decode(searchData, "UTF-8");

    	  String searchQuery = "pageNumber=" + pageNumber + "&searchGubun=&searchData=";
    	  if(imsiSearchYN.equals("O")) {
    		  String imsiSearchGubun = URLEncoder.encode(searchGubun, "UTF-8");
    		  String imsiSearchData = URLEncoder.encode(searchData, "UTF-8");
    		  searchQuery = "pageNumber=" + pageNumber;
    		  searchQuery = "&searchGubun=" + imsiSearchGubun + "&searchData=" + imsiSearchData;
    	  }
    	  
    	  request.setAttribute("searchGubun", searchGubun);
    	  request.setAttribute("searchData", searchData);
    	  request.setAttribute("searchQuery", searchQuery);
    	  
	      String forwardPage = "/WEB-INF/project/main/main.jsp";
	      if(fileName.equals("list")) {	    	  
	    	  MemberDTO arguDto2 = new MemberDTO();
	    	  arguDto2.setSearchGubun(searchGubun);
	    	  arguDto2.setSearchData(searchData);
	    	  
	    	  MemberDAO dao = new MemberDAO();
	    	  
	    	  int pageSize = 3;
	    	  int blockSize = 10;
	    	  int totalRecord = dao.getTotalRecord(arguDto2);
	    	  request.setAttribute("totalRecord", totalRecord);
	    	  
	    	  Map<String, Integer> map =  util.getPagerMap(pageNumber, pageSize, blockSize, totalRecord);
	    	  map.put("blockSize", blockSize);
	    	  request.setAttribute("map", map);
	    	  
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setSearchGubun(searchGubun);
	    	  arguDto.setSearchData(searchData);
	    	  arguDto.setStartRecord(map.get("startRecord"));
	    	  arguDto.setLastRecord(map.get("lastRecord"));
	    	  
	    	  ArrayList<MemberDTO> list = dao.getSelectAll(arguDto);    	  
	    	  request.setAttribute("list", list);
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
		      rd.forward(request, response);
	      } else if(fileName.equals("view")) {	    
	    	  MemberDTO arguDto2 = new MemberDTO();
	    	  arguDto2.setSearchGubun(searchGubun);
	    	  arguDto2.setSearchData(searchData);
	    	  
	    	  String no_ = request.getParameter("no");
	    	  int no = util.getNumberCheck(no_, 0);
	    	  
	    	  if(no == 0) {
	    		  System.out.println("no : " + no);
	    		  return;
	    	  }
	    	  
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setNo(no);
	    	  
	    	  MemberDAO dao = new MemberDAO();
	    	  arguDto.setSearchGubun(searchGubun);
	    	  arguDto.setSearchData(searchData);
	    	  
	    	  MemberDTO dto = dao.getSelectOne(arguDto);
	    	  
	    	  request.setAttribute("dto", dto);
	    	  
		      RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
		      rd.forward(request, response);
	      } else if(fileName.equals("chuga")) {
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
		      rd.forward(request, response);
	      } else if(fileName.equals("sujung")) {
	    	  String no_ = request.getParameter("no");
	    	  int no = util.getNumberCheck(no_, 0);
	    	  
	    	  if (no == 0) {
	    		  System.out.println("no : " + no);
	    		  return;
	    	  }
	    	
//	    	  if(no == 0) {	    		  
//	    		  if(sessionNo > 0) {
//	    			  no = sessionNo;
//	    		  } else {
//	    			  System.out.println("no : " + no);
//	    			  return;
//	    			  
//	    		  }
//	    	  }
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setNo(no);
	    	  arguDto.setSearchData(searchData);
	    	  arguDto.setSearchGubun(searchGubun);
	    	  MemberDAO dao = new MemberDAO();
	    	  MemberDTO dto = dao.getSelectOne(arguDto);
	    	  
	    	  request.setAttribute("dto", dto);
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
		      rd.forward(request, response);
	      } else if(fileName.equals("sakje")) {
	    	  String no_ = request.getParameter("no");
	    	  int no = util.getNumberCheck(no_, 0);
	    	  
	    	  if(no == 0) {
	    		  if(sessionNo > 0) {
	    			  no = sessionNo;
	    		  } else {
	    			  System.out.println("no : " + no);
	    			  return;	    			  
	    		  }
	    	  }
	    	  
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setNo(no);
	    	  arguDto.setSearchData(searchData);
	    	  arguDto.setSearchGubun(searchGubun);
	    	  MemberDAO dao = new MemberDAO();
	    	  MemberDTO dto = dao.getSelectOne(arguDto);
	    	  
	    	  request.setAttribute("dto", dto);
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
		      rd.forward(request, response);
	      } else if(fileName.equals("search")) {
	    	  
	    	  String moveUrl = path + "/member_servlet/member_list.do?" + searchQuery;
	    	  response.sendRedirect(moveUrl);
	    	  	    	  
	      } else if(fileName.equals("chugaProc")) {
	    	  String id = request.getParameter("id");
	    	  String tempId = request.getParameter("tempId");  	  
	    	  String passwd = request.getParameter("passwd");
	    	  String passwdChk = request.getParameter("passwdChk");
	    	  String name = request.getParameter("name");
	    	  String jumin1 = request.getParameter("jumin1");
	    	  String jumin2 = request.getParameter("jumin2");
	    	  String phone1 = request.getParameter("phone1");
	    	  String phone2 = request.getParameter("phone2");
	    	  String phone3 = request.getParameter("phone3");
	    	  String email1 = request.getParameter("email1");
	    	  String email2 = request.getParameter("email2");
	    	  
	    	  String postcode = request.getParameter("sample6_postcode");
	    	  String address = request.getParameter("sample6_address");
	    	  String detailAddress = request.getParameter("sample6_detailAddress");
	    	  String extraAddress = request.getParameter("sample6_extraAddress");
	    	  
	    	  id = util.getNullBlankCheck(id);
	    	  tempId = util.getNullBlankCheck(tempId);
	    	  passwd = util.getNullBlankCheck(passwd);
	    	  passwdChk = util.getNullBlankCheck(passwdChk);
	    	  name = util.getNullBlankCheck(name);
	    	  jumin1 = util.getNullBlankCheck(jumin1);
	    	  jumin2 = util.getNullBlankCheck(jumin2);
	    	  phone1 = util.getNullBlankCheck(phone1);
	    	  phone2 = util.getNullBlankCheck(phone2);
	    	  phone3 = util.getNullBlankCheck(phone3);
	    	  email1 = util.getNullBlankCheck(email1);
	    	  email2 = util.getNullBlankCheck(email2);
	    	  postcode = util.getNullBlankCheck(postcode);
	    	  address = util.getNullBlankCheck(address);
	    	  detailAddress = util.getNullBlankCheck(detailAddress);
	    	  extraAddress = util.getNullBlankCheck(extraAddress);
	    	  
	    	  int failCounter = 0;
	    	  if(id.equals("")) { System.out.println("id null"); failCounter++; }
	    	  if(tempId.equals("")) { System.out.println("tempId null"); failCounter++; }
	    	  if(passwd.equals("")) { System.out.println("passwd null"); failCounter++; }
	    	  if(passwdChk.equals("")) { System.out.println("passwdChk null"); failCounter++; }
	    	  if(name.equals("")) { System.out.println("name null"); failCounter++; }
	    	  if(jumin1.equals("")) { System.out.println("jumin1 null");failCounter++; }
	    	  if(jumin2.equals("")) { System.out.println("jumin2 null"); failCounter++; }
	    	  if(phone1.equals("")) { System.out.println("phone1 null"); failCounter++; }
	    	  if(phone2.equals("")) { System.out.println("phone2 null"); failCounter++; }
	    	  if(phone3.equals("")) { System.out.println("phone3 null"); failCounter++; }
	    	  if(email1.equals("")) { System.out.println("email1 null"); failCounter++; }
	    	  if(email2.equals("")) { System.out.println("email2 null"); failCounter++; }
	    	  if(postcode.equals("")) { System.out.println("postcode null"); failCounter++; }
	    	  if(address.equals("")) { System.out.println("address null"); failCounter++; }
	    	  if(detailAddress.equals("")) { System.out.println("detailAddress null"); failCounter++; }
	    	  if(extraAddress.equals("")) { System.out.println("extraAddress null"); extraAddress = "-"; }
	    	  
	    	  
	    	  if(!id.equals(tempId)) {
	    		  System.out.println("id != tempId");
	    		  failCounter++;
	    	  }	    	  
	    	  if(passwd.equals(passwdChk) && !passwd.equals("")) {
	    		 
	    	  } else {
	    		  System.out.println("passwd != passwdChk || passwd == null");
	    		  failCounter++;
	    	  }
	    	  int imsiJumin1 = util.getNumberCheck(jumin1, 0);
	    	  if(jumin1.length() == 6 && imsiJumin1 > 0) {

	    	  } else {
	    		  System.out.println("jumin1 length !=6");
	    		  failCounter++;
	    	  }
	    	  int imsiJumin2 = util.getNumberCheck(jumin2, 0);
	    	  if(jumin2.length() == 1 && imsiJumin2 > 0) {
	    		  
	    	  } else {
	    		  System.out.println("jumin2 length != 1");
	    		  failCounter++;
	    	  }
	    	  if(phone1.equals("010") || phone1.equals("011") || phone1.equals("016")) {
	    		  
	    	  } else {
	    		  System.out.println("phone1 error");
	    		  failCounter++;
	    	  }
	    	  int imsiPhone2 = util.getNumberCheck(phone2, 0);
	    	  if(phone2.length() == 4 && imsiPhone2 >=0) {
	    		  
	    	  } else {
	    		  System.out.println("phone2 length != 4");
	    		  failCounter++;
	    	  }
	    	  int imsiPhone3 = util.getNumberCheck(phone3, 0);
	    	  if(phone3.length() == 4 && imsiPhone3 >=0) {
	    		  
	    	  } else {
	    		  System.out.println("phone3 length != 4");
	    		  failCounter++;
	    	  }
	    	  if(failCounter > 0) {
	    		  System.out.println("failCounter : " + failCounter);
	    		  return;
	    	  }
	    	  
	    	  id = util.getCheckString(id);
	    	  passwd = util.getCheckString(passwd);
	    	  name = util.getCheckString(name);
	    	  jumin1 = util.getCheckString(jumin1);
	    	  jumin2 = util.getCheckString(jumin2);
	    	  phone1 = util.getCheckString(phone1);
	    	  phone2 = util.getCheckString(phone2);
	    	  phone3 = util.getCheckString(phone3);
	    	  email1 = util.getCheckString(email1);
	    	  email2 = util.getCheckString(email2);
	    	  postcode = util.getCheckString(postcode);
	    	  address = util.getCheckString(address);
	    	  detailAddress = util.getCheckString(detailAddress);
	    	  extraAddress = util.getCheckString(extraAddress);
	    		
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setId(id);
	    	  arguDto.setPasswd(passwd);
	    	  arguDto.setName(name);
	    	  arguDto.setJumin1(jumin1);
	    	  arguDto.setJumin2(jumin2);
	    	  arguDto.setPhone1(phone1);
	    	  arguDto.setPhone2(phone2);
	    	  arguDto.setPhone3(phone3);
	    	  arguDto.setEmail1(email1);
	    	  arguDto.setEmail2(email2);
	    	  arguDto.setPostcode(postcode);
	    	  arguDto.setAddress(address);
	    	  arguDto.setDetailAddress(detailAddress);
	    	  arguDto.setExtraAddress(extraAddress);
	    	  
	    	  MemberDAO dao = new MemberDAO();
	    	  int result = dao.setInsert(arguDto);
	    	  
	    	  if(result > 0) {
	    		  response.sendRedirect(path + "/member_servlet/member_list.do");
	    	  } else {
	    		  response.sendRedirect(path + "/member_servlet/member_chuga.do?" + searchQuery);
	    	  }	    	  
	      } else if(fileName.equals("sujungProc")) {
	    	  String no_ = request.getParameter("no");
	    	  int no = util.getNumberCheck(no_, 0);
	    	  
	    	  if(no == 0) {
	    		  System.out.println("no : " + no);
	    		  return;
	    	  }
	    	  String passwd = request.getParameter("passwd");
	    	  String phone1 = request.getParameter("phone1");
	    	  String phone2 = request.getParameter("phone2");
	    	  String phone3 = request.getParameter("phone3");
	    	  String email1 = request.getParameter("email1");
	    	  String email2 = request.getParameter("email2");
	    	  
	    	  String postcode = request.getParameter("sample6_postcode");
	    	  String address = request.getParameter("sample6_address");
	    	  String detailAddress = request.getParameter("sample6_detailAddress");
	    	  String extraAddress = request.getParameter("sample6_extraAddress");
	    	  
	    	  passwd = util.getNullBlankCheck(passwd);
	    	  phone1 = util.getNullBlankCheck(phone1);
	    	  phone2 = util.getNullBlankCheck(phone2);
	    	  phone3 = util.getNullBlankCheck(phone3);
	    	  email1 = util.getNullBlankCheck(email1);
	    	  email2 = util.getNullBlankCheck(email2);
	    	  postcode = util.getNullBlankCheck(postcode);
	    	  address = util.getNullBlankCheck(address);
	    	  detailAddress = util.getNullBlankCheck(detailAddress);
	    	  extraAddress = util.getNullBlankCheck(extraAddress);
	    	  
	    	  int failCounter = 0;

	    	  if(passwd.equals("")) { System.out.println(2); failCounter++; }
	    	  if(phone1.equals("")) { System.out.println(7); failCounter++; }
	    	  if(phone2.equals("")) { System.out.println(8); failCounter++; }
	    	  if(phone3.equals("")) { System.out.println(9); failCounter++; }
	    	  if(email1.equals("")) { System.out.println(10); failCounter++; }
	    	  if(email2.equals("")) { System.out.println(11); failCounter++; }
	    	  if(postcode.equals("")) { System.out.println(12); failCounter++; }
	    	  if(address.equals("")) { System.out.println(13); failCounter++; }
	    	  if(detailAddress.equals("")) { System.out.println(14); failCounter++; }
	    	  if(extraAddress.equals("")) { System.out.println(15); extraAddress = "-"; }
	    	  
	    	  
	    	  if(phone1.equals("010") || phone1.equals("011") || phone1.equals("016")) {
	    		  
	    	  } else {
	    		  System.out.println(19);
	    		  failCounter++;
	    	  }
	    	  int imsiPhone2 = util.getNumberCheck(phone2, 0);
	    	  if(phone2.length() == 4 && imsiPhone2 >=0) {
	    		  
	    	  } else {
	    		  System.out.println(20);
	    		  failCounter++;
	    	  }
	    	  int imsiPhone3 = util.getNumberCheck(phone3, 0);
	    	  if(phone3.length() == 4 && imsiPhone3 >=0) {
	    		  
	    	  } else {
	    		  System.out.println(21);
	    		  failCounter++;
	    	  }
	    	  if(failCounter > 0) {
	    		  System.out.println("failCounter : " + failCounter);
	    		  return;
	    	  }
	    	  passwd = util.getCheckString(passwd);
	    	  phone1 = util.getCheckString(phone1);
	    	  phone2 = util.getCheckString(phone2);
	    	  phone3 = util.getCheckString(phone3);
	    	  email1 = util.getCheckString(email1);
	    	  email2 = util.getCheckString(email2);
	    	  postcode = util.getCheckString(postcode);
	    	  address = util.getCheckString(address);
	    	  detailAddress = util.getCheckString(detailAddress);
	    	  extraAddress = util.getCheckString(extraAddress);
	    		
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setNo(no);
	    	  arguDto.setPasswd(passwd);
	    	  arguDto.setPhone1(phone1);
	    	  arguDto.setPhone2(phone2);
	    	  arguDto.setPhone3(phone3);
	    	  arguDto.setEmail1(email1);
	    	  arguDto.setEmail2(email2);
	    	  arguDto.setPostcode(postcode);
	    	  arguDto.setAddress(address);
	    	  arguDto.setDetailAddress(detailAddress);
	    	  arguDto.setExtraAddress(extraAddress);
	    	  
	    	  MemberDAO dao = new MemberDAO();
	    	  MemberDTO returnDto = dao.getSelectOne(arguDto);
	    	  
	    	  if(!passwd.equals(returnDto.getPasswd())) {
	    		  System.out.println("패스워드체킹실패");
	    		  failCounter++;
	    	  }
	    	  int result = dao.setUpdate(arguDto);
	    	  
	    	  if(result > 0) {
	    		  response.sendRedirect(path + "/member_servlet/member_view.do?no=" + no + "&" + searchQuery);
	    	  } else {
	    		  response.sendRedirect(path + "/member_servlet/member_sujung.do?no=" + no + "&" + searchQuery);
	    	  }
	      } else if(fileName.equals("sakjeProc")) {
	    	  String no_ = request.getParameter("no");
	    	  int no = util.getNumberCheck(no_, 0);
	    	  
	    	  if(no == 0) {
	    		  System.out.println("no : " + no);
	    		  return;
	    	  }
	    	  String passwd = request.getParameter("passwd");
	    	  passwd = util.getNullBlankCheck(passwd);
	    	  
	    	  int failCounter = 0;
	    	  if(failCounter > 0) {
	    		  System.out.println("failCounter : " + failCounter);
	    		  return;
	    	  }
	    	  
	    	  passwd = util.getCheckString(passwd);
	    	  
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setNo(no);
	    	  arguDto.setPasswd(passwd);
	    	  
	    	  MemberDAO dao = new MemberDAO();
	    	  MemberDTO returnDto = dao.getSelectOne(arguDto);
	    	  
	    	  if(!passwd.equals(returnDto.getPasswd())) {
	    		  failCounter++;
	    		  System.out.println("failCounter : " + failCounter);
	    		  return;
	    	  }
	    	  
	    	  int result = dao.setDelete(arguDto);
	    	  
	    	  if(result > 0) {
	    		  response.sendRedirect(path + "/member_servlet/member_list.do");
	    	  } else {
	    		  response.sendRedirect(path + "/member_servlet/member_sakje.do?no=" + no);
	    	  }
	      } else if(fileName.equals("idCheck")) {
	    	  String id = request.getParameter("id");
	    	  id = util.getNullBlankCheck(id);
	    	  
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setId(id);
	    	  
	    	  MemberDAO dao = new MemberDAO();
	    	  int result = dao.getIdCheckWin(arguDto);
	    	  
	    	  response.setContentType("text/html; charset=utf-8");
	    	  PrintWriter out = response.getWriter();
	    	  out.println(result);
	    	  out.flush();
	    	  out.close();
	    	  return; 
	      } else if(fileName.equals("idCheckWin")) {
	    	  request.setAttribute("imsiId", "");
	    	  
	    	  forwardPage = "/WEB-INF/project/member/idCheckWin.jsp";
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("idCheckWinProc")) {
	    	  String id = request.getParameter("id");
	    	  id = util.getNullBlankCheck(id);
	    	  
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setId(id);
	    	  
	    	  MemberDAO dao = new MemberDAO();
	    	  int result = dao.getIdCheckWin(arguDto);
	    	  
	    	  String imsiId = id;
	    	  String msg = "사용 가능한 아이디입니다.";
	    	  if(result > 0) {
	    		  imsiId = "";
	    		  msg = "사용 불가능한 아이디입니다.";
	    	  }
	    	  request.setAttribute("id", id);
	    	  request.setAttribute("msg", msg);
	    	  request.setAttribute("imsiId", imsiId);
	    	  
	    	  forwardPage = "/WEB-INF/project/member/idCheckWin.jsp";
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);	    	  
	      }
	}

}
