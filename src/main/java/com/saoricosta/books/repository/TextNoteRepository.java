package com.saoricosta.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saoricosta.books.entity.TextNote;

public interface TextNoteRepository extends JpaRepository<TextNote, Long> {

}
