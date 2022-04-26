package com.techtalentsouth.techtalentblog.blogposts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogPostController {
	
	//Tell Spring Boot to set this automatically via INJECTION (Autowired)
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	private static List<BlogPost> posts = new ArrayList<>(); //intermediate hack to make this more understandable
	
	
	
	@GetMapping(value="/")
	public String index(BlogPost blogPost, Model model) { //model lets us access object via Thymeleaf key:value
		
		posts.removeAll(posts);
		Iterable<BlogPost> iterable = blogPostRepository.findAll(); //finds all bPR data and returns in "iterable" format
		
//		for (BlogPost post : iterable) { //this is the easier enhanced for-loop variant of the below Iterator
//			posts.add(post);
//		}
		
		Iterator<BlogPost> iterator = iterable.iterator(); //example of Iterator v iterable
		while (iterator.hasNext()) {
			posts.add(iterator.next());
		}
		
//		BlogPost blogPost = new BlogPost(); 
		//using the variable input auto-instantiates this for us
		
//		blogPost.setAuthor("Scott Dossey"); //set default author
		
		model.addAttribute("posts", posts);
		//add in our posts variable to hold blogPosts
		//this also allows it to be able to show with th:
		
//		model.addAttribute("blogPost", blogPost);
		// re: Model model
		//with the parameter blogPost, this also gets handled for us
		return "blogpost/index";
		
	}
	
	//new mapping for /new:
	@GetMapping(value="/blogposts/new")
	public String newBlog(BlogPost post) {
//		post.setAuthor("Scott Dossey"); //moved from index
		return "blogpost/new";
		
	}

	@PostMapping(value="/blogposts") //reflects changes with new.html et al
	public String addNewBlogPost(BlogPost blogPost, Model model) {
		//"make us a blogPost, but check to see if there IS one first
		//complete with user entered info
		
		//this method's goal is to save a blogPost entered in the form to
		//the database
		
		//our db table is represented with BlogPostRepository
		BlogPost savedBlogPost = blogPostRepository.save(blogPost);
//		posts.add(savedBlogPost); //don't need after iterable loop insertion above (line 29)
		
		//we need "Model model" back in vars to handle this:
		model.addAttribute("blogPost", savedBlogPost);
//		model.addAttribute("title", savedBlogPost.getTitle());
//		model.addAttribute("author", savedBlogPost.getAuthor());
//		model.addAttribute("blogEntry", savedBlogPost.getBlogEntry());
		
		return "blogpost/result"; //note: this is matching the templates directory, not the variable
		
		//Slides have more, but unnecessary, code. This are the critical 
		//elements.
	}
	
	//Edit post method:
	@GetMapping(value="/blogposts/{id}")
	public String editPostWithid(@PathVariable Long id, Model model) { //path variable pulls from the mapping value
		//Now we'll use the id to load the blogpost from the db table that has that id
		
		Optional<BlogPost> post = blogPostRepository.findById(id); //see: Java Doc CrudRepository findById
		if (post.isPresent()) {
			BlogPost actualPost = post.get();
			model.addAttribute("blogPost", actualPost);
		}
		return "blogpost/edit";
	}
	
	//submit your edit button
	@RequestMapping(value="/blogposts/update/{id}")
	public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
		//we need to get the database entry and modify it
		Optional<BlogPost> post = blogPostRepository.findById(id);
		if (post.isPresent()) {
			BlogPost actualPost = post.get();
			actualPost.setTitle(blogPost.getTitle());
			actualPost.setAuthor(blogPost.getAuthor());
			actualPost.setBlogEntry(blogPost.getBlogEntry());
			blogPostRepository.save(actualPost);
			model.addAttribute("blogPost", actualPost);
		}
		
		return "blogpost/result";
	}
	
	@RequestMapping(value = "/blogposts/delete/{id}")
	public String deletePostById(@PathVariable Long id) {
		blogPostRepository.deleteById(id);
		return "blogpost/delete";
	}
	
	//How do we know when to use Get/Post/Request???
	//Most web requests will be GET requests. 
	//When you create a form, you specify what type of request you want to do.
	//(see: new.html, th:action, method="post"
	//Get returns/filters data. If you're permanently changing actual data, use Post.
	
	
	
	
	
	
	
}
