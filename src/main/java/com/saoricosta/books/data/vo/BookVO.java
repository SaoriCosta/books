package com.saoricosta.books.data.vo;

import java.io.Serializable;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.saoricosta.books.entity.Book;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({"id","date","title","readingStatus","author","pageQuantity","lastPage"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BookVO extends RepresentationModel<BookVO> implements Serializable{
	 
	private static final long serialVersionUID = 3238408867413953027L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("date")
	private Date date;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("readingStatus")
	private String readingStatus;
	
	@JsonProperty("author")
	private String author;
	
	@JsonProperty("pageQuantity")
	private Integer pageQuantity;
	
	@JsonProperty("lastPage")
	private Integer lastPage;

	
	public static BookVO create(Book book) {
		
		return new ModelMapper().map(book, BookVO.class);
		
	}
}
