package com.newlecture.web.controller.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.controller.entity.Notice;
import com.newlecture.web.controller.entity.NoticeView;
import com.newlecture.web.service.NoticeService;
@WebServlet("/notice/list")
public class ListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
		//list?f=title&q=a
		
		//사용자가 요청할지 안할지 모르니 기본값을 주고 사용한다
		String field_ =request.getParameter("f");
		String query_ =request.getParameter("q");
		String page_ = request.getParameter("p");
		String field ="title";
		
		if(field_ != null && !field_.equals("")) field = field_;
		
		String query = "";
		if(query_ != null && !query_.equals("")) query = query_;
		
		int page=1;
		if(page_ !=null) page=Integer.parseInt(page_);
		
		
		NoticeService service = new NoticeService();
		List<NoticeView> list =service.getNoticeList(field,query,page);
				new ArrayList<Notice>();
		int count =service.getNoticeCount(field,query);
		System.out.println(count);
		request.setAttribute("list", list);
		request.setAttribute("count",count);
		
		
		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp")	//지정한 페이지를 요청하면서 request, response 객체를 공유
		 .forward(request, response); 		
		                  
		 
	}
}
