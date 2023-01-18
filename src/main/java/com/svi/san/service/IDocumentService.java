package com.svi.san.service;

import java.util.List;

import com.svi.san.entity.Document;

public interface IDocumentService {
	
 void saveDocument(Document doc);
 List<Object[]> getDocumentByIdAndName();
 void deleteDocumentById(Long id);
 Document getDocumentById(Long id);
}
