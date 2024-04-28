package com.maitsev.chatservice.chat.repository;

import com.maitsev.chatservice.chat.model.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, String> {

}
