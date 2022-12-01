package project.noLogin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import project.common.Util;
import project.member.model.dao.MemberDAO;
import project.member.model.dto.MemberDTO;

@WebServlet("/noLogin_servlet/*")
public class NoLoginController extends HttpServlet {
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
	      
	      request.setAttribute("fileName", fileName);
	      request.setAttribute("folderName", folderName);
	      request.setAttribute("path", path);
	      request.setAttribute("ip", ip);
	      request.setAttribute("referer", referer);
	      
	      String forwardPage = "/WEB-INF/project/main/main.jsp";
	      if(fileName.equals("login")) {	
	    	  
	    	  RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
		      rd.forward(request, response);		      
	      } else if(fileName.equals("loginProc")) {
	    	  String id = request.getParameter("id");
	    	  String passwd = request.getParameter("passwd");
	    	  
	    	  id = util.getNullBlankCheck(id);
	    	  passwd = util.getNullBlankCheck(passwd);
	    	  
	    	  id = util.getCheckString(id);
	    	  passwd = util.getCheckString(passwd);
	    	  
	    	  MemberDTO arguDto = new MemberDTO();
	    	  arguDto.setId(id);
	    	  arguDto.setPasswd(passwd);
	    	  
	    	  MemberDAO dao = new MemberDAO();
	    	  MemberDTO returnDto = dao.getLogin(arguDto);
	    	  
	    	  String msg = "아이디 또는 비밀번호가 다릅니다.\\n 다시 이용해주세요.";
	    	  if(returnDto.getNo() > 0) {
	    		  msg = "정상적으로 로그인되었습니다.";
	    		  HttpSession session = request.getSession();
	    		  //session.setMaxInactiveInterval(1*60);
	    		  
	    		  session.setAttribute("sessionNo", returnDto.getNo());
	    		  session.setAttribute("sessionId", returnDto.getId());
	    		  session.setAttribute("sessionName", returnDto.getName());
	    	  }
	    	  response.setContentType("text/html; charset=utf-8");
	    	  PrintWriter out = response.getWriter();
	    	  out.println("<script>");
	    	  out.println("alert('" + msg + "');");
	    	  out.println("location.href = '" + path + "';");
	    	  out.println("</script>");
	    	  out.flush();
	    	  out.close();
	    	  return;
	      } else if(fileName.equals("logout")) {
	    	  HttpSession session = request.getSession();
    		  session.invalidate();
    		  
    		  response.setContentType("text/html; charset=utf-8");
	    	  PrintWriter out = response.getWriter();
	    	  out.println("<script>");
	    	  out.println("alert('로그아웃 되었습니다.\\n 즐거운 하루 되세요.');");
	    	  out.println("location.href = '" + path + "';");
	    	  out.println("</script>");
	    	  out.flush();
	    	  out.close();
	    	  return;
	      }
	}
}
