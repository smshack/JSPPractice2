package com.newlecture.web.controller.entity;

import java.util.Date;

public class NoticeView extends Notice {

	private int cmtCount;
	
	public NoticeView() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeView(int id, String title, String writerid, Date regdate, int hit, String files,boolean pub, int cmtCount) {
		super(id, title, writerid, regdate, hit, files,"",pub);
		this.cmtCount =cmtCount;
	}

	public int getCmtCount() {
		return cmtCount;
	}

	public void setCmtCount(int cmtCount) {
		this.cmtCount = cmtCount;
	}
	
}
