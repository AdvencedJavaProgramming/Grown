package com.Polodz.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StringWebData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5766409459648709226L;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
