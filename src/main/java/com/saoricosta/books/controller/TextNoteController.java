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

import com.saoricosta.books.data.vo.TextNoteVO;
import com.saoricosta.books.service.TextNoteService;

@RestController
@RequestMapping("/api/note")
public class TextNoteController {
	
	private final TextNoteService textNoteService;
	private final PagedResourcesAssembler<TextNoteVO> assembler;
	
	@Autowired
	public TextNoteController(TextNoteService textNoteService, PagedResourcesAssembler<TextNoteVO> assembler) {
		this.textNoteService = textNoteService;
		this.assembler = assembler;
	}
	
	@GetMapping(value = "/{id}", produces =  {"application/json","application/xml","application/x-yaml"})
	public TextNoteVO findById(@PathVariable("id") Long id) {
		
		TextNoteVO textNoteVO = textNoteService.findById(id);
		textNoteVO.add(linkTo(methodOn(TextNoteController.class).findById(id)).withSelfRel());
		
		return textNoteVO;
		
	}
	
	@GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"name"));
				
		Page<TextNoteVO> textNotes = textNoteService.findAll(pageable);
		
		textNotes.stream()
		.forEach(p -> p.add(linkTo(methodOn(TextNoteController.class).findById(p.getId())).withSelfRel()));
		
		PagedModel<EntityModel<TextNoteVO>> pagedModel = assembler.toModel(textNotes);
		
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	@PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public TextNoteVO create(@RequestBody TextNoteVO textNoteVO) {
		
		TextNoteVO textNoteVOReturn = textNoteService.create(textNoteVO);
		textNoteVOReturn.add(linkTo(methodOn(TextNoteController.class).findById(textNoteVOReturn.getId())).withSelfRel());
		
		return textNoteVOReturn;
		
	}
	
	@PutMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public TextNoteVO update(@RequestBody TextNoteVO textNoteVO) {
		
		TextNoteVO textNoteVOReturn = textNoteService.update(textNoteVO);
		textNoteVOReturn.add(linkTo(methodOn(BookController.class).findById(textNoteVOReturn.getId())).withSelfRel());
		
		return textNoteVOReturn;
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		
		textNoteService.delete(id);
		
		return ResponseEntity.ok().build();
		
	}

}
