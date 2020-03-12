package br.com.castgroup.diffdoc.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.castgroup.diffdoc.domain.Document;
import br.com.castgroup.diffdoc.model.DocRequest;
import br.com.castgroup.diffdoc.model.DocResponse;
import br.com.castgroup.diffdoc.model.ModelApiResponse;
import br.com.castgroup.diffdoc.service.DocumentService;
import br.com.castgroup.diffdoc.util.Message;

@RunWith(MockitoJUnitRunner.class)
public class DiffDocControllerTest {

	@InjectMocks
	private final DiffDocController controler = new DiffDocController();

	@Mock
	private DocumentService service;
	
	private static String DOC_A = "docA.txt";
	private static String DOC_B = "docB.txt";
	private static String DOC_C = "docC.txt";

	@Test
	public void resultCompareTestSucess() {

		// Given
		Long codID = 1L;

		// When
		DocResponse serviceResponse = new DocResponse();
		serviceResponse.setResultMessage(String.format(Message.DOC_LENGHT_DIFFERENT.getDesc(), 1L));
		serviceResponse.setCode(Message.DOC_LENGHT_DIFFERENT.getCode());
		serviceResponse.setDifference("[1],[2]");

		when(service.compare(codID)).thenReturn(serviceResponse);

		ResponseEntity<DocResponse> response = controler.resultCompare(codID);

		// Then
		Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		Assert.assertNotNull(response.getBody().getResultMessage());
		Assert.assertNotNull(response.getBody().getCode());
		Assert.assertNotNull(response.getBody().getDifference());
	}

	@Test
	public void uploadFileLeftTestSucess() {
		
		// Given
		DocRequest request = new DocRequest();
		request.setDocumentBase64(readFileAndReturnByte(DOC_A).toString());

		// When
		when(service.save(any(Document.class))).thenReturn(true);
		ResponseEntity<ModelApiResponse> response = controler.uploadFileLeft(1L,request );
		
		// Then
		Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		Assert.assertEquals(response.getBody().getMessage(),"Documento salvo com sucesso!");

	}
	
	@Test
	public void uploadFileLeftTestFail1() {
		
		// Given
		DocRequest request = new DocRequest();
		request.setDocumentBase64(readFileAndReturnByte(DOC_A).toString());

		// When
		when(service.save(any(Document.class))).thenReturn(false);
		ResponseEntity<ModelApiResponse> response = controler.uploadFileLeft(1L,request );
		
		// Then
		Assert.assertTrue(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
		Assert.assertEquals(response.getBody().getMessage(),"Erro ao salvar documento!");

	}
	
	@Test
	public void uploadFileRightTestSucess() {
		
		// Given
		DocRequest request = new DocRequest();
		request.setDocumentBase64(readFileAndReturnByte(DOC_A).toString());

		// When
		when(service.save(any(Document.class))).thenReturn(true);
		ResponseEntity<ModelApiResponse> response = controler.uploadFileRight(1L,request );
		
		// Then
		Assert.assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		Assert.assertEquals(response.getBody().getMessage(),"Documento salvo com sucesso!");

	}
	
	@Test
	public void uploadFileRightTestFail1() {
		
		// Given
		DocRequest request = new DocRequest();
		request.setDocumentBase64(readFileAndReturnByte(DOC_A).toString());

		// When
		when(service.save(any(Document.class))).thenReturn(false);
		ResponseEntity<ModelApiResponse> response = controler.uploadFileRight(1L,request );
		
		// Then
		Assert.assertTrue(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
		Assert.assertEquals(response.getBody().getMessage(),"Erro ao salvar documento!");

	}
	

	private Document readFileReturnDocument(String fileName, Long docID, String side) {

			Document document = Document.builder()
					.docID(docID)
					.side(side)
					.id(docID)
					.content(readFileAndReturnByte(fileName)).build();

			return document;
	}
	
	private byte[] readFileAndReturnByte(String fileLocation) {
		
		try {
			
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileLocation).getFile());
			byte[] readAllBytes = Files.readAllBytes(file.toPath());
			return Base64.encodeBase64(readAllBytes);
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		return null;
	}

}
