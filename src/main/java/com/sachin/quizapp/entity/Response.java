package com.sachin.quizapp.entity;

/**
 * The `Response` class represents a user's response to a quiz question. It
 * contains attributes to store the response's unique identifier and the user's
 * answer.
 *
 * @author Sachin Rathod
 */
public class Response {

	private Long id;
	private String response;

	public Response() {
		super();
	}

	public Response(Long id, String response) {
		super();
		this.id = id;
		this.response = response;
	}

	public Response(String response) {
		super();
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "Response [id=" + id + ", response=" + response + "]";
	}
}
