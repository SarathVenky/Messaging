package com.message.model;

import java.util.List;

import com.message.repo.Message;

/**
 * 
 * @author sarathvenky
 * 
 * Response model for the Message APIs
 *
 */

public class MessageResponse {
	
	private List<Message> messages;

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	
	
}
