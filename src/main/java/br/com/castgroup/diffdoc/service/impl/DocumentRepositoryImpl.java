package br.com.castgroup.diffdoc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import br.com.castgroup.diffdoc.dao.DocumentDAO;
import br.com.castgroup.diffdoc.domain.Document;
import br.com.castgroup.diffdoc.service.DocumentRepository;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {
	
	private DocumentDAO documentDAO;

	@Autowired
	public DocumentRepositoryImpl(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	@Override
	public List<Document> findByDocID(Long docID) {
		return documentDAO.findByDocID(docID);
	}

	@Override
	public boolean saveDocument(Document document) {
		
		Document responseDoc = documentDAO.saveAndFlush(document);		
		
		return responseDoc.getId() == null ? false : true;
		
	}
	
}
