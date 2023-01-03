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

import com.example.demo_book.constants.BookMessageCode;
import com.example.demo_book.entity.Book;
import com.example.demo_book.respository.BookDao;
import com.example.demo_book.service.ifs.BookService;
import com.example.demo_book.vo.BookRes;
import com.example.demo_book.vo.ConsumerReceiptInfo;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;

	@Override
	public Book newRelease(String ISBN, String name, String author, String category, Integer price, Integer purchase) {

		if (bookDao.existsById(ISBN)) {
			return null;
		}

		Book book = new Book(ISBN, name, author, category, price, purchase);
		return bookDao.save(book);
	}

	@Override
	public BookRes updateBookInfo(String ISBN, String name, String author, String category, Integer price,
			Integer purchase, Integer sales) {

		BookRes res = new BookRes();

		Optional<Book> bookOp = bookDao.findById(ISBN);
		Book book = bookOp.get();

		if (!bookOp.isPresent()) {
			return new BookRes(BookMessageCode.ISBN_WRONG.getMessage());
		}

		setParams(book, name, author, category, price, purchase, sales);
		bookDao.save(book);
		res.setBook(book);
		res.setMessage(BookMessageCode.SUCCESSFUL.getMessage());
		return res;
	}

	private void setParams(Book book, String name, String author, String category, Integer price, Integer purchase,
			Integer sales) {// parameter

		if (StringUtils.hasText(name)) {
			book.setName(name);
		}

		if (StringUtils.hasText(author)) {
			book.setAuthor(author);
		}

		if (StringUtils.hasText(category)) {
			book.setCategory(category);
		}

		if (price >= 0) {
			book.setPrice(price);
		}

		if (purchase >= 0) {
			book.setPurchase(purchase);
		}

		if (sales >= 0) {
			book.setSales(sales);
		}
	}

	@Override
	public List<Book> searchCategory(String category) {

		String[] stringList = category.split(",");
		Set<String> bookSet = new HashSet<>();
		for (String item : stringList) {
			bookSet.add(item.trim());
		}

		List<Book> newBookList = new ArrayList<>();
		for (String item1 : bookSet) {
			List<Book> bookList = bookDao.findByCategoryContaining(item1);
			if (bookList.isEmpty()) {
				return null;
			}
			for (Book item2 : bookList) {
				item2.setCategory(null);
				item2.setSales(null);
				newBookList.add(item2);
			}
		}

		return newBookList;
	}

	@Override
	public BookRes getBookInfoByISBNOrNameOrAuthor(String password, String ISBN, String name, String author) {

		BookRes res = new BookRes();
		List<Book> bookList = bookDao.findByISBNOrNameOrAuthor(ISBN, name, author);
		List<Book> consumerBookList = new ArrayList<>();
		List<Book> storeBookList = new ArrayList<>();

		if (!StringUtils.hasText(password)) {
			res.setMessage(BookMessageCode.ITEM_EMPTY.getMessage());
			return res;
		}

		if (bookList.isEmpty()) {
			return null;
		}

		if (password.equals("0")) {
			for (Book item : bookList) {
				item.setCategory(null);
				storeBookList.add(item);
			}
			res.setStoreBookList(storeBookList);
			res.setMessage(BookMessageCode.SUCCESSFUL.getMessage());
			return res;
		} else {
			for (Book item : bookList) {
				item.setCategory(null);
				item.setPurchase(null);
				item.setSales(null);
				consumerBookList.add(item);
			}

			res.setConsumerBookList(consumerBookList);
			res.setMessage(BookMessageCode.SUCCESSFUL.getMessage());
			return res;
		}
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
	public BookRes sales(Map<String, Integer> buyBook) {

		BookRes res = new BookRes();
		List<ConsumerReceiptInfo> receipt = new ArrayList<>();
		int total = 0;

		for (Map.Entry<String, Integer> item : buyBook.entrySet()) {
			if (!StringUtils.hasText(item.getKey())) {
				return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
			}

			Optional<Book> bookOp = bookDao.findById(item.getKey());
			if (!bookOp.isPresent()) {
				return new BookRes(BookMessageCode.ISBN_NULL.getMessage());
			}

			Integer value = item.getValue();
			if (value == null || value < 0) {
				return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
			}

			if (value > bookOp.get().getPurchase()) {
				return new BookRes(BookMessageCode.NO_STOCK.getMessage());
			}

			Book storeBook = bookOp.get();
			storeBook.setSales(storeBook.getSales() + value);
			storeBook.setPurchase(storeBook.getPurchase() - value);
			bookDao.save(storeBook);

			total += bookOp.get().getPrice() * value;

			ConsumerReceiptInfo consumerBuyBook = new ConsumerReceiptInfo(storeBook.getName(), storeBook.getISBN(),
					storeBook.getAuthor(), storeBook.getPrice(), value);
			receipt.add(consumerBuyBook);

		}

		res.setConsumerReceipt(receipt);
		res.setTotalPrice(total);
		res.setMessage(BookMessageCode.SUCCESSFUL.getMessage());
		return res;

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