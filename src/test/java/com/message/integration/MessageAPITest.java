package com.message.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.message.repo.Message;
import com.message.repo.MessageRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MessageAPITest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	MessageRepository messageRepo;
	
	@Before
	public void setUp() {
		List<String> jsonList = new ArrayList<String>();
		String json="{\r\n" + 
				"  \"body\": \"solluga\",\r\n" + 
				"  \"from\": \"Vishnu\",\r\n" + 
				"  \"subject\": \"hello\",\r\n" + 
				"  \"to\": \"Venky\"\r\n" + 
				"}";
		String json1="{\r\n" + 
				"  \"body\": \"brother\",\r\n" + 
				"  \"from\": \"Vishnu\",\r\n" + 
				"  \"subject\": \"hi\",\r\n" + 
				"  \"to\": \"Venky\"\r\n" + 
				"}";
		String json2="{\r\n" + 
				"  \"body\": \"Ena panra\",\r\n" + 
				"  \"from\": \"Nakshatra\",\r\n" + 
				"  \"subject\": \"uncle\",\r\n" + 
				"  \"to\": \"Venky\"\r\n" + 
				"}";
		jsonList.add(json);
		jsonList.add(json1);
		jsonList.add(json2);
		try {
			for(String jsonContent:jsonList) {
				this.mockMvc.perform(post("http://localhost:8080/message/send")
						.content(jsonContent).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
						.andExpect(status().isOk()).andExpect(content().string("Message sent successfully"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	@Test
	public void sendMessage() throws Exception {
		String json="{\r\n" + 
				"  \"body\": \"solluga\",\r\n" + 
				"  \"from\": \"Vishnu\",\r\n" + 
				"  \"subject\": \"hello\",\r\n" + 
				"  \"to\": \"Venky\"\r\n" + 
				"}";
		this.mockMvc.perform(post("http://localhost:8080/message/send")
				.content(json).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andExpect(content().string("Message sent successfully"));
	}
	
	@Test
	public void readMessagesSentByUser() throws Exception {
		Message message = new Message();
		message.setSender("Vishnu");
		message.setReceiver("venky");
		List<Message> messages = new ArrayList<>();
		messages.add(message);
		BDDMockito.given(messageRepo.findBySender(Mockito.anyString())).willReturn(messages );
		this.mockMvc.perform(get("http://localhost:8080/message/read/sent/Nakshatra").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
	}
	
	@Test
	public void readReceivedMessages() throws Exception {
		Message message = new Message();
		message.setSender("Nakshatra");
		message.setReceiver("venky");
		List<Message> messages = new ArrayList<>();
		messages.add(message);
		BDDMockito.given(messageRepo.findByReceiver(Mockito.anyString())).willReturn(messages );
		this.mockMvc.perform(get("http://localhost:8080/message/read/received/Venky").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
	}
	
	
}
