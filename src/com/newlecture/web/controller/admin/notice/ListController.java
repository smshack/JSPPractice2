package com.newlecture.web.controller.admin.notice;

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

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet {

	// 404 페이지가 없을 때
	// 405 처리할 수 있는 메서드가 없을 때
	// 403 권한이 없을 때
	public ListController() {
		// TODO Auto-generated constructor stub
		System.out.println("ListController클래스가 호출되었습니다");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Auto-generated method stub
		// list?f=title&q=a

		// 사용자가 요청할지 안할지 모르니 기본값을 주고 사용한다
		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");
		String field = "title";

		if (field_ != null && !field_.equals(""))
			field = field_;

		String query = "";
		if (query_ != null && !query_.equals(""))
			query = query_;

		int page = 1;
		if (page_ != null)
			page = Integer.parseInt(page_);

		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page);
		new ArrayList<Notice>();
		int count = service.getNoticeCount(field, query);
		System.out.println(count);
		request.setAttribute("list", list);
		request.setAttribute("count", count);

		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp") // 지정한 페이지를 요청하면서 request, response
																					// 객체를 공유
				.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String[] openIds = request.getParameterValues("open-id");
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");

		System.out.println("============================");
		System.out.println("cmd:" + cmd);
		for (String openid : openIds)
			System.out.printf("openid id: %s\n", openid);
		for (String delid : delIds)
			System.out.printf("delid id: %s\n", delid);
		System.out.println("============================");

		switch (cmd) {
		case "일괄공개":
			for (String openid : openIds)
				System.out.printf("openid id: %s\n", openid);

			break;
		case "일괄삭제":
			for (String delid : delIds)
				System.out.printf("delid id: %s\n", delid);
			NoticeService service = new NoticeService();
			// 문자열 배열을 정수형 배열로 바꾸는 방법
			int[] ids = new int[delIds.length];

			for (int i = 0; i < ids.length; i++) {
				ids[i] = Integer.parseInt(delIds[i]);

				int result = service.deleteNoticeAll(ids);
				break;
			}
			response.sendRedirect("list");

		}
	}
}