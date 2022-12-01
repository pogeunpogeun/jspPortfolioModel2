package project.memoMybatis.controller;

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
import project.memoMybatis.model.dao.MemoMybatisDAO;
import project.memoMybatis.model.dto.MemoMybatisDTO;

@WebServlet("/memoMybatis_servlet/*")
public class MemoMybatisController extends HttpServlet {
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
	    	  MemoMybatisDTO arguDto2 = new MemoMybatisDTO();
	    	  arguDto2.setSearchGubun(searchGubun);
	    	  arguDto2.setSearchData(searchData);
	    	  
	    	  MemoMybatisDAO dao = new MemoMybatisDAO();
	    	  
	    	  int pageSize = 10;
	    	  int blockSize = 10;
	    	  int totalRecord = dao.getTotalRecord(arguDto2);
	    	  request.setAttribute("totalRecord", totalRecord);
	    	  
	    	  Map<String, Integer> map =  util.getPagerMap(pageNumber, pageSize, blockSize, totalRecord);
	    	  map.put("blockSize", blockSize);
	    	  request.setAttribute("map", map);
	    	  
	    	  MemoMybatisDTO arguDto = new MemoMybatisDTO();
	    	  arguDto.setSearchGubun(searchGubun);
	    	  arguDto.setSearchData(searchData);
	    	  arguDto.setStartRecord(map.get("startRecord"));
	    	  arguDto.setLastRecord(map.get("lastRecord"));
	    	  
	    	  List<MemoMybatisDTO> list = dao.getSelectAll(arguDto);
	    	  request.setAttribute("list", list);
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("view")) {
	    	  String memoNo_ = request.getParameter("memoNo");
	    	  int memoNo = util.getNumberCheck(memoNo_, 0);
	    	  
	    	  if(memoNo == 0) {
	    		  System.out.println("memoNo : " + memoNo);
	    		  return;
	    	  }
	    	  MemoMybatisDTO arguDto = new MemoMybatisDTO();
	    	  arguDto.setMemoNo(memoNo);
	    	  arguDto.setSearchData(searchData);
	    	  arguDto.setSearchGubun(searchGubun);
	    	  
	    	  MemoMybatisDAO dao = new MemoMybatisDAO();
	    	  MemoMybatisDTO returnDto = dao.getSelectOne(arguDto);
	    	  
	    	  request.setAttribute("dto", returnDto);
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("chuga")) {
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("chugaProc")) {
	    	  String subject = request.getParameter("subject");
	    	  String writer = request.getParameter("writer");
	    	  String content = request.getParameter("content");
	    	  
	    	  subject = util.getNullBlankCheck(subject);
	    	  writer = util.getNullBlankCheck(writer);
	    	  content = util.getNullBlankCheck(content);
	    	  
	    	  int failCounter = 0;
	    	  if(subject.equals("")) { System.out.println("subject Null"); failCounter++; }
	    	  if(writer.equals("")) { System.out.println("writer Null"); failCounter++; }
	    	  if(content.equals("")) { System.out.println("content Null"); failCounter++; }
	    	  
	    	  if(failCounter > 0) {
	    		  System.out.println("failCounter : " + failCounter);
	    		  return;
	    	  }
	    	  subject = util.getCheckString(subject);
	    	  writer = util.getCheckString(writer);
	    	  content = util.getCheckString(content);
	    	  
	    	  MemoMybatisDTO arguDto = new MemoMybatisDTO();
	    	  arguDto.setWriter(writer);
	    	  arguDto.setSubject(subject);
	    	  arguDto.setContent(content);
	    	  
	    	  MemoMybatisDAO dao = new MemoMybatisDAO();
	    	  int result = dao.setInsert(arguDto);
	    	  
	    	  if(result > 0) {
	    		  response.sendRedirect(path + "/memoMybatis_servlet/memoMybatis_list.do");
	    	  } else {
	    		  response.sendRedirect(path + "/memoMybatis_servlet/memoMybatis_chuga.do?" + searchQuery);
	    	  }
	    	  
	      } else if(fileName.equals("sujung")) {
	    	  String memoNo_ = request.getParameter("memoNo");
	    	  int memoNo = util.getNumberCheck(memoNo_, 0);
	    	  
	    	  if(memoNo == 0) {
	    		  System.out.println("memoNo : " + memoNo);
	    		  return;
	    	  }
	    	  MemoMybatisDTO arguDto = new MemoMybatisDTO();
	    	  arguDto.setMemoNo(memoNo);
	    	  arguDto.setSearchData(searchData);
	    	  arguDto.setSearchGubun(searchGubun);
	    	  
	    	  MemoMybatisDAO dao = new MemoMybatisDAO();
	    	  MemoMybatisDTO returnDto = dao.getSelectOne(arguDto);
	    	  
	    	  request.setAttribute("dto", returnDto);
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("sujungProc")) {
	    	  String memoNo_ = request.getParameter("memoNo");
	    	  int memoNo = util.getNumberCheck(memoNo_, 0);
	    	  
	    	  if(memoNo == 0) {
	    		  System.out.println("memoNo : " + memoNo);
	    		  return;
	    	  }
	    	  String subject = request.getParameter("subject");
	    	  String content = request.getParameter("content");
	    	  
	    	  subject = util.getNullBlankCheck(subject);
	    	  content = util.getNullBlankCheck(content);
	    	  
	    	  int failCounter = 0;
	    	  if(subject.equals("")) { System.out.println("subject null"); failCounter++; }
	    	  if(content.equals("")) { System.out.println("content null"); failCounter++; }
	    	  
	    	  if(failCounter > 0) {
	    		  return;
	    	  }
	    	  
	    	  subject = util.getCheckString(subject);
	    	  content = util.getCheckString(content);
	    	  
	    	  MemoMybatisDTO arguDto = new MemoMybatisDTO();
	    	  arguDto.setMemoNo(memoNo);
	    	  arguDto.setSubject(subject);
	    	  arguDto.setContent(content);
	    	  
	    	  MemoMybatisDAO dao = new MemoMybatisDAO();
	    	  int result = dao.setUpdate(arguDto);
	    	  
	    	  if(result > 0) {
	    		  response.sendRedirect(path + "/memoMybatis_servlet/memoMybatis_view.do?memoNo=" + memoNo + "&" + searchQuery);
	    	  } else {
	    		  response.sendRedirect(path + "/memoMybatis_servlet/memoMybatis_sujung.do?memoNo=" + memoNo + "&" + searchQuery);
	    	  }
	    	  
	      } else if(fileName.equals("sakje")) {
	    	  String memoNo_ = request.getParameter("memoNo");
	    	  int memoNo = util.getNumberCheck(memoNo_, 0);
	    	  
	    	  if(memoNo == 0) {
	    		  System.out.println("memoNo : " + memoNo);
	    		  return;
	    	  }
	    	  MemoMybatisDTO arguDto = new MemoMybatisDTO();
	    	  arguDto.setMemoNo(memoNo);
	    	  //arguDto.setSearchData(searchData);
	    	  //arguDto.setSearchGubun(searchGubun);
	    	  
	    	  MemoMybatisDAO dao = new MemoMybatisDAO();
	    	  MemoMybatisDTO returnDto = dao.getSelectOne(arguDto);
	    	  
	    	  request.setAttribute("dto", returnDto);
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
	    	  rd.forward(request, response);
	      } else if(fileName.equals("sakjeProc")) {
	    	  String memoNo_ = request.getParameter("memoNo");
	    	  int memoNo = util.getNumberCheck(memoNo_, 0);
	    	  
	    	  if(memoNo == 0) {
	    		  System.out.println("memoNo : " + memoNo);
	    		  return;
	    	  }
	    	  MemoMybatisDTO arguDto = new MemoMybatisDTO();
	    	  arguDto.setMemoNo(memoNo);
	    	  
	    	  MemoMybatisDAO dao = new MemoMybatisDAO();
	    	  int result = dao.setDelete(arguDto);
	    	  
	    	  if(result > 0) {
	    		  response.sendRedirect(path + "/memoMybatis_servlet/memoMybatis_list.do");
	    	  } else {
	    		  response.sendRedirect(path + "/memoMybatis_servlet/memoMybatis_sakje.do?memoNo=" + memoNo + "&" + searchQuery);
	    	  }
	      } else if(fileName.equals("search")) {  
	    	  
	    	  String moveUrl = path + "/memoMybatis_servlet/memoMybatis_list.do?" + searchQuery;
	    	  response.sendRedirect(moveUrl);
	    	  
	      } else {
	    	  System.out.println("-- 존재하지 않는 URL --");
	      }
	}

}

			 	   