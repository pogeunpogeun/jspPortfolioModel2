package project.memo.controller;

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
import project.memo.model.dao.MemoDAO;
import project.memo.model.dto.MemoDTO;

@WebServlet("/memo_servlet/*")
public class MemoController extends HttpServlet {
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
		
		if(sessionNo <= 0) {
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
		
		if(searchGubun.equals("") || searchData.equals("")) {
			imsiSearchYN = "X";
			searchData = "";
			searchGubun = "";
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
			MemoDTO arguDto2 = new MemoDTO();
			arguDto2.setSearchGubun(searchGubun);
			arguDto2.setSearchData(searchData);
			
			MemoDAO dao = new MemoDAO();
			
			int pageSize = 3;
			int blockSize = 10;
			int totalRecord = dao.getTotalRecord(arguDto2);
			request.setAttribute("totalRecord", totalRecord);
						
			Map<String, Integer> map = util.getPagerMap(pageNumber, pageSize, blockSize, totalRecord);
			map.put("blockSize", blockSize);
			request.setAttribute("map", map);
			
			MemoDTO arguDto = new MemoDTO();
			arguDto.setSearchGubun(searchGubun);
			arguDto.setSearchData(searchData);
			arguDto.setStartRecord(map.get("startRecord"));
			arguDto.setLastRecord(map.get("lastRecord"));
			
			ArrayList<MemoDTO> list = dao.getSelectAll(arguDto);		
			request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			rd.forward(request, response);
		} else if(fileName.equals("view")) {
			MemoDTO arguDto2 = new MemoDTO();
			arguDto2.setSearchGubun(searchGubun);
			arguDto2.setSearchData(searchData);
			
			String memoNo_ = request.getParameter("memoNo");
			int memoNo = util.getNumberCheck(memoNo_, 0);
			
			int failCounter = 0;
			if(memoNo < 0) {
				System.out.println("memoNo null");
				failCounter++;
				return;
			}
			MemoDTO arguDto = new MemoDTO();
			arguDto.setMemoNo(memoNo);
			
			MemoDAO dao = new MemoDAO();		
			arguDto.setSearchGubun(searchGubun);
			arguDto.setSearchData(searchData);
			
			MemoDTO viewDto = dao.getSelectOne(arguDto);
			
			request.setAttribute("viewDto", viewDto);
							
			RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			rd.forward(request, response);
		} else if(fileName.equals("chuga")) {
			String subject = request.getParameter("subject");
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			
			RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			rd.forward(request, response);
		} else if(fileName.equals("sujung")) {			
			String memoNo_ = request.getParameter("memoNo");
			int memoNo = util.getNumberCheck(memoNo_, 0);
			
			int failCounter = 0;
			if(memoNo == 0) {
				System.out.println("memoNo null");
				failCounter++;
				return;
			}
			MemoDTO arguDto = new MemoDTO();
			arguDto.setMemoNo(memoNo);
			arguDto.setSearchGubun(searchGubun);
			arguDto.setSearchData(searchData);
			MemoDAO dao = new MemoDAO();
			MemoDTO dto = dao.getSelectOne(arguDto);
			
			request.setAttribute("sujungDto", dto);
			
			RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			rd.forward(request, response);
		} else if(fileName.equals("sakje")) {
			String memoNo_ = request.getParameter("memoNo");
			int memoNo = util.getNumberCheck(memoNo_, 0);
			
			int failCounter = 0;
			if(memoNo == 0) {
				System.out.println("memoNo null");
				failCounter++;
				return;
			}
			MemoDTO arguDto = new MemoDTO();
			arguDto.setMemoNo(memoNo);
			arguDto.setSearchGubun(searchGubun);
			arguDto.setSearchData(searchData);
			MemoDAO dao = new MemoDAO();
			MemoDTO sakjeDto = dao.getSelectOne(arguDto);
			
			request.setAttribute("sakjeDto", sakjeDto);
			
			RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			rd.forward(request, response);
		} else if(fileName.equals("search")) {
			String moveUrl = path + "/memo_servlet/memo_list.do?" + searchQuery;
			response.sendRedirect(moveUrl);
		} else if(fileName.equals("chugaProc")) {
			String subject = request.getParameter("subject");
			String writer = request.getParameter("writer");
			String content = request.getParameter("content");
			
			subject = util.getNullBlankCheck(subject);
			writer = util.getNullBlankCheck(writer);
			content = util.getNullBlankCheck(content);
			
			int failCounter = 0;
			
			if(subject.equals("")) { System.out.println("subject null"); failCounter++; }
			if(writer.equals("")) { System.out.println("writer null"); failCounter++; }
			if(content.equals("")) { System.out.println("content null"); failCounter++; }
			
			subject = util.getCheckString(subject);
			writer = util.getCheckString(writer);
			content = util.getCheckString(content);
			
			MemoDTO arguDto = new MemoDTO();
			arguDto.setSubject(subject);
			arguDto.setWriter(writer);
			arguDto.setContent(content);
			
			MemoDAO dao = new MemoDAO();
			int result = dao.setInsert(arguDto);
			
			if(result > 0) {
				response.sendRedirect(path + "/memo_servlet/memo_list.do");
			} else {
				response.sendRedirect(path + "/memo_servlet/memo_chuga.do");
			}
		} else if(fileName.equals("sujungProc")) {
			String memoNo_ = request.getParameter("memoNo");
			int memoNo = util.getNumberCheck(memoNo_, 0);
			
			int failCounter = 0;
			if(memoNo == 0) {
				System.out.println("memoNo null");
				failCounter++;
				return;
			}
			
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			
			subject = util.getNullBlankCheck(subject);
			content = util.getNullBlankCheck(content);
			
			if(subject.equals("")) { System.out.println("subject null"); failCounter++; }
			if(content.equals("")) { System.out.println("content null"); failCounter++; }
			
			subject = util.getCheckString(subject);
			content = util.getCheckString(content);
			
			MemoDTO arguDto = new MemoDTO();
			arguDto.setMemoNo(memoNo);
			arguDto.setSubject(subject);
			arguDto.setContent(content);
			
			MemoDAO dao = new MemoDAO();
			int result = dao.setUpdate(arguDto);
			
			if(result > 0) {
				response.sendRedirect(path + "/memo_servlet/memo_list.do");
			} else {
				response.sendRedirect(path + "/memo_servlet/memo_sujung.do?memoNo=" + memoNo);
			}		
		} else if(fileName.equals("sakjeProc")) {
			String memoNo_ = request.getParameter("memoNo");
			int memoNo = util.getNumberCheck(memoNo_, 0);
			
			int failCounter = 0;
			if(memoNo == 0) {
				System.out.println("memoNo null");
				failCounter++;
				return;
			}
			
			MemoDTO arguDto = new MemoDTO();
			arguDto.setMemoNo(memoNo);
			
			MemoDAO dao = new MemoDAO();
			int result = dao.setDelete(arguDto);
			
			if(result > 0) {
				response.sendRedirect(path + "/memo_servlet/memo_list.do");
			} else {
				response.sendRedirect(path + "/memo_servlet/memo_sakje.do?memoNo=" + memoNo);
			}		
		}
	}
}
