package com.example.demo_book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_book.constants.BookMessageCode;
import com.example.demo_book.entity.Book;
import com.example.demo_book.service.ifs.BookService;
import com.example.demo_book.vo.BookReq;
import com.example.demo_book.vo.BookRes;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	// 創建書籍資料
	@PostMapping(value = "api/new_release")
	public BookRes newRelease(@RequestBody BookReq req) {
		
		// 如任意項目為空，則回傳錯誤訊息
		if (!StringUtils.hasText(req.getISBN()) || !StringUtils.hasText(req.getName())
				|| !StringUtils.hasText(req.getAuthor()) || !StringUtils.hasText(req.getCategory())) {
			return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
		}

		// 判斷ISBN長度
		switch (req.getISBN().length()) {

		case 10:
		case 13:
			break;

		default:
			return new BookRes(BookMessageCode.ISBN_LENGTH.getMessage());
		}

		// 價格與初始庫存不得小於等於0
		if (req.getPrice() <= 0 || req.getPurchase() <= 0) {
			return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
		}

		Book book = bookService.newRelease(req.getISBN(), req.getName(), req.getAuthor(), req.getCategory(),
				req.getPrice(), req.getPurchase());

		// 如ISBN已存在，則回傳錯誤訊息
		if (book == null) {
			return new BookRes(BookMessageCode.ISBN_EXISTED.getMessage());
		}

		return new BookRes(book, BookMessageCode.CREATE_INFO_SUCCESSFUL.getMessage());
	}

	// 更新書籍資料
	@PostMapping(value = "api/update_book_info")
	public BookRes updateBookInfo(@RequestBody BookReq req) {

		if (!StringUtils.hasText(req.getISBN())) {
			return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
		}
		
		// 如所有項目為空(無變更)，則回傳錯誤訊息
		if (!StringUtils.hasText(req.getName()) && !StringUtils.hasText(req.getName())
				&& !StringUtils.hasText(req.getAuthor()) && !StringUtils.hasText(req.getCategory())
				&& req.getPrice() == 0 && req.getPurchase() == 0 && req.getSales() == 0) {
			return new BookRes(BookMessageCode.ITEM_WRONG.getMessage());
		}

		// 如價格、庫存、銷售小於0，則回傳錯誤訊息
		if (req.getPrice() < 0 || req.getPurchase() < 0 || req.getSales() < 0) {
			return new BookRes(BookMessageCode.NUMBER_ERRO.getMessage());
		}

		return bookService.updateBookInfo(req.getISBN(), req.getName(), req.getAuthor(), req.getCategory(),
				req.getPrice(), req.getPurchase(), req.getSales());
	}

	// 透過類別搜尋書籍資料
	@PostMapping(value = "api/search_category")
	public BookRes searchCategory(@RequestBody BookReq req) {

		// 類別無輸入值，則回傳錯誤訊息
		if (!StringUtils.hasText(req.getCategory())) {
			return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
		}

		// 如此類別無書籍資料，則回傳錯誤訊息
		List<Book> bookList = bookService.searchCategory(req.getCategory());
		if (bookList == null) {
			return new BookRes(BookMessageCode.NO_RESULT.getMessage());
		}

		return new BookRes(bookList, BookMessageCode.SEARCH_SUCCESSFUL.getMessage());
	}

	// 透過ISBN、書名、作者名等任意項目搜尋書籍資料(根據密碼分為書籍商、使用者2種顯示版本)
	@PostMapping(value = "api/find_by_ISBN_Name_Author")
	public BookRes findByISBNOrNameOrAuthor(@RequestBody BookReq req) {

		// 如所有項目為空，則回傳錯誤訊息
		if (!StringUtils.hasText(req.getISBN()) && !StringUtils.hasText(req.getName())
				&& !StringUtils.hasText(req.getAuthor())) {
			return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
		}

		// 如查無任何資料，則回傳錯誤樳
		BookRes listBook = bookService.getBookInfoByISBNOrNameOrAuthor(req.getPassword(), req.getISBN(), req.getName(),
				req.getAuthor());
		if (listBook == null) {
			return new BookRes(BookMessageCode.NO_RESULT.getMessage());
		}

		return bookService.getBookInfoByISBNOrNameOrAuthor(req.getPassword(), req.getISBN(), req.getName(),
				req.getAuthor());
	}

	// 更新庫存
	@PostMapping(value = "api/update_purchase")
	public BookRes updatePurchase(@RequestBody BookReq req) {
		
		// 如ISBN輸入為空，則回傳錯誤訊息
		if (!StringUtils.hasText(req.getISBN())) {
			return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
		}

		// 如庫存量小於0，則回傳錯誤訊息
		if (req.getPurchase() < 0) {
			return new BookRes(BookMessageCode.NUMBER_ERRO.getMessage());
		}

		// 如查無此ISBN，則回傳錯誤訊息
		Book book = bookService.updatePurchase(req.getISBN(), req.getPurchase());
		if (book == null) {
			return new BookRes(BookMessageCode.ISBN_NULL.getMessage());
		}

		return new BookRes(book, BookMessageCode.UPDATE_SUCCESSFUL.getMessage());
	}

	// 更新價格
	@PostMapping(value = "api/update_price")
	public BookRes updatePrice(@RequestBody BookReq req) {

		// 如價格輸入為空，則回傳錯誤訊息
		if (!StringUtils.hasText(req.getISBN())) {
			return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
		}

		// 如價格小於0，則回傳錯誤訊息
		if (req.getPrice() < 0) {
			return new BookRes(BookMessageCode.NUMBER_ERRO.getMessage());
		}

		// 如查無此ISBN，則回傳錯誤訊息
		Book book = bookService.updatePrice(req.getISBN(), req.getPrice());
		if (book == null) {
			return new BookRes(BookMessageCode.ISBN_NULL.getMessage());
		}

		return new BookRes(book, BookMessageCode.UPDATE_SUCCESSFUL.getMessage());
	}

	// 銷售書籍
	@PostMapping(value = "/api/sales")
	public BookRes sales(@RequestBody BookReq req) {

		// 如無購書資訊(Map形式：ISBN+購買數量)，則回傳錯誤訊息
		if (req.getBuyBook().isEmpty()) {
			return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
		}

		return bookService.sales(req.getBuyBook());
	}

	// 銷售排行前5
	@PostMapping(value = "api/best_seller")
	public List<Book> bestSeller() {

		return bookService.bestSeller();
	}
}
