package com.saoricosta.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saoricosta.books.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
