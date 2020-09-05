package com.newlecture.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.newlecture.web.controller.entity.Notice;
import com.newlecture.web.service.NoticeService;

@MultipartConfig(
		/* location ="/tmp", */
		fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 50 * 5)

@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {

	public RegController() {
		System.out.println("RegController클래스가 호출되었습니다");

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp") // 지정한 페이지를 요청하면서 request, response 객체를
																					// 공유
				.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 기본값을 설정할 필요가 있느냐 없느냐

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");
		/* String file = request.getParameter("file"); */

		Collection<Part> parts = request.getParts();
		StringBuilder builder = new StringBuilder();
		
		for (Part p : parts) {
			
			if(!p.getName().equals("file")) continue;
			if(p.getSize() ==0) continue;
			Part filePart = p;
			String fileName = filePart.getSubmittedFileName();
			builder.append(fileName);
			builder.append(",");
			
			InputStream fis = filePart.getInputStream();

			// "/upload" -> "c:/temp/upload"
			String realPath = request.getServletContext().getRealPath("/upload");
			System.out.println(realPath);

			// 윈도우에서만 사용하는 거면 이것도 맞지만 운영체제가 다르면 문제가 생김
			// String filePath = realPath +"\\"+ fileName;
			
			File path = new File(realPath);
			if(!path.exists()) //경로가 존재하지 않는다면
				path.mkdirs();	//디렉터리를 만들어 준다
			
			// 파일 저장시 
			// 파일명이 기조에 있는 파일명과 같을 수 도 있으므로
			// 이 부분을 처리해야 한다.
			// 일단적으로 동일한 이름이 있다면 aa 소괄호 열고
			// 정책을 수립해서 해야 된다.
			// 내가 생각하는건 세션으로 디렉터리를 하나 나누고
			// timestamp로 분초 단위 까지 해주면 안 겹치지 않을까???
			
			String filePath = realPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath);

			/*
			 * int b; while((b=fis.read()) !=-1) { fos.write(b); }
			 */
			int size = 0;
			byte[] buf = new byte[1024];
			while ((size = fis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}

			fos.close();
			fis.close();
		}
		//"abcdedf"  d 부터 e까지고 e는 포함되지 않는다
		//builder.delete(3, 4);
		builder.delete(builder.length()-1,builder.length());
		/*
		 * Part filePart=request.getPart("file"); String fileName =
		 * filePart.getSubmittedFileName(); InputStream fis= filePart.getInputStream();
		 * 
		 * //"/upload" -> "c:/temp/upload" String
		 * realPath=request.getServletContext().getRealPath("/upload");
		 * System.out.println(realPath);
		 * 
		 * // 윈도우에서만 사용하는 거면 이것도 맞지만 운영체제가 다르면 문제가 생김 //String filePath = realPath
		 * +"\\"+ fileName; String filePath = realPath +File.separator+ fileName;
		 * FileOutputStream fos = new FileOutputStream(filePath);
		 * 
		 * int b; while((b=fis.read()) !=-1) { fos.write(b); }
		 * 
		 * int size=0; byte[] buf = new byte[1024]; while((size=fis.read(buf)) !=-1) {
		 * fos.write(buf,0,size); }
		 * 
		 * fos.close(); fis.close();
		 */

		boolean pub = false;
		if (isOpen != null)
			pub = true;

		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterid("newlec1");
		notice.setFiles(builder.toString());
		
		
		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice);

		System.out.println(title);
		System.out.println(content);
		System.out.println(isOpen);
		System.out.println(result);
		/* System.out.println(file); */
		response.sendRedirect("list");
	};
}
