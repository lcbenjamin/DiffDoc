package br.com.castgroup.diffdoc.presentation;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.castgroup.diffdoc.api.V1Api;
import br.com.castgroup.diffdoc.domain.Document;
import br.com.castgroup.diffdoc.model.DocRequest;
import br.com.castgroup.diffdoc.model.DocResponse;
import br.com.castgroup.diffdoc.model.ModelApiResponse;
import br.com.castgroup.diffdoc.service.DocumentService;
import br.com.castgroup.diffdoc.util.DocENUM;
import io.swagger.annotations.Api;

@RestController
@Api(value = "document", description = "Diff Doc Document")
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class DiffDocController implements V1Api {

	@Autowired
	private DocumentService service;
	
	@Override
	public ResponseEntity<DocResponse> resultCompare(Long docID) {

		DocResponse response = service.compare(docID);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ModelApiResponse> uploadFileLeft(Long ID, @Valid DocRequest documentBase64) {

		ModelApiResponse response = new ModelApiResponse();

		Document document = Document.builder()
				.docID(ID)
				.side(DocENUM.LEFT.getDesc())
				.content(documentBase64.getDocumentBase64().getBytes()).build();

		boolean responseService = service.save(document);

		if (responseService) {

			response.setCode(1);
			response.setMessage("Documento salvo com sucesso!");
			response.setType("LEFT");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {

			response.setCode(0);
			response.setMessage("Erro ao salvar documento!");
			response.setType("LEFT");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<ModelApiResponse> uploadFileRight(Long ID, @Valid DocRequest documentBase64) {

		ModelApiResponse response = new ModelApiResponse();

		Document document = Document.builder()
				.docID(ID)
				.side(DocENUM.RIGHT.getDesc())
				.content(documentBase64.getDocumentBase64().getBytes()).build();

		boolean responseService = service.save(document);

		if (responseService) {

			response.setCode(1);
			response.setMessage("Documento salvo com sucesso!");
			response.setType("RIGHT");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {

			response.setCode(0);
			response.setMessage("Erro ao salvar documento!");
			response.setType("RIGHT");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
