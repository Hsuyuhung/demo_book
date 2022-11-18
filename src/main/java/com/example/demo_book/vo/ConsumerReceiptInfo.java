package com.example.demo_book.vo;

public class ConsumerReceiptInfo {

	private String name;
	private String ISBN;
	private String author;
	private Integer price;
	private Integer quantity;

	public ConsumerReceiptInfo() {

	}

	public ConsumerReceiptInfo(String name, String ISBN, String author, Integer price, Integer quantity) {
		this.name = name;
		this.ISBN = ISBN;
		this.author = author;
		this.price = price;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}