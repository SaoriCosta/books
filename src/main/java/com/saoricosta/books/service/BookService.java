package com.saoricosta.books.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saoricosta.books.data.vo.BookVO;
import com.saoricosta.books.entity.Book;
import com.saoricosta.books.repository.BookRepository;
import com.saoricosta.books.exception.ResourceNotFoundException;

@Service
public class BookService {
	
	private final BookRepository bookRepository;
	
	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public BookVO create(BookVO bookVO) {
		
		BookVO bookVOReturn = BookVO.create(bookRepository.save(Book.create(bookVO)));
		
		return bookVOReturn;
	}
	
	private BookVO convertToBookVO(Book book) {
		
		return BookVO.create(book);
	}
	
	public Page<BookVO> findAll(Pageable pageable){
		
		var page = bookRepository.findAll(pageable);
		
		return page.map(this::convertToBookVO);
		
	}
	
	public BookVO findById(Long id) {
		
		var book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID")); 
		
		return BookVO.create(book);
	}
	
	public BookVO update(BookVO bookVO) {
		
		final Optional<Book> optionalBook = bookRepository.findById(bookVO.getId());
		
		if(!optionalBook.isPresent()) {
			
			new ResourceNotFoundException("No records found for this ID");
		}
		
		return BookVO.create(bookRepository.save(Book.create(bookVO)));
		
	}
	
	public void delete(Long id) {
		
		var book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		bookRepository.delete(book);
	}

}
