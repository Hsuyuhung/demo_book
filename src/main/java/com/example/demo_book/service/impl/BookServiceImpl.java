package com.example.demo_book.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo_book.entity.Book;
import com.example.demo_book.respository.BookDao;
import com.example.demo_book.service.ifs.BookService;

public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;
	
	@Override
	public Book findCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book findInfo(int iSBN, String name, String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book changeInfo(int purchase, int price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book purchase(int iSBN, int purchase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book sales(int iSBN, int sales) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> bestSeller() {
		// TODO Auto-generated method stub
		return null;
	}

}
