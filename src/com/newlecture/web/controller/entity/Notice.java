package com.newlecture.web.controller.entity;

import java.util.Date;

public class Notice {
	private int id;
	private String title;
	private String writerid;
	private Date regdate;
	private int hit;
	private String files;
	private String content;
	private boolean pub;
	
	
	public Notice() {
		// TODO Auto-generated constructor stub
	}
	
	



	public Notice(int id, String title, String writerid, Date regdate, int hit, String files, String content,
			boolean pub) {

		this.id = id;
		this.title = title;
		this.writerid = writerid;
		this.regdate = regdate;
		this.hit = hit;
		this.files = files;
		this.content = content;
		this.pub = pub;
	}






	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public String getTitle() {
		return title;
	}





	public void setTitle(String title) {
		this.title = title;
	}





	public String getWriterid() {
		return writerid;
	}





	public void setWriterid(String writerid) {
		this.writerid = writerid;
	}





	public Date getRegdate() {
		return regdate;
	}





	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}





	public int getHit() {
		return hit;
	}





	public void setHit(int hit) {
		this.hit = hit;
	}





	public String getFiles() {
		return files;
	}





	public void setFiles(String files) {
		this.files = files;
	}





	public String getContent() {
		return content;
	}





	public void setContent(String content) {
		this.content = content;
	}





	public boolean getPub() {
		return pub;
	}





	public void setPub(boolean pub) {
		this.pub = pub;
	}





	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", writerid=" + writerid + ", regdate=" + regdate + ", hit="
				+ hit + ", files=" + files + ", content=" + content + ", pub=" + pub + "]";
	}






	
	
	
}
