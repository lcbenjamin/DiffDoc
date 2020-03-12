package br.com.castgroup.diffdoc.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.castgroup.diffdoc.domain.Document;
import br.com.castgroup.diffdoc.model.DocResponse;
import br.com.castgroup.diffdoc.service.impl.DocumentServiceImpl;
import br.com.castgroup.diffdoc.util.Message;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceImplTest {

	@Mock
	private DocumentRepository repository;

	@InjectMocks
	private final DocumentServiceImpl service = new DocumentServiceImpl(repository);
	
	private static String DOC_A = "docA.txt";
	private static String DOC_B = "docB.txt";
	private static String DOC_C = "docC.txt";

	@Test
	public void saveTestSucess() {

		// Given
		Document document = readFileReturnDocument(DOC_A, 1L, "LEFT");
		
		// When
		when(repository.saveDocument(any(Document.class))).thenReturn(true);
		boolean response = service.save(document);
		
		// Then
		Assert.assertTrue(response);
		
	}
	
	@Test
	public void compareTestSameLenghtDiffContentSucess() {

		// Given
		Long docID = 1L;
		List<Document> documents = new ArrayList<>();
		
		documents.add( Document.builder()
						.content(readFileAndReturnByte(DOC_A)).docID(docID)
						.id(1L).side("LEFT").build() );
		
		documents.add( Document.builder()
						.content(readFileAndReturnByte(DOC_B)).docID(docID)
						.id(2L).side("RIGHT").build() );
		
		// When
		when(repository.findByDocID(docID)).thenReturn(documents);
		DocResponse response = service.compare(docID);
		
		// Then
		Assert.assertEquals(response.getCode(),Message.DOC_DIFF.getCode());
		
	}
	
	@Test
	public void compareTestDiffLenghtSucess() {

		// Given
		Long docID = 1L;
		List<Document> documents = new ArrayList<>();
		
		documents.add( Document.builder()
						.content(readFileAndReturnByte(DOC_A)).docID(docID)
						.id(1L).side("LEFT").build() );
		
		documents.add( Document.builder()
						.content(readFileAndReturnByte(DOC_C)).docID(docID)
						.id(2L).side("RIGHT").build() );
		
		// When
		when(repository.findByDocID(docID)).thenReturn(documents);
		DocResponse response = service.compare(docID);
		
		// Then
		Assert.assertEquals(response.getCode(),Message.DOC_LENGHT_DIFFERENT.getCode());
		
	}
	
	@Test
	public void compareTestFileEqualsSucess() {

		// Given
		Long docID = 1L;
		List<Document> documents = new ArrayList<>();
		
		documents.add( Document.builder()
						.content(readFileAndReturnByte(DOC_A)).docID(docID)
						.id(1L).side("LEFT").build() );
		
		documents.add( Document.builder()
						.content(readFileAndReturnByte(DOC_A)).docID(docID)
						.id(2L).side("RIGHT").build() );
		
		// When
		when(repository.findByDocID(docID)).thenReturn(documents);
		DocResponse response = service.compare(docID);
		
		// Then
		Assert.assertEquals(response.getCode(),Message.DOC_EQUAL.getCode());
		
	}

	private Document readFileReturnDocument(String fileName, Long docID, String side) {

		Document document = Document.builder().docID(docID).side(side).id(docID)
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
