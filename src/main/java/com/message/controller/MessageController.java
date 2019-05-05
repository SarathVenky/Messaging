package com.message.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@ApiOperation(
	        value = "Read Messages",
	        notes = "This operation is to read messages sent by the user."
	)
	@ApiResponses({
        @ApiResponse(code = 200, message = "Read Message"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found")})
	@GetMapping("/receive/{user}")
	public List<Message> readMessage(@PathVariable String user) {
		Message message = new Message();
		message.setReceiver(user);
		Example<Message> example = Example.of(message);
		return messageRepo.findAll(example);
		
	}
	
	@ApiOperation(
	        value = "Read Single Message",
	        notes = "This operation is to read a single message sent by the user which taking sender as input."
	)
	@ApiResponses({
        @ApiResponse(code = 200, message = "Read Messages"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found")})
	@GetMapping("/readSent")
	public List<MessageRequest> readSentMessages(@PathVariable String user) {
		
		return null;
		
	}
	

}
