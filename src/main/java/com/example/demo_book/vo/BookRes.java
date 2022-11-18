package com.example.demo_book.vo;

import java.util.List;

import com.example.demo_book.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookRes {

	@JsonProperty(value = "book_info")
	private Book book;

	private String message;

	@JsonProperty(value = "book_list_info")
	private List<Book> bookList;

	@JsonProperty(value = "store_book_list_info")
	private List<Book> storeBookList;

	@JsonProperty(value = "consumer_book_list_info")
	private List<Book> consumerBookList;

	@JsonProperty(value = "consumer_receipt")
	private List<ConsumerReceiptInfo> consumerReceipt;

	private Integer totalPrice;

	public BookRes() {

	}

	public BookRes(String message) {
		this.message = message;
	}

	public BookRes(Book book, String message) {
		this.book = book;
		this.message = message;
	}

	public BookRes(List<Book> bookList, String message) {
		this.bookList = bookList;
		this.message = message;
	}

	public BookRes(Book book) {
		this.book = book;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public List<Book> getStoreBookList() {
		return storeBookList;
	}

	public void setStoreBookList(List<Book> storeBookList) {
		this.storeBookList = storeBookList;
	}

	public List<Book> getConsumerBookList() {
		return consumerBookList;
	}

	public void setConsumerBookList(List<Book> consumerBookList) {
		this.consumerBookList = consumerBookList;
	}

	public List<ConsumerReceiptInfo> getConsumerReceipt() {
		return consumerReceipt;
	}

	public void setConsumerReceipt(List<ConsumerReceiptInfo> consumerReceipt) {
		this.consumerReceipt = consumerReceipt;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
}