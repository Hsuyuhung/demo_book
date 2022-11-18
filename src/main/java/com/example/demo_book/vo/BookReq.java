package com.example.demo_book.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookReq {

	@JsonProperty(value = "ISBN")
	private String ISBN;
	private String name;
	private String author;
	private String category;
	private int price;
	private int purchase;
	private int sales;
	private String password;
	private Map<String, Integer> buyBook;

	public BookReq() {

	}

	public BookReq(String ISBN, String name, String author, String category, int price, int purchase, int sales) {
		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.category = category;
		this.price = price;
		this.purchase = purchase;
		this.sales = sales;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPurchase() {
		return purchase;
	}

	public void setPurchase(int purchase) {
		this.purchase = purchase;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Integer> getBuyBook() {
		return buyBook;
	}

	public void setBuyBook(Map<String, Integer> buyBook) {
		this.buyBook = buyBook;
	}
}