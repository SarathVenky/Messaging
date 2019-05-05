package com.message.model;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author sarathvenky
 * 
 * Request model for the Message APIs
 * 
 *
 */
public class MessageRequest {
	
	@NotNull
	private String from;
	@NotNull
	private String to;
	@NotNull
	private String subject;
	private String body;
	
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	
	
	
	

}
