package com.example.demo_book.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo_book.constants.BookRtnCode;
import com.example.demo_book.entity.Book;
import com.example.demo_book.respository.BookDao;
import com.example.demo_book.service.ifs.BookService;
import com.example.demo_book.vo.BookRes;
import com.example.demo_book.vo.ConsumerBuyBook;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;

	@Override
	public Book newRelease(String ISBN, String name, String author, String category, Integer price, Integer purchase,
			Integer sales) {

		if (bookDao.existsById(ISBN)) {
			return null;
		}

		Book book = new Book(ISBN, name, author, category, price, purchase, sales);
		return bookDao.save(book);
	}

	@Override
	public BookRes getBookInfoByISBNOrNameOrAuthor(String ISBN, String name, String author) {
		BookRes res = new BookRes();
		List<Book> bookList = bookDao.findByISBNOrNameOrAuthor(ISBN, name, author);
		List<Book> consumerBookList = new ArrayList<>();
		List<Book> storeBookList = new ArrayList<>();
		
		for (Book item : bookList) {
			item.setCategory(null);
			item.setPurchase(null);
			item.setSales(null);
			consumerBookList.add(item);
		}
		if (consumerBookList.isEmpty()) {
			return null;
		}
		res.setConsumerBookList(consumerBookList);
		
		for (Book item : bookList) {
			item.setCategory(null);
			storeBookList.add(item);
		}
		if (storeBookList.isEmpty()) {
			return null;
		}	
		
		res.setMessage(BookRtnCode.SUCCESSFUL.getMessage());
		res.setBookList(storeBookList);

		return res;
	}

	@Override
	public List<Book> searchCategory(String category) {
		BookRes res = new BookRes();
		String[] stringList = category.split(",");
		Set<String> bookSet = new HashSet<>();
		for (String item : stringList) {
			bookSet.add(item.trim());
		}

		List<Book> newBookList = new ArrayList<>();
		for (String item1 : bookSet) {
			res.setBookList(bookDao.findByCategoryContaining(item1));
			if(res.getBookList().isEmpty()) {
				return null;
			}
			for (Book item2 : res.getBookList()) {
				item2.setCategory(null);
				item2.setSales(null);
				newBookList.add(item2);
			}
		}

		return newBookList;
	}

	@Override
	public BookRes sales(Map<String, Integer> buyBook) {
		BookRes res = new BookRes();
		List<ConsumerBuyBook> receipt = new ArrayList<>();
		int total = 0;
		for (Map.Entry<String, Integer> item : buyBook.entrySet()) {
			if (!StringUtils.hasText(item.getKey())) {
				return new BookRes(BookRtnCode.ITEM_EMPTY.getMessage());
			}
			Optional<Book> bookOp = bookDao.findById(item.getKey());
			if (!bookOp.isPresent()) {
				return new BookRes(BookRtnCode.ISBN_WRONG.getMessage());
			}
			if (item.getValue() > bookOp.get().getPurchase()) {
				return new BookRes(BookRtnCode.NO_STOCK.getMessage());
			}
			
			Book storeBook = bookOp.get();
			storeBook.setSales(storeBook.getSales() + item.getValue());// 銷售量及庫存量變更
			storeBook.setPurchase(storeBook.getPurchase() - item.getValue());
			bookDao.save(storeBook);
			
			total += bookOp.get().getPrice() * item.getValue();
			
			
			ConsumerBuyBook consumerBuyBook = new ConsumerBuyBook(storeBook.getName(), storeBook.getISBN(), storeBook.getAuthor(), storeBook.getPrice(), item.getValue());
			receipt.add(consumerBuyBook);
				
		}
		res.setConsumerReceipt(receipt);
		res.setTotalPrice(total);
		res.setMessage(BookRtnCode.SUCCESSFUL.getMessage());
		return res;

	}

	@Override
	public Book updatePurchase(String ISBN, Integer purchase) {
		Optional<Book> bookOp = bookDao.findById(ISBN);
		if (bookOp.isPresent()) {
			Book book = bookOp.get();
			book.setPurchase(purchase);
			bookDao.save(book);
			book.setCategory(null);
			book.setSales(null);
			return book;
		}
		return null;
	}

	@Override
	public Book updatePrice(String ISBN, Integer price) {
		Optional<Book> bookOp = bookDao.findById(ISBN);
		if (bookOp.isPresent()) {
			Book book = bookOp.get();
			book.setPrice(price);
			bookDao.save(book);
			book.setCategory(null);
			book.setSales(null);
			return book;
		}
		return null;
	}

	@Override
	public List<Book> bestSeller() {
		List<Book> bookList = bookDao.findTop5ByOrderBySalesDesc();
		List<Book> newBookList = new ArrayList<>();
		for (Book item : bookList) {
			item.setCategory(null);
			item.setPurchase(null);
			item.setSales(null);
			newBookList.add(item);
		}
		return newBookList;
	}
}
