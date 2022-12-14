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

	// 創建書籍資料
	@Override
	public Book newRelease(String ISBN, String name, String author, String category, Integer price, Integer purchase) {

		// 如果為已存在ISBN，則回傳null，於controller進行判斷，回傳錯誤訊息
		if (bookDao.existsById(ISBN)) {
			return null;
		}

		Book book = new Book(ISBN, name, author, category, price, purchase);
		return bookDao.save(book);
	}

	// 更新書籍資料
	@Override
	public BookRes updateBookInfo(String ISBN, String name, String author, String category, Integer price,
			Integer purchase, Integer sales) {

		BookRes res = new BookRes();

		Optional<Book> bookOp = bookDao.findById(ISBN);
		Book book = bookOp.get();

		// 如查無ISBN，則回傳錯誤訊息
		if (!bookOp.isPresent()) {
			return new BookRes(BookMessageCode.ISBN_WRONG.getMessage());
		}

		// 透過私有方法進行防呆
		setParams(book, name, author, category, price, purchase, sales);
		bookDao.save(book);
		res.setBook(book);
		res.setMessage(BookMessageCode.UPDATE_SUCCESSFUL.getMessage());
		return res;
	}

	// 判斷輸入值是否存回資料庫、是否小於0之私有方法
	private void setParams(Book book, String name, String author, String category, Integer price, Integer purchase,
			Integer sales) {// parameter

		// 如有輸入值，則將輸入值存回資料庫
		if (StringUtils.hasText(name)) {
			book.setName(name);
		}

		if (StringUtils.hasText(author)) {
			book.setAuthor(author);
		}

		if (StringUtils.hasText(category)) {
			book.setCategory(category);
		}

		// 如價格、庫存、銷售大於等於0，則將輸入值存回資料庫
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

	// 透過類別搜尋書籍資料
	@Override
	public List<Book> searchCategory(String category) {

		// 將輸入值以「,」切分(split)，並去除字串頭尾空白(trim)，最後將輸入值放入Set去除重複
		String[] stringList = category.split(",");
		Set<String> bookSet = new HashSet<>();
		for (String item : stringList) {
			bookSet.add(item.trim());
		}

		// 將上方取得之Set進行遍歷，比對輸入值跟資料庫是否有相符值
		List<Book> newBookList = new ArrayList<>();
		for (String item1 : bookSet) {
			List<Book> bookList = bookDao.findByCategoryContaining(item1);
			
			// 如果輸入值不存在於資料庫，則回傳null，於controller進行判斷，回傳錯誤訊息
			if (bookList.isEmpty()) {
				return null;
			}
			
			// 為不顯示Category與Sales，故使用遍歷將List內所有此2項目設定成null，並透過JsonInclude使其不顯示
			for (Book item2 : bookList) {
				item2.setCategory(null);
				item2.setSales(null);
				newBookList.add(item2);
			}
		}

		return newBookList;
	}

	// 透過ISBN、書名、作者名等任意項目搜尋書籍資料(根據密碼分為書籍商、使用者2種顯示版本)
	@Override
	public BookRes getBookInfoByISBNOrNameOrAuthor(String password, String ISBN, String name, String author) {

		BookRes res = new BookRes();
		List<Book> bookList = bookDao.findByISBNOrNameOrAuthor(ISBN, name, author);
		// 書籍商版本之List
		List<Book> consumerBookList = new ArrayList<>();
		// 使用者版本之List
		List<Book> storeBookList = new ArrayList<>();

		// 如無輸入密碼，則回傳錯誤訊息
		if (!StringUtils.hasText(password)) {
			res.setMessage(BookMessageCode.ITEM_EMPTY.getMessage());
			return res;
		}

		// 如查無任何資料，則回傳null，於controller進行判斷，回傳錯誤訊息
		if (bookList.isEmpty()) {
			return null;
		}

		// 如果輸入正確密碼(0)，則回傳書籍商版本資料
		if (password.equals("0")) {
			
			// 為不顯示Category項目，故進行遍歷設定為null不顯示
			for (Book item : bookList) {
				item.setCategory(null);
				storeBookList.add(item);
			}
			res.setStoreBookList(storeBookList);
			res.setMessage(BookMessageCode.SEARCH_SUCCESSFUL.getMessage());
			return res;
		} else {
			// 如未輸入正確密碼，則回傳使用者版本資料
			for (Book item : bookList) {
				item.setCategory(null);
				item.setPurchase(null);
				item.setSales(null);
				consumerBookList.add(item);
			}

			res.setConsumerBookList(consumerBookList);
			res.setMessage(BookMessageCode.SEARCH_SUCCESSFUL.getMessage());
			return res;
		}
	}

	// 更新庫存
	@Override
	public Book updatePurchase(String ISBN, Integer purchase) {

		Optional<Book> bookOp = bookDao.findById(ISBN);
		
		// 如ISBN存在，則將取得之新庫存量存回資料庫，存完後再將不顯示之項目設定為null
		if (bookOp.isPresent()) {
			Book book = bookOp.get();
			book.setPurchase(purchase);
			bookDao.save(book);
			book.setCategory(null);
			book.setSales(null);
			return book;
		}

		// 如果ISBN不存在，則回傳null，於controller進行判斷，回傳錯誤訊息
		return null;
	}

	// 更新價格
	@Override
	public Book updatePrice(String ISBN, Integer price) {

		Optional<Book> bookOp = bookDao.findById(ISBN);
		
		// 如ISBN存在，則將取得之新價格存回資料庫，存完後再將不顯示之項目設定為null
		if (bookOp.isPresent()) {
			Book book = bookOp.get();
			book.setPrice(price);
			bookDao.save(book);
			book.setCategory(null);
			book.setSales(null);
			return book;
		}

		// 如果ISBN不存在，則回傳null，於controller進行判斷，回傳錯誤訊息
		return null;
	}

	// 銷售書籍
	@Override
	public BookRes sales(Map<String, Integer> buyBook) {

		BookRes res = new BookRes();
		List<ConsumerReceiptInfo> receipt = new ArrayList<>();
		// 預設總金額為0
		int total = 0;

		// 遍歷Map取得ISBN與購買數量，並計算金額
		for (Map.Entry<String, Integer> item : buyBook.entrySet()) {
			
			// 如Key值(ISBN)為空，則回傳錯誤訊息
			if (!StringUtils.hasText(item.getKey())) {
				return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
			}

			Optional<Book> bookOp = bookDao.findById(item.getKey());
			// 如Key值(ISBN)不存在，則回傳錯誤訊息
			if (!bookOp.isPresent()) {
				return new BookRes(BookMessageCode.ISBN_NULL.getMessage());
			}

			Integer value = item.getValue();
			// 如Value值(購買數量)小於0，則回傳錯誤訊息
			if (value == null || value < 0) {
				return new BookRes(BookMessageCode.ITEM_EMPTY.getMessage());
			}

			// 如Value值(購買數量)大於Purchase(庫存量)，則回傳錯誤訊息
			if (value > bookOp.get().getPurchase()) {
				return new BookRes(BookMessageCode.NO_STOCK.getMessage());
			}

			// 根據購買數量，更新Sales(增加銷售)與Purchase(減少庫存)數量
			Book storeBook = bookOp.get();
			storeBook.setSales(storeBook.getSales() + value);
			storeBook.setPurchase(storeBook.getPurchase() - value);
			bookDao.save(storeBook);

			// 計算購買總金額
			total += bookOp.get().getPrice() * value;

			ConsumerReceiptInfo consumerBuyBook = new ConsumerReceiptInfo(storeBook.getName(), storeBook.getISBN(),
					storeBook.getAuthor(), storeBook.getPrice(), value);
			receipt.add(consumerBuyBook);

		}

		res.setConsumerReceipt(receipt);
		res.setTotalPrice(total);
		res.setMessage(BookMessageCode.SALE_SUCCESSFUL.getMessage());
		return res;

	}

	// 銷售排行前5
	@Override
	public List<Book> bestSeller() {

		// 使用JPA DAO方法排序銷售前5五之書籍資訊
		List<Book> bookList = bookDao.findTop5ByOrderBySalesDesc();
		List<Book> newBookList = new ArrayList<>();
		// 將不顯示之項目設定為null
		for (Book item : bookList) {
			item.setCategory(null);
			item.setPurchase(null);
			item.setSales(null);
			newBookList.add(item);
		}

		return newBookList;
	}
}