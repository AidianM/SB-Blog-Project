package com.techtalentsouth.techtalentblog.blogposts;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository //not critical, but makes it easier to auto-instantiate/markup
public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
	//INTERFACE specified to tell Spring Boot that this needs to mesh into
	//our (query systems for the app?)
	
	//first parameter is the type to be stored in database, second param
	//is type used for primary key
	
	//what goes in here are additional methods that aren't provided by
	//CrudRepository that we want to perform on our database table.
	
	//We don't just add methods with any name we want/to do anything, 
	//instead, the methods are scanned by Spring Boot when loading this
	//file to determine queries we want to do in the database.
	
	
	//CrudRepository JavaDoc has just...a LOT of methods available, one
	//of which is "save": <save(S entity)> //<S extends T> S
	//This will increment the primary key set, and if we save one with 
	//an identical primary key, it will overwite the original ("saving over"
	//the old post).
	
	@Override
	List<BlogPost> findAll();
}
