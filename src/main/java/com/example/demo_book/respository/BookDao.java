package com.example.demo_book.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_book.entity.Book;

@Repository
public interface BookDao extends JpaRepository<Book, String> {
	
	List<Book> findByCategoryContaining(String category);

	List<Book> findByISBNOrNameOrAuthor(String ISBN, String name, String author);

	List<Book> findTop5ByOrderBySalesDesc();

}
