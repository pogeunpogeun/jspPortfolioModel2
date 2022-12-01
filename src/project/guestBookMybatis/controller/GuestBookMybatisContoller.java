package project.guestBookMybatis.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.common.Util;
import project.guestBookMybatis.model.dao.GuestBookMybatisDAO;
import project.guestBookMybatis.model.dto.GuestBookMybatisDTO;


@WebServlet("/guestBookMybatis_servlet/*")
public class GuestBookMybatisContoller extends HttpServlet {
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
    	  String searchQuery2 = "";
    	  if(imsiSearchYN.equals("O")) {
    		  String imsiSearchGubun = URLEncoder.encode(searchGubun, "UTF-8");
    		  String imsiSearchData = URLEncoder.encode(searchData, "UTF-8");
    		  searchQuery = "pageNumber=" + pageNumber + "&searchGubun=" + imsiSearchGubun + "&searchData=" + imsiSearchData;
    		  searchQuery2 = "&searchGubun=" + imsiSearchGubun + "&searchData=" + imsiSearchData;
    	  }
    	  
    	  request.setAttribute("searchGubun", searchGubun);
    	  request.setAttribute("searchData", searchData);
    	  request.setAttribute("searchQuery", searchQuery);
    	  request.setAttribute("searchQuery2", searchQuery2);
    	  
	      String forwardPage = "/WEB-INF/project/main/main.jsp";
	      if(fileName.equals("list")) {	 
	    	  GuestBookMybatisDTO arguDto2 = new GuestBookMybatisDTO();
	    	  arguDto2.setSearchGubun(searchGubun);
	    	  arguDto2.setSearchData(searchData);
	    	  
	    	  GuestBookMybatisDAO dao = new GuestBookMybatisDAO();
	    	  
	    	  int pageSize = 3;
	    	  int blockSize = 10;
	    	  int totalRecord = dao.getTotalRecord(arguDto2);	    	  
	    	  request.setAttribute("totalRecord", totalRecord);
	    	  
	    	  Map<String, Integer> map = util.getPagerMap(pageNumber, pageSize, blockSize, totalRecord);
	    	  map.put("blockSize", blockSize);
	    	  request.setAttribute("map", map);
	    	  
	    	  GuestBookMybatisDTO arguDto = new GuestBookMybatisDTO();
	    	  arguDto.setSearchData(searchData);
	    	  arguDto.setSearchGubun(searchGubun);
	    	  arguDto.setStartRecord(map.get("startRecord"));
	    	  arguDto.setLastRecord(map.get("lastRecord"));
	    	  
	    	  List<GuestBookMybatisDTO> list = dao.getSelectAll(arguDto);    	  
	    	  request.setAttribute("list", list);
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("view")) {
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("chuga")) {
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("chugaProc")) {
	    	  String name = request.getParameter("name");
	    	  String email = request.getParameter("email");
	    	  String content = request.getParameter("content");
	    	  String passwd = request.getParameter("passwd");
	    	  
	    	  name = util.getNullBlankCheck(name);
	    	  email = util.getNullBlankCheck(email);
	    	  content = util.getNullBlankCheck(content);
	    	  passwd = util.getNullBlankCheck(passwd);
	    	  
	    	  int failCounter = 0;
	    	  if(name.equals("")) { System.out.println("name null"); failCounter++; }
	    	  if(email.equals("")) { 
	    		  email = "-";
	    	  }
	    	  if(content.equals("")) { System.out.println("content null"); failCounter++; }
	    	  if(passwd.equals("")) { System.out.println("passwd null"); failCounter++; }
	    	  
	    	  if(failCounter > 0) {
	    		  return;
	    	  }
	    	  
	    	  name = util.getCheckString(name);
	    	  email = util.getCheckString(email);
	    	  content = util.getCheckString(content);
	    	  passwd = util.getCheckString(passwd);
	    	  int memberNo = 0;
	    	  
	    	  GuestBookMybatisDTO arguDto = new GuestBookMybatisDTO();
	    	  arguDto.setName(name);
	    	  arguDto.setEmail(email);
	    	  arguDto.setContent(content);
	    	  arguDto.setPasswd(passwd);
	    	  arguDto.setMemberNo(memberNo);
	    	  
	    	  GuestBookMybatisDAO dao = new GuestBookMybatisDAO();
	    	  int result = dao.setInsert(arguDto);
	    	  
	    	  if(result > 0) {
	    		  response.sendRedirect(path + "/guestBookMybatis_servlet/guestBookMybatis_list.do?" + searchQuery);
	    	  } else {
	    		  response.sendRedirect(path + "/guestBookMybatis_servlet/guestBookMybatis_chuga.do?" + searchQuery);
	    	  }
	    	  
	      } else if(fileName.equals("sujung")) {
	    	  String no_ = request.getParameter("no");
	    	  int no = util.getNumberCheck(no_, 0);
	    	  
	    	  if(no == 0) {
	    		  return;
	    	  }
	    	  
	    	  GuestBookMybatisDTO arguDto = new GuestBookMybatisDTO();
	    	  arguDto.setNo(no);
	    	  
	    	  GuestBookMybatisDAO dao = new GuestBookMybatisDAO();
	    	  GuestBookMybatisDTO returnDto = dao.getSelectOne(arguDto);
	    	  
	    	  request.setAttribute("dto", returnDto);
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("sujungProc")) {
	    	  String no_ = request.getParameter("no");
	    	  int no = util.getNumberCheck(no_, 0);
	    	  
	    	  if(no == 0) {
	    		  return;
	    	  }
	    	  String name = request.getParameter("name");
	    	  String email = request.getParameter("email");
	    	  String content = request.getParameter("content");
	    	  String passwd = request.getParameter("passwd");
	    	  
	    	  name = util.getNullBlankCheck(name);
	    	  email = util.getNullBlankCheck(email);
	    	  content = util.getNullBlankCheck(content);
	    	  passwd = util.getNullBlankCheck(passwd);
	    	  
	    	  int failCounter = 0;
	    	  if(name.equals("")) { System.out.println("name null"); failCounter++; }
	    	  if(email.equals("")) { 
	    		  email = "-";
	    	  }
	    	  if(content.equals("")) { System.out.println("content null"); failCounter++; }
	    	  if(passwd.equals("")) { System.out.println("passwd null"); failCounter++; }
	    	  
	    	  if(failCounter > 0) {
	    		  return;
	    	  }
	    	  
	    	  name = util.getCheckString(name);
	    	  email = util.getCheckString(email);
	    	  content = util.getCheckString(content);
	    	  passwd = util.getCheckString(passwd);
	    	  int memberNo = 0;
	    	  
	    	  GuestBookMybatisDTO arguDto = new GuestBookMybatisDTO();
	    	  arguDto.setNo(no);
	    	  arguDto.setName(name);
	    	  arguDto.setEmail(email);
	    	  arguDto.setContent(content);
	    	  arguDto.setPasswd(passwd);
	    	  arguDto.setMemberNo(memberNo);
	    	  
	    	  GuestBookMybatisDAO dao = new GuestBookMybatisDAO();
	    	  int result = dao.setUpdate(arguDto);
	    	  
	    	  if(result > 0) {
	    		  response.sendRedirect(path + "/guestBookMybatis_servlet/guestBookMybatis_list.do?" + searchQuery);
	    	  } else {
	    		  response.sendRedirect(path + "/guestBookMybatis_servlet/guestBookMybatis_sujung.do?no=" + no + "&" + searchQuery);
	    	  }
	      } else if(fileName.equals("sakje")) {
	    	  String no_ = request.getParameter("no");
	    	  int no = util.getNumberCheck(no_, 0);
	    	  
	    	  if(no == 0) {
	    		  return;
	    	  }
	    	  
	    	  GuestBookMybatisDTO arguDto = new GuestBookMybatisDTO();
	    	  arguDto.setNo(no);
	    	  
	    	  GuestBookMybatisDAO dao = new GuestBookMybatisDAO();
	    	  GuestBookMybatisDTO returnDto = dao.getSelectOne(arguDto);
	    	  
	    	  request.setAttribute("dto", returnDto);
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("sakjeProc")) {
	    	  String no_ = request.getParameter("no");
	    	  int no = util.getNumberCheck(no_, 0);
	    	  
	    	  if(no == 0) {
	    		  return;
	    	  }
	    	  
	    	  String passwd = request.getParameter("passwd");
	    	  
	    	  passwd = util.getNullBlankCheck(passwd);
	    	  
	    	  GuestBookMybatisDTO arguDto2 = new GuestBookMybatisDTO();
	    	  arguDto2.setNo(no);
	    	  
	    	  GuestBookMybatisDAO dao = new GuestBookMybatisDAO();
	    	  GuestBookMybatisDTO returnDto = dao.getSelectOne(arguDto2);
	    	  
	    	  if(passwd.equals("")) {
	    		  System.out.println("password null");
	    		  return;
	    	  }
	    	  if(!passwd.equals(returnDto.getPasswd())) {
	    		  System.out.println("password wrong");
	    	  }
	    	  GuestBookMybatisDTO arguDto = new GuestBookMybatisDTO();
	    	  arguDto.setPasswd(passwd);
	    	  arguDto.setNo(no);
	    	  
	    	  int result = dao.setDelete(arguDto);
	    	  
	    	  if(result > 0) {
	    		  response.sendRedirect(path + "/guestBookMybatis_servlet/guestBookMybatis_list.do?" + searchQuery2);
	    	  } else {
	    		  response.sendRedirect(path + "/guestBookMybatis_servlet/guestBookMybatis_sakje.do?no=" + no + "&" + searchQuery);
	    	  }
	    	  
	      	  } else if(fileName.equals("search")) {
	      		String moveUrl = path + "/guestBookMybatis_servlet/guestBookMybatis_list.do?" + searchQuery;
		    	response.sendRedirect(moveUrl);
	    	  
	      }
	}

}
