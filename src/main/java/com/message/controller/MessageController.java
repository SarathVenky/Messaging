package com.message.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.message.model.MessageRequest;
import com.message.repo.Message;
import com.message.repo.MessageRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
 * 
 * @author sarathvenky
 *
 * This Message controller has different end points which serves to send and read messages
 *
 *
 */
@RestController
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	MessageRepository messageRepo;
	
	/**
	 * 
	 * @param messageRequest
	 * @return ResponseEntity
	 */
	@PostMapping("/send")
	@ApiOperation(
	        value = "Send Message",
	        notes = "This operation is to send message to the user."
	)
	@ApiResponses({
	        @ApiResponse(code = 200, message = "Message sent successfully"),
	        @ApiResponse(code = 400, message = "Bad Request"),
	        @ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<String> sendMessage(@RequestBody MessageRequest messageRequest) {
		if(null!=messageRequest) {
		  Message messageEntity = new Message();
		  messageEntity.setBody(messageRequest.getBody());
		  messageEntity.setReceiver(messageRequest.getTo());
		  messageEntity.setSender(messageRequest.getFrom());
		  messageEntity.setSubject(messageRequest.getSubject()); 
		  java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		  messageEntity.setSentDate(date);
		  messageRepo.save(messageEntity);
		  return new ResponseEntity<>("Message sent successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Error in validating Request",HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	/**
	 * 
	 * @param user
	 * @return ResponseEntity<List<Message>>
	 */
	@ApiOperation(
	        value = "Read Messages",
	        notes = "This operation is to read messages sent by the user."
	)
	@ApiResponses({
        @ApiResponse(code = 200, message = "Read Message"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found")})
	@GetMapping("/read/sent/{user}")
	public ResponseEntity<List<Message>> readMessagesSentByUser(@PathVariable @NotNull String user) {
		Message message = new Message();
		message.setSender(user);
		return this.getMessagesResponse(message);
	}
	
	/**
	 * 
	 * @param user
	 * @return ResponseEntity<List<Message>>
	 */
	@ApiOperation(
	        value = "Read Messages",
	        notes = "This operation is to read messages received."
	)
	@ApiResponses({
        @ApiResponse(code = 200, message = "Read Message"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found")})
	@GetMapping("/read/received/{user}")
	public ResponseEntity<List<Message>> readReceivedMessages(@PathVariable @NotNull String user) {
		Message message = new Message();
		message.setReceiver(user);
		return this.getMessagesResponse(message);
	}
	
	
	/**
	 * This method is used to call respository to get the list of messages and returns in the response entity
	 * 
	 * @param message
	 * @return ResponseEntity
	 */
	private ResponseEntity<List<Message>> getMessagesResponse(Message message) {
		Example<Message> example = Example.of(message);
		List<Message> messages = messageRepo.findAll(example);
		if(!CollectionUtils.isEmpty(messages)) {
			return new ResponseEntity<List<Message>>(messages,HttpStatus.OK);	
		}
		else {
			return new ResponseEntity<List<Message>>(Collections.emptyList(),HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return ResponseEntity<Message>
	 */
	@ApiOperation(
	        value = "Read Single Message",
	        notes = "This operation is to read a single message sent by the user which taking id of the message."
	)
	@ApiResponses({
        @ApiResponse(code = 200, message = "Read Messages"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found")})
	@GetMapping("/read/{id}")
	public ResponseEntity<Message> readMessage(@PathVariable Long id) {
		Optional<Message> message = messageRepo.findById(id);
		return new ResponseEntity<Message>(message.get(),HttpStatus.OK);
		
	}
	

}
