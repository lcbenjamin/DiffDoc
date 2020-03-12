package br.com.castgroup.diffdoc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Builder;

@Entity
@Builder
public class Document {

	@Id
	@GeneratedValue
	private Long id;

	private Long docID;

	private String side;

	@Lob
	@Column(length = 100000)
	private byte[] content;

	public Document() {
	}

	public Document(Long id, Long docID, String side, byte[] contentBase64) {
		this.id = id;
		this.docID = docID;
		this.side = side;
		this.content = contentBase64;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDocID() {
		return docID;
	}

	public void setDocID(Long docID) {
		this.docID = docID;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
