package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.controller.entity.Notice;
@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<Notice> list = new ArrayList<Notice>();
		
		String url ="jdbc:oracle:thin:@localhost:1521/orcl";
		String sql = "select * from notice";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con =DriverManager.getConnection(url,"practice","1111");
			Statement st =con.createStatement();
			ResultSet rs =st.executeQuery(sql);
				
			while(rs.next()){
				int id =rs.getInt("id");
				String title = rs.getString("title");
				Date regdate =rs.getDate("regdate");
				String writerid =rs.getString("writer_id");
				int hit =rs.getInt("hit");
				String files =rs.getString("files");
				String content =rs.getString("content");
				
				Notice notice = new Notice(id,
						title,
						writerid,
						regdate,
						hit,
						files,
						content);
				
				list.add(notice);
			}
			
			request.setAttribute("list", list);

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
		
		
		request.getRequestDispatcher("/notice/list.jsp")	//지정한 페이지를 요청하면서 request, response 객체를 공유
		 .forward(request, response); 		
		                  
		 
	}
}
