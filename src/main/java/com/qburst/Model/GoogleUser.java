package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleUser {
	@JsonProperty("id_token")
	private String id_token;
	
	public String getIdToken() {
		return id_token;
	}
	
	public void setIdToken(String id_token) {
		this.id_token = id_token;
	}
}
