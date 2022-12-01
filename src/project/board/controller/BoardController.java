package project.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.common.Util;


@WebServlet("/board_servlet/*")
public class BoardController extends HttpServlet {
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
			  
			  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			  rd.forward(request, response);
		  } else if(fileName.equals("chuga")) {
			  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			  rd.forward(request, response);
		  } else if(fileName.equals("chugaProc")) {
			  String writer = request.getParameter("writer");
			  String email = request.getParameter("email");
			  String subject = request.getParameter("email");
			  String content = request.getParameter("content");
			  String passwd = request.getParameter("passwd");
			  
		  } else if(fileName.equals("view")) {
			  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			  rd.forward(request, response);
		  } else if(fileName.equals("sujung")) {
			  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			  rd.forward(request, response);
		  } else if(fileName.equals("sujungProc")) {
			  
		  } else if(fileName.equals("sakje")) {
			  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			  rd.forward(request, response);
		  } else if(fileName.equals("sakjeProc")) {
			  
		  } else if(fileName.equals("search")) {
			  
		  }
		}

}
