package com.example.demo_book.constants;

public enum BookMessageCode {

	CREATE_INFO_SUCCESSFUL("200", "創建成功。"),
	SEARCH_SUCCESSFUL("200", "搜尋成功。"),
	UPDATE_SUCCESSFUL("200", "更新成功。"),
	SALE_SUCCESSFUL("200", "感謝您的購買。"),
	ISBN_EXISTED("400", "ISBN已存在。"), 
	ISBN_WRONG("400", "ISBN錯誤。"),
	ISBN_LENGTH("400", "ISBN長度錯誤。"), 
	ISBN_NULL("400", "查無此ISBN。"),
	ITEM_EMPTY("400", "項目不得為空。"), 
	ITEM_WRONG("400", "請輸入欲變更項目。"),
	SALE_INITIAL("400", "初始銷售數量需為0。"),
	NUMBER_ERRO("400", "數字不得小於0。"), 
	NO_RESULT("400", "查無結果。"),
	NO_STOCK("400", "庫存短缺。");

	private String code;
	private String message;

	private BookMessageCode(String code, String message) {
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