package com.saoricosta.books.data.vo;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.saoricosta.books.entity.Book;
import com.saoricosta.books.entity.TextNote;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@JsonPropertyOrder({"id","name", "note", "imageUrl", "book"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TextNoteVO extends RepresentationModel<TextNoteVO> implements Serializable{

	private static final long serialVersionUID = -4928272917618533548L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("note")
	private String note;
	
	@JsonProperty("imageUrl")
	private String imageUrl;
	
	@JsonProperty("book")
	private Book book;
	
	public static TextNoteVO create(TextNote textNote) {
		
		return new ModelMapper().map(textNote, TextNoteVO.class);
		
	}
}
