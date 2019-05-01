package com.message.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	
	private String sender;
	private String receiver;
	private String subject;
	private String body;
	private String sentDate;

}
