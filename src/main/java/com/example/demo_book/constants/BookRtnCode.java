package com.example.demo_book.constants;

public enum BookRtnCode {

	SUCCESSFUL("200", "Success"), ISBN_EXISTED("400", "ISBN is existed!!"), 
	ISBN_WRONG("400", "ISBN length is wrong!!"),
	ITEM_EMPTY("400", "Item is empty!!"), 
	SALE_INITIAL("400", "Initial sales volume must be 0!!"),
	NO_RESULT("400", "No result!!"), 
	NO_STOCK("400", "Inventory shortage!!");

	private String code;
	private String message;

	private BookRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
