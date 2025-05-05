package com.example.bcsd;

public class Article {
	private int id;
	private String title;
	private String content;

	public Article(int id, String title, String content) {
		if (title==null || content==null) {
			throw new RuntimeException();
		}
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public int getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		if (title==null || title.isEmpty()) {
			throw new RuntimeException();
		}
		this.title = title;
	}

	public void setContent(String content) {
		if (content==null || content.isEmpty()) {
			throw new RuntimeException();
		}
		this.content = content;
	}
}