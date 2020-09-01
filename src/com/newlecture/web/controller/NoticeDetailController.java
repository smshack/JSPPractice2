package com.newlecture.web.controller;

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

/**
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public NoticeDetailController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int id =Integer.parseInt(request.getParameter("id"));
		String url ="jdbc:oracle:thin:@localhost:1521/orcl";
		String sql = "select * from notice where id=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con =DriverManager.getConnection(url,"practice","1111");
			
			PreparedStatement st =con.prepareStatement(sql);	//쿼리를 미리 준비해두고 사용한다
			st.setInt(1,id);
			
			ResultSet rs =st.executeQuery();
			
			rs.next();
			System.out.println("2-----------");
			String title = rs.getString("title");
			Date regdate =rs.getDate("regdate");
			String writerid =rs.getString("writer_id");
			int hit =rs.getInt("hit");
			String files =rs.getString("files");
			String content =rs.getString("content");
			System.out.println(id);
			System.out.println(title);
			System.out.println(writerid);
			System.out.println(regdate);
			System.out.println(hit);
			System.out.println(files);
			System.out.println(content);
			System.out.println("2-----------");
			Notice notice = new Notice(id,
									title,
									writerid,
									regdate,
									hit,
									files,
									content);
			
			System.out.println(notice.toString());
			
			request.setAttribute("notice", notice);
			
			//이부분을 객체화 한다
			/*
			request.setAttribute("title", title);
			request.setAttribute("writerid", writerid);
			request.setAttribute("regdate",regdate );
			request.setAttribute("hit", hit);
			request.setAttribute("files", files);
			request.setAttribute("content", content);
			*/
			rs.close();
			st.close();
			con.close();   
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 request.getRequestDispatcher("/notice/detail.jsp")	//지정한 페이지를 요청하면서 request, response 객체를 공유
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
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * */
		                  
	}



}
