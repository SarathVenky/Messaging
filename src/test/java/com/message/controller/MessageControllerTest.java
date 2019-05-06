package com.message.controller;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.message.model.MessageRequest;
import com.message.repo.Message;
import com.message.repo.MessageRepository;

/**
 * 
 * @author sarathvenky
 *
 */
public class MessageControllerTest {

	@Mock
	MessageRepository messageRepo;
	@InjectMocks
	MessageController messageController;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSendMessage() {
		MessageRequest messageRequest = Mockito.mock(MessageRequest.class);
		Mockito.when(messageRequest.getBody()).thenReturn("body");
		Mockito.when(messageRequest.getFrom()).thenReturn("abc");
		Mockito.when(messageRequest.getTo()).thenReturn("xyz");
		Mockito.when(messageRequest.getSubject()).thenReturn("hi");
		ResponseEntity<String> response = messageController.sendMessage(messageRequest);
		Assert.assertSame(HttpStatus.OK, response.getStatusCode());
		Assert.assertSame("Message sent successfully", response.getBody());
	}

	@Test
	public void testSendMessage_when_request_object_is_null() {
		ResponseEntity<String> response = messageController.sendMessage(null);
		Assert.assertSame(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
	}
	
	@Test
	public void testreadMessagesSentByUser() {
		List<Message> messages = Mockito.mock(List.class);
		Message message = Mockito.mock(Message.class);
		Mockito.when(message.getSender()).thenReturn("venky");
		messages.add(message);
		Mockito.when(messageRepo.findBySender(Mockito.anyString())).thenReturn(messages);
		ResponseEntity<List<Message>> response = messageController.readMessagesSentByUser("user");
		Assert.assertSame(HttpStatus.OK, response.getStatusCode());
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testreadMessagesSentByUser_when_response_is_null_return_not_found() {
		Mockito.when(messageRepo.findBySender(Mockito.anyString())).thenReturn(null);
		ResponseEntity<List<Message>> response = messageController.readMessagesSentByUser("user");
		Assert.assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void testreadMessagesReceivedForUser() {
		List<Message> messages = Mockito.mock(List.class);
		Message message = Mockito.mock(Message.class);
		Mockito.when(message.getSender()).thenReturn("venky");
		messages.add(message);
		Mockito.when(messageRepo.findByReceiver(Mockito.anyString())).thenReturn(messages);
		ResponseEntity<List<Message>> response = messageController.readReceivedMessages("user");
		Assert.assertSame(HttpStatus.OK, response.getStatusCode());
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testreadMessagesReceivedForUser_when_response_is_null_return_not_found() {
		Mockito.when(messageRepo.findByReceiver(Mockito.anyString())).thenReturn(null);
		ResponseEntity<List<Message>> response = messageController.readMessagesSentByUser("user");
		Assert.assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void testreadMessagesById() {
		Message message = Mockito.mock(Message.class);
		Mockito.when(message.getSender()).thenReturn("venky");
		Mockito.when(messageRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(message));
		ResponseEntity<Message> response = messageController.readMessage(1l);
		Assert.assertSame(HttpStatus.OK, response.getStatusCode());
		Assert.assertNotNull(response.getBody());
	}
	
	@After
	public void tearDown() throws Exception {
	messageController = null;
	}
	
}
