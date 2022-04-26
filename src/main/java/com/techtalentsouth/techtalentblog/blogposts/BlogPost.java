package com.techtalentsouth.techtalentblog.blogposts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlogPost {
	
	@Id 
	//ID labels the next variable as the primary key when stored 
	//to database
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private String author;
	private String blogEntry;
		//the above creates the database
	
	public BlogPost() {
		//must have empty/default constructor for @Entity to work
	}
	
	public BlogPost(String title, String author, String blogEntry) {
		this.title=title;
		this.author=author;
		this.blogEntry=blogEntry;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBlogEntry() {
		return blogEntry;
	}

	public void setBlogEntry(String blogEntry) {
		this.blogEntry = blogEntry;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "BlogPost [id=" + id + ", title=" + title + ", author=" + author + ", blogEntry=" + blogEntry + "]";
	}
	
	
}
