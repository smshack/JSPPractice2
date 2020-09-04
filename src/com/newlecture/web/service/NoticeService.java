package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.controller.entity.Notice;
import com.newlecture.web.controller.entity.NoticeView;

public class NoticeService {
//================================================================================================================
//이름이 같은 메서드 오버로딩으로 사용할 때 
//같은 것들 중 하나만 구현하고
//나머지에서는 구현한걸 재 호출해서 사용하는 식으로 사용하는 것이 좋음
	// 하나로 통일해서 가져오는 것이 좋음 메서드가 메서드를 호출하는 방식이 반복되면
	// 스택 사용이 늘어나 안좋음
// 보통 인자가 제일 많은 걸 구현
// 처음 공지사항 목록을 가져와서 보여주는 메서드
	
public NoticeService() {
	// TODO Auto-generated constructor stub
	System.out.println( "NoticeService클래스가 호출되었습니다");
}
	public List<NoticeView> getNoticeList() {

		return getNoticeList("title", "", 1);
	}

	// 페이지 번호를 받아서 그에 따라서 공지사항 목록을 보여주는 메서드
	public List<NoticeView> getNoticeList(int page) {

		return getNoticeList("title", "", page);
	}

	// 검색 기능 사용시 Notice의 컬럼명인 field와 쿼리문, 페이지 번호를 받아서 공지사항 목록으로 보여주는 메서드
	public List<NoticeView> getNoticeList(String field /* title, writer_id */, String query /* A */, int page) {
		List<NoticeView> list = new ArrayList<NoticeView>();

		String sql = "select * from (" + "select rownum num, n.* " + "from (select * from notice_view where " + field
				+ " LIKE ? order by regdate desc) n" + ") " + "where num between ? and ?";

		// 1, 11, 21, 31 -> 등차수열 an = (n-1)*10
		// an = 1 + (page-1) * 10
		// sql문 ?를 어떻게 값을 껴 넣을까???
		// 10, 20, 30, 40 -> page * 10

		String url = "jdbc:oracle:thin:@localhost:1521/orcl";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "practice", "1111");
			PreparedStatement st = con.prepareStatement(sql);

			st.setString(1, "%" + query + "%");
			st.setInt(2, 1 + (page - 1) * 10);
			st.setInt(3, page * 10);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				Date regdate = rs.getDate("regdate");
				String writerid = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				// String content =rs.getString("content");
				int cmtCount = rs.getInt("cmt_cnt");
				boolean pub = rs.getBoolean("pub");
				
				NoticeView notice = new NoticeView(id, title, writerid, regdate, hit, files,pub,
						// content,
						cmtCount);

				list.add(notice);
			}

//			request.setAttribute("list", list);

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

		return list;
	}

//================================================================================================================	
	// 목록의 개수를 구하는 메서드
	public int getNoticeCount() {

		return getNoticeCount("title", "");
	}

	// 검색된 목록의 개수를 구하는 메서드
	public int getNoticeCount(String field, String query) {
		String sql = "select count(id) count from (" + "select rownum num, n.* " + "from (select * from notice where "
				+ field + " LIKE ? order by regdate desc) n" + ")";

		String url = "jdbc:oracle:thin:@localhost:1521/orcl";

		int count = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "practice", "1111");
			PreparedStatement st = con.prepareStatement(sql);

			st.setString(1, "%" + query + "%");

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				count = rs.getInt("count");
			}

//			request.setAttribute("list", list);

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

		return count;
	}

//================================================================================================================
	// 자세한 페이지에 들어가서 notice 하나만 상세하게 다 출력
	public Notice getNotice(int id) {
		Notice notice = null;

		String sql = "select * from notice where id=?";
		String url = "jdbc:oracle:thin:@localhost:1521/orcl";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "practice", "1111");
			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				int nid = rs.getInt("id");
				String title = rs.getString("title");
				Date regdate = rs.getDate("regdate");
				String writerid = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				boolean pub = rs.getBoolean("pub");
				notice = new Notice(nid, title, writerid, regdate, hit, files, content,pub);
			}

//			request.setAttribute("list", list);

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

		return notice;

	}

//================================================================================================================
	// 상세페이지에서 다음글을 보여주는 메서드
	public Notice getNextNotice(int id) {
		Notice notice = null;

		String sql = "select * from notice " + "where regdate >(select regdate from notice where id=?) "
				+ "and rownum=1";

		String url = "jdbc:oracle:thin:@localhost:1521/orcl";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "practice", "1111");
			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				int nid = rs.getInt("id");
				String title = rs.getString("title");
				Date regdate = rs.getDate("regdate");
				String writerid = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				boolean pub = rs.getBoolean("pub");
				notice = new Notice(nid, title, writerid, regdate, hit, files, content,pub);
			}

//			request.setAttribute("list", list);

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

		return notice;
	}

	// 상세페이지에서 이전글을 보여주는 메서드
	public Notice getPrevNotice(int id) {
		Notice notice = null;

		String sql = "select id from (select * from notice order by regdate desc) "
				+ "where regdate <(select regdate from notice where id=?) " + "and rownum=1";

		String url = "jdbc:oracle:thin:@localhost:1521/orcl";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "practice", "1111");
			PreparedStatement st = con.prepareStatement(sql);

			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				int nid = rs.getInt("id");
				String title = rs.getString("title");
				Date regdate = rs.getDate("regdate");
				String writerid = rs.getString("writer_id");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				boolean pub = rs.getBoolean("pub");
				notice = new Notice(nid, title, writerid, regdate, hit, files, content,pub);
			}

//			request.setAttribute("list", list);

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

		return notice;
	}

//================================================================================================================
	/*
	 * 추가될 메소드들 일괄 삭제 요청removeNoticeAll(ids) 일괄 공개 요청pubNooticeAll(ids)
	 * 
	 * 공지사항 글쓰기 페이지 만들어서 진행 공지등록 요청 insertNotice(notice) insertNotice(notice) 공지삭제요청
	 * deleteNotice(id) 공지 수정요청 updateNotice(notice)
	 * 
	 * 메인 페이지에서 공지사항의 최근 글 목록을 보여주는 메서드 getNoticeNewestList()
	 */
	public int removeNoticeAll(int[] ids) {

		return 0;
	}

	public int pubNoticeAll(int[] ids) {

		return 0;
	}

	public int insertNotice(Notice notice) {
		int result =0;
		
		String sql = "insert into notice(title, content,writer_id,pub) values(?,?,?,?)";

		String url = "jdbc:oracle:thin:@localhost:1521/orcl";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "practice", "1111");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, notice.getTitle());
			st.setString(2, notice.getContent());
			st.setString(3, notice.getWriterid());
			st.setBoolean(4, notice.getPub());
			
			result = st.executeUpdate();
			
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}


	public int deleteNotice(int id) {

		return 0;
	}

	public int updateNotice(Notice notice) {

		return 0;
	}

	public List<NoticeView> getNoticeViewNewestList() {

		return null;
	}

	public int deleteNoticeAll(int[] ids) {
		// TODO Auto-generated method stub
		
		int result =0;
		String params ="";
		
		for (int i = 0; i < ids.length; i++) {
			params += ids[i];
			
			if(i<ids.length-1) {
				params +=",";
			}
		}
		
		String sql = "delete notice where id in (" +params+")";

		String url = "jdbc:oracle:thin:@localhost:1521/orcl";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "practice", "1111");
			Statement st = con.createStatement();


			result = st.executeUpdate(sql);
			
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
