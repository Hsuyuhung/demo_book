package com.example.demo_book.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name = "book")
public class Book {

	@Id
	@Column(name = "ISBN")
	private String ISBN;

	@Column(name = "name")
	private String name;

	@Column(name = "author")
	private String author;

	@Column(name = "category")
	private String category;

	@Column(name = "price")
	private Integer price;

	@Column(name = "purchase")
	private Integer purchase;

	@Column(name = "sales")
	private Integer sales = 0;

	public Book() {

	}

	public Book(String ISBN, String name, String author, String category, Integer price, Integer purchase,
			Integer sales) {
		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.category = category;
		this.price = price;
		this.purchase = purchase;
		this.sales = sales;
	}

	public Book(String name, String author, String category, Integer price, Integer purchase, Integer sales) {
		this.name = name;
		this.author = author;
		this.category = category;
		this.price = price;
		this.purchase = purchase;
		this.sales = sales;
	}

	public Book(String ISBN, String name, String author, String category, Integer price, Integer purchase) {
		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.category = category;
		this.price = price;
		this.purchase = purchase;
	}

	public Book(String ISBN, Integer sales) {
		this.ISBN = ISBN;
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

	public Integer getPrice() {
		return price;
	}

	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}