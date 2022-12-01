package project.index.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project.common.Util;


@WebServlet("/")
public class indexController extends HttpServlet {
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
      
      folderName = "dashBoard";
      fileName = "index";
      
      request.setAttribute("fileName", fileName);
      request.setAttribute("folderName", folderName);
      request.setAttribute("path", path);
      request.setAttribute("referer", referer);
      
      String forwardPage = "/WEB-INF/project/main/main.jsp";
      RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
      rd.forward(request, response);
      
   }
}
