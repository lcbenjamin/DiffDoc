package br.com.castgroup.diffdoc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.castgroup.diffdoc.domain.Document;
import br.com.castgroup.diffdoc.model.DocResponse;
import br.com.castgroup.diffdoc.service.DocumentRepository;
import br.com.castgroup.diffdoc.service.DocumentService;
import static br.com.castgroup.diffdoc.util.CompareUtil.*;
import br.com.castgroup.diffdoc.util.DocENUM;
import br.com.castgroup.diffdoc.util.Message;

@Service
public class DocumentServiceImpl implements DocumentService {

	private DocumentRepository repository;

	public DocumentServiceImpl(DocumentRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean save(Document document) {
		return repository.saveDocument(document);
	}

	@Override
	public DocResponse compare(long docID) {

		List<Document> documents = repository.findByDocID(docID);
		DocResponse response = new DocResponse();

		Document docRight = documents.stream()
				.filter(doc -> DocENUM.RIGHT.getDesc().equals(doc.getSide()))
				.findAny()
				.orElse(null);

		Document docLeft = documents.stream()
				.filter(doc -> DocENUM.LEFT.getDesc().equals(doc.getSide()))
				.findAny()
				.orElse(null);

		// Valida se os documentos são iguais em tamanho e conteudo
		if (documentsEquals(docRight, docLeft)) {

			response.setResultMessage(String.format(Message.DOC_EQUAL.getDesc(), docID));
			response.setCode(Message.DOC_EQUAL.getCode());
			response.setDifference(null);
			
			return response;

		}

		// Valida se os documentos são diferentes em tamanho
		if (documentDiffentSize(docLeft, docRight)) {

			response.setResultMessage(String.format(Message.DOC_LENGHT_DIFFERENT.getDesc(), docID));
			response.setCode(Message.DOC_LENGHT_DIFFERENT.getCode());
			response.setDifference(null);
			
			return response;
		}

		// Valida se os documentos tem o mesmo tamanho, porem conteudo diferente
		if (!documentDiffentSize(docLeft, docRight)) {

			response.setResultMessage(String.format(Message.DOC_DIFF.getDesc(), docID));
			response.setCode(Message.DOC_DIFF.getCode());
			response.setDifference(documentsDifference(docRight, docLeft).toString());
			
			return response;
		}
		
		return response;

	}

}
