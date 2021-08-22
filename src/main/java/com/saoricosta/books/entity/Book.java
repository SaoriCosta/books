package com.saoricosta.books.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import com.saoricosta.books.data.vo.BookVO;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book implements Serializable{
	
	private static final long serialVersionUID = 3649349284094463741L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "title", nullable = false, length = 255)
	private String title;
	
	@Column(name = "readingStatus", nullable = false, length = 255)
	private String readingStatus;
	
	@Column(name = "author", nullable = false, length = 255)
	private String author;
	
	@Column(name = "pageQuantity", nullable = false, length = 10)
	private Integer pageQuantity;
	
	@Column(name = "lastPage", nullable = false, length = 10)
	private Integer lastPage;

	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TextNote> textNotes;
	
	public static Book create(BookVO bookVO) {
		
		return new ModelMapper().map(bookVO, Book.class);
	}
}
