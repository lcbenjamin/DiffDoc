package br.com.castgroup.diffdoc.service;

import java.util.List;

import br.com.castgroup.diffdoc.domain.Document;

public interface DocumentRepository {
	
	List<Document> findByDocID(Long docID);
	boolean saveDocument(Document document);

}
