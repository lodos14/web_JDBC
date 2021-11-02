package com.newlecture.app.entity;

import java.util.Date;

public class Notice {
	
	private int id;
	private String title;
	private String writerid;
	private Date regDate;
	private String content;
	private int hit;
	
	public Notice (){
		
	}
	
	// Source -> constructor using fields 로 만드는 기능 있음
	public Notice(int id, String title, String writerid, Date regDate, String content, int hit) {
		
		this.id = id;
		this.title = title;
		this.writerid = writerid;
		this.regDate = regDate;
		this.content = content;
		this.hit = hit;
		
	}

	// Source -> generate Getters and Setters 로 한 번에 만들거나 만들기
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
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
}
