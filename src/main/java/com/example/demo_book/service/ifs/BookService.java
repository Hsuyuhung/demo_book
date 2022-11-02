package com.example.demo_book.service.ifs;

import java.util.List;

import com.example.demo_book.entity.Book;

public interface BookService {
public Book findCategory(String category);
	
	public Book findInfo(int iSBN, String name, String author);
	
	public Book changeInfo(int purchase, int price);
	
	public Book purchase(int iSBN, int purchase);
	
	public Book sales(int iSBN, int sales);
	
	public List<Book> bestSeller();
}
