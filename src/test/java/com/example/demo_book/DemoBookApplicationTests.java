package com.example.demo_book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo_book.entity.Book;
import com.example.demo_book.respository.BookDao;
import com.example.demo_book.service.ifs.BookService;
import com.example.demo_book.vo.BookRes;

@SpringBootTest(classes = DemoBookApplication.class)
class DemoBookApplicationTests {

	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private BookService bookService;
	
	
	@Test
	void contextLoads() {
//		List<Book> bookList = bookDao.findByISBNOrNameOrAuthor("5555555", null, null);
//		System.out.println(bookList);
	
		Map<String, Integer> buyBook = new HashMap<>();
		buyBook.put("9780063066175", 2);
		buyBook.put("9780307268990", 2);
		buyBook.put("9780316412889", 2);
		
		BookRes res = bookService.sales(buyBook);
		System.out.println(res.getTotalPrice());
		
	
	}

}
