package br.com.castgroup.diffdoc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.castgroup.diffdoc.domain.Document;

public class CompareUtil {

	/**
	 * Scans documents for differences and reports relative positions
	 * 
	 * @param docRight Document
	 * @param docLeft  Document
	 * @return List<Integer> positions offSet
	 */
	public static List<Integer> documentsDifference(Document docRight, Document docLeft) {

		List<Integer> difference = new ArrayList<>();

		int ll = docLeft.getContent().length;
		int rl = docRight.getContent().length;

		// Compara quando possivel o conteudo dos dois arquivos
		for (int i = 0; i < ll && i < rl; i++) {

			if (Byte.compare(docRight.getContent()[i], docLeft.getContent()[i]) != 0) {
				difference.add(i);
			}
		}
		
		// Adiciona o conteudo a mais do documento da esquerda, se existir
		if(ll > rl ) {
			// TODO: Implementar logica para adicionar a lista as posições que não fazem parte do documento da direita 
		}
		
		// Adiciona o conteudo a mais do documento da esquerda, se existir
		if(rl > ll ) {
			// TODO: Implementar logica para adicionar a lista as posições que não fazem parte do documento da esquerda 
		}
		
		return difference;

	}

	/**
	 * Checks if the documents informed are the same
	 * 
	 * @param docLeft  Document
	 * @param docRight Document
	 * @return boolean
	 */
	public static boolean documentsEquals(Document docLeft, Document docRight) {

		return Arrays.equals(docLeft.getContent(), docRight.getContent());
	}

	/**
	 * Checks whether documents are different sizes
	 * @param docLeft Document
	 * @param docRight Document
	 * @return boolean
	 */
	public static boolean documentDiffentSize(Document docLeft, Document docRight) {
		
		int sizeLeft = docLeft.getContent().length;
		int sizeRight = docRight.getContent().length;
		
		return (sizeLeft != sizeRight) ? true : false ;
	
	}
}
