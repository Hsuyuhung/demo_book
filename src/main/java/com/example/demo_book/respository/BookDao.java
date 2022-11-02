package com.example.demo_book.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_book.entity.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Integer> {

}
