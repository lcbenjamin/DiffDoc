package br.com.castgroup.diffdoc.util;

public enum DocENUM {

	LEFT("LEFT"), 
	RIGHT("RIGHT");

	private String desc;

	private DocENUM(String desc) {
	        this.desc = desc;
	    }

	public String getDesc() {
		return desc;
	}

}
