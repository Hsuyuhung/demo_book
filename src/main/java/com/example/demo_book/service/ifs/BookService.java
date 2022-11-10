package com.example.demo_book.service.ifs;

import java.util.List;
import java.util.Map;

import com.example.demo_book.entity.Book;
import com.example.demo_book.vo.BookRes;

public interface BookService {
	
	public Book newRelease(String ISBN, String name, String author, String category, Integer price, Integer purchase, Integer sales);
	
	public List<Book> searchCategory(String category);
	
	public BookRes getBookInfoByISBNOrNameOrAuthor(String ISBN, String name, String author);

	public Book updatePurchase(String ISBN, Integer purchase);
	
	public Book updatePrice(String ISBN, Integer price);
	
	public BookRes sales(Map<String, Integer> buyBook);
	
	public List<Book> bestSeller();
	
}
