package com.maitsev.chatservice.chat.repository;

import com.maitsev.chatservice.chat.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, String> {

}
