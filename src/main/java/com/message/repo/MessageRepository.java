package com.message.repo;

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
	

}
