package com.example.demo_book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_book.constants.BookRtnCode;
import com.example.demo_book.entity.Book;
import com.example.demo_book.service.ifs.BookService;
import com.example.demo_book.vo.BookReq;
import com.example.demo_book.vo.BookRes;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping(value = "api/new_release")
	public BookRes newRelease(@RequestBody BookReq req) {

		switch (req.getISBN().length()) {
		case 10:
			break;

		case 13:
			break;

		default:
			return new BookRes(BookRtnCode.ISBN_WRONG.getMessage());
		}

		if (!StringUtils.hasText(req.getISBN()) || !StringUtils.hasText(req.getName())
				|| !StringUtils.hasText(req.getAuthor()) || !StringUtils.hasText(req.getCategory())) {
			return new BookRes(BookRtnCode.ITEM_EMPTY.getMessage());
		}

		if (req.getPrice() == 0) {
			return new BookRes(BookRtnCode.ITEM_EMPTY.getMessage());
		}

		if (req.getSales() != 0) {
			return new BookRes(BookRtnCode.SALE_INITIAL.getMessage());
		}

		Book book = bookService.newRelease(req.getISBN(), req.getName(), req.getAuthor(), req.getCategory(),
				req.getPrice(), req.getPurchase(), req.getSales());

		if (book == null) {
			return new BookRes(BookRtnCode.ISBN_EXISTED.getMessage());
		}

		return new BookRes(book, BookRtnCode.SUCCESSFUL.getMessage());

	}

	@PostMapping(value = "api/search_category")
	public BookRes searchCategory(@RequestBody BookReq req) {

		if (!StringUtils.hasText(req.getCategory())) {
			return new BookRes(BookRtnCode.ITEM_EMPTY.getMessage());
		}

		List<Book> listBook = bookService.searchCategory(req.getCategory());
		if(listBook == null) {
			return new BookRes(BookRtnCode.NO_RESULT.getMessage());
		}
		
		return new BookRes(listBook, BookRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "api/find_by_ISBN_Name_Author")
	public BookRes findByISBNOrNameOrAuthor(@RequestBody BookReq req) {

		if (!StringUtils.hasText(req.getISBN()) && !StringUtils.hasText(req.getName())
				&& !StringUtils.hasText(req.getAuthor())) {
			// 如輸入3個資料皆為空
			return new BookRes(BookRtnCode.ITEM_EMPTY.getMessage());
		}

		BookRes listBook = bookService.getBookInfoByISBNOrNameOrAuthor(req.getISBN(), req.getName(),
				req.getAuthor());
		if (listBook == null) {
			// 如查無結果
			return new BookRes(BookRtnCode.NO_RESULT.getMessage());
		}

		return bookService.getBookInfoByISBNOrNameOrAuthor(req.getISBN(), req.getName(), req.getAuthor());
	}

	@PostMapping(value = "api/update_purchase")
	public BookRes updatePurchase(@RequestBody BookReq req) {

		if (!StringUtils.hasText(req.getISBN())) {
			return new BookRes(BookRtnCode.ITEM_EMPTY.getMessage());
		}
		
		Book book = bookService.updatePurchase(req.getISBN(), req.getPurchase());
		if (book == null) {
			return new BookRes(BookRtnCode.ISBN_WRONG.getMessage());
		}
		return new BookRes(book, BookRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "api/update_price")
	public BookRes updatePrice(@RequestBody BookReq req) {

		if (!StringUtils.hasText(req.getISBN())) {
			return new BookRes(BookRtnCode.ITEM_EMPTY.getMessage());
		}
		if (req.getPrice() == 0) {
			return new BookRes(BookRtnCode.ITEM_EMPTY.getMessage());
		}

		Book book = bookService.updatePrice(req.getISBN(), req.getPrice());
		if (book == null) {
			return new BookRes(BookRtnCode.ISBN_WRONG.getMessage());
		}
		return new BookRes(book, BookRtnCode.SUCCESSFUL.getMessage());
	}

	@PostMapping(value = "/api/sales")
	public BookRes sales(@RequestBody BookReq req) {

		return bookService.sales(req.getBuyBook());
	}

	@PostMapping(value = "api/best_seller")
	public List<Book> bestSeller() {
		return bookService.bestSeller();

	}
}
