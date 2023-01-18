package com.svi.san.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.svi.san.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
	
	@Query("SELECT id,docName FROM Document")
	List<Object[]> getDocumentIdAndName();

}
