package com.message.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * 
 * @author sarathvenky
 * 
 * Message Repository interface which handles database operations
 *
 */

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
	
	/**
	 * Method to retrieve list of messages sent by the sender
	 * @param sender
	 * @return List<Message>
	 */
	List<Message> findBySender(String sender);
	
	/**
	 * Method to retrieve list of messages received by the user
	 * @param receiver
	 * @return List<Message>
	 */
	List<Message> findByReceiver(String receiver);
	
}


