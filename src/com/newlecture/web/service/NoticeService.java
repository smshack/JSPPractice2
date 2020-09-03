package com.newlecture.web.service;

import java.util.List;

import com.newlecture.web.controller.entity.Notice;

public class NoticeService {
//================================================================================================================
//이름이 같은 메서드 오버로딩으로 사용할 때 
//같은 것들 중 하나만 구현하고
//나머지에서는 구현한걸 재 호출해서 사용하는 식으로 사용하는 것이 좋음
	//하나로 통일해서 가져오는 것이 좋음 메서드가 메서드를 호출하는 방식이 반복되면
	//스택 사용이 늘어나 안좋음
// 보통 인자가 제일 많은 걸 구현
// 처음 공지사항 목록을 가져와서 보여주는 메서드
	public List<Notice> getNoticeList() {

		return getNoticeList("title","",1);
	}
	//페이지 번호를 받아서 그에 따라서 공지사항 목록을 보여주는 메서드
	public List<Notice> getNoticeList(int page) {

		return getNoticeList("title","",page);
	}

	// 검색 기능 사용시 Notice의 컬럼명인 field와 쿼리문, 페이지 번호를 받아서 공지사항 목록으로 보여주는 메서드
	public List<Notice> getNoticeList(String field, String query, int page) {

		return null;
	}
//================================================================================================================	
	//목록의 개수를 구하는 메서드
	public int getNoticeCount() {

		return 0;
	}
	//검색된 목록의 개수를 구하는 메서드
	public int getNoticeCount(String field, String query) {

		return 0;
	}
//================================================================================================================
	// 자세한 페이지에 들어가서 notice 하나만 상세하게 다 출력
	public Notice getNotice(int id) {

		return null;
	}
//================================================================================================================
	// 상세페이지에서 다음글을 보여주는 메서드
	public Notice getNextNotice(int id) {

		return null;
	}
	// 상세페이지에서 이전글을 보여주는 메서드
	public Notice getPrevNotice(int id) {

		return null;
	}
//================================================================================================================
}
