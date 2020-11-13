package com.bcnit14.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StringDto {

	@JsonProperty("message")
	private String message;

	public StringDto() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static StringDto stringToJson(String message) {
		StringDto stringJson = new StringDto();
		stringJson.setMessage(message);
		return stringJson;
	}
}