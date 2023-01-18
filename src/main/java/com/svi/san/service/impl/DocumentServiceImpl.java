package com.svi.san.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svi.san.entity.Document;
import com.svi.san.exception.DocumentNotFoundException;
import com.svi.san.repo.DocumentRepository;
import com.svi.san.service.IDocumentService;

@Service("docuService")
public class DocumentServiceImpl implements IDocumentService {
	
	@Autowired
	private DocumentRepository docRepo;

	@Override
	public void saveDocument(Document doc) {
		docRepo.save(doc);
		
	}

	@Override
	public List<Object[]> getDocumentByIdAndName() {
		return docRepo.getDocumentIdAndName();
	}

	@Override
	public void deleteDocumentById(Long id) {
		docRepo.deleteById(getDocumentById(id).getId());
		
	}

	@Override
	public Document getDocumentById(Long id) {
		return docRepo.findById(id).orElseThrow(()->  new DocumentNotFoundException("Document not found"));
	}

	

}
