package com.saoricosta.books.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saoricosta.books.data.vo.TextNoteVO;
import com.saoricosta.books.entity.TextNote;
import com.saoricosta.books.exception.ResourceNotFoundException;
import com.saoricosta.books.repository.TextNoteRepository;

@Service
public class TextNoteService {
	
	private final TextNoteRepository textNoteRepository;

	@Autowired
	public TextNoteService(TextNoteRepository textNoteRepository) {
		this.textNoteRepository = textNoteRepository;
	}
	
	public TextNoteVO create(TextNoteVO textNoteVO) {
		
		TextNoteVO textNoteVOReturn = TextNoteVO.create(textNoteRepository.save(TextNote.create(textNoteVO)));
		
		return textNoteVOReturn;
	}
	
	private TextNoteVO convertToTextNoteVO(TextNote textNote) {
		
		return TextNoteVO.create(textNote);
	}
	
	public Page<TextNoteVO> findAll(Pageable pageable){
		
		var page = textNoteRepository.findAll(pageable);
		
		return page.map(this::convertToTextNoteVO);
		
	}
	
	public TextNoteVO findById(Long id) {
		
		var textNote = textNoteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID")); 
		
		return TextNoteVO.create(textNote);
	}
	
	public TextNoteVO update(TextNoteVO textNoteVO) {
		
		final Optional<TextNote> optionalTextNote = textNoteRepository.findById(textNoteVO.getId());
		
		if(!optionalTextNote.isPresent()) {
			
			new ResourceNotFoundException("No records found for this ID");
		}
		
		return TextNoteVO.create(textNoteRepository.save(TextNote.create(textNoteVO)));
		
	}
	
	public void delete(Long id) {
		
		var textNote = textNoteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		textNoteRepository.delete(textNote);
	}
	

}
