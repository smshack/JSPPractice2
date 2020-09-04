package com.newlecture.web.controller.admin.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.controller.entity.Notice;
import com.newlecture.web.service.NoticeService;

/**
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/admin/board/notice/detail")
public class DetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DetailController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int id =Integer.parseInt(request.getParameter("id"));
		
		NoticeService service = new NoticeService();
		Notice notice =service.getNotice(id);
		request.setAttribute("notice",notice);
		
		
		 request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/detail.jsp")	//지정한 페이지를 요청하면서 request, response 객체를 공유
		 .forward(request, response);
		
		
		
		//흐름상 list.jsp -> 현재 서블릿  -> detail.jsp 로 흘러가야됨
		
		//서블릿에서 서블릿으로 전이하는 방법
		//redirect
		/* 호출해서 다른 페이지로 넘어가는 메서드
		 * 뭔가 작업을 하고 다른 페이지로 넘겨줄때 사용
		 *  
		 *  */
		
		
		//forward
		/* 
		 * 작업한 내용을 이어서 내용을 그 서블릿으로 넘겨주는 것
		 * */
		                  
	}



}
