package br.com.castgroup.diffdoc.service;

import br.com.castgroup.diffdoc.domain.Document;
import br.com.castgroup.diffdoc.model.DocResponse;

public interface DocumentService {

	boolean save(Document document);
	DocResponse compare(long docID);
	
}
