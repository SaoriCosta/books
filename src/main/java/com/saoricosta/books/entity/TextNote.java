package com.saoricosta.books.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.saoricosta.books.data.vo.TextNoteVO;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "text_note")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TextNote implements Serializable{
	
	private static final long serialVersionUID = 4406975183504913871L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@Column(name = "note", nullable = false, length = 255)
	private String note;
	
	@Column(name = "imageUrl", nullable = false, length = 255)
	private String imageUrl;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	@NotNull
	private Book book;
	
	public static TextNote create(TextNoteVO textNoteVO) {
		
		return new ModelMapper().map(textNoteVO, TextNote.class);
	}

}
