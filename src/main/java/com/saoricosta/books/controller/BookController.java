package com.saoricosta.books.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saoricosta.books.data.vo.BookVO;
import com.saoricosta.books.service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

	private final BookService bookService;
	private final PagedResourcesAssembler<BookVO> assembler;
	
	@Autowired
	public BookController(BookService bookService, PagedResourcesAssembler<BookVO> assembler) {
		super();
		this.bookService = bookService;
		this.assembler = assembler;
	}
	
	@GetMapping(value = "/{id}", produces =  {"application/json","application/xml","application/x-yaml"})
	public BookVO findById(@PathVariable("id") Long id) {
		
		BookVO bookVO = bookService.findById(id);
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return bookVO;
		
	}
	
	@GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"name"));
				
		Page<BookVO> books = bookService.findAll(pageable);
		
		books.stream()
		.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getId())).withSelfRel()));
		
		PagedModel<EntityModel<BookVO>> pagedModel = assembler.toModel(books);
		
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	@PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public BookVO create(@RequestBody BookVO bookVO) {
		
		BookVO bookVOReturn = bookService.create(bookVO);
		bookVOReturn.add(linkTo(methodOn(BookController.class).findById(bookVOReturn.getId())).withSelfRel());
		
		return bookVOReturn;
		
	}
	
	@PutMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public BookVO update(@RequestBody BookVO bookVO) {
		
		BookVO bookVOReturn = bookService.update(bookVO);
		bookVOReturn.add(linkTo(methodOn(BookController.class).findById(bookVOReturn.getId())).withSelfRel());
		
		return bookVOReturn;
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		
		bookService.delete(id);
		
		return ResponseEntity.ok().build();
		
	}
	
	
	
}
