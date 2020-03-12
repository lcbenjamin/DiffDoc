package br.com.castgroup.diffdoc.util;

public enum Message {

	DOC_LENGHT_DIFFERENT(1, "Documentos com o ID %s com tamanho diferente "),
	DOC_LENGHT_EQUAL(2, "Documentos com o ID %s com tamanho igual"),
	DOC_EQUAL(3, "Documentos com o ID %s são iguais"),
	DOC_DIFF(4, "Documentos com o ID %s são diferentes");

	private Integer code;
	private String desc;

	private Message(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public Integer getCode() {
		return this.code;
	}
}
