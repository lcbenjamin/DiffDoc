package br.com.castgroup.diffdoc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.castgroup.diffdoc.domain.Document;

public interface DocumentDAO extends JpaRepository<Document, Long>{
	
	List<Document> findByDocID(Long docID);

}
