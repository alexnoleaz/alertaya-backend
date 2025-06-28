package com.alertaya.backend.shared;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
	private String status;
	private Object data;
	private String message;
	private Instant timestamp;

	@JsonIgnore
	private HttpStatus code;

	public static Response success() {
		return Response.success(null);
	}

	public static Response success(Object data) {
		return Response.success(data, HttpStatus.OK);
	}

	public static Response success(Object data, HttpStatus code) {
		return new Response("success", data, null, code);
	}

	public static Response fail(Object data) {
		return Response.fail(data, HttpStatus.BAD_REQUEST);
	}

	public static Response fail(Object data, HttpStatus code) {
		return new Response("fail", data, null, code);
	}

	public static Response error(String message) {
		return Response.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static Response error(String message, HttpStatus code) {
		return new Response("error", null, message, code);
	}

	private Response(String status, Object data, String message, HttpStatus code) {
		this.status = status;
		this.data = data;
		this.message = message;
		this.code = code;
		this.timestamp = Instant.now();
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public HttpStatus getCode() {
		return code;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	@JsonProperty("code")
	public int getCodeValue() {
		return code.value();
	}
}
