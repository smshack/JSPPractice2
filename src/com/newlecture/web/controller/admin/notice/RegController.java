package com.newlecture.web.controller.admin.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.controller.entity.Notice;
import com.newlecture.web.service.NoticeService;

@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {
	
	public RegController() {
		System.out.println("RegController클래스가 호출되었습니다");
		
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		request
		.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp") // 지정한 페이지를 요청하면서 request, response 객체를 공유
			.forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		request.setCharacterEncoding("UTF-8");
		// 기본값을 설정할 필요가 있느냐 없느냐
	
		
		String title= request.getParameter("title");
		String content= request.getParameter("content");
		String isOpen= request.getParameter("open");
		
		boolean pub = false;
		if(isOpen != null) pub=true;
		
		
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterid("newlec1");
		
		NoticeService service = new NoticeService();
		int result =service.insertNotice(notice);
		
		
		System.out.println(title);
		System.out.println(content);
		System.out.println(isOpen);
		System.out.println(result);
		
		response.sendRedirect("list");
	};
}

