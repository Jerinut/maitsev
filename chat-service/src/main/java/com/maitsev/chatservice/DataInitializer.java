
package com.maitsev.chatservice;

import com.maitsev.chatservice.chat.model.Message;
import com.maitsev.chatservice.chat.repository.ChatRepository;
import com.maitsev.chatservice.chat.model.Chat;
import com.maitsev.chatservice.chat.repository.MessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initializeChatAndMessageData(ChatRepository chatRepository, MessageRepository messageRepository) {
        return args -> {
            Chat chat1 = new Chat();
            chat1.setId("01");
            chat1.setUser1Id("01");
            chat1.setUser2Id("02");
            chat1.setCreatedAt(LocalDate.now());

            Message message1 = new Message();
            message1.setContent("Hello, how are you?");
            message1.setSender("01");
            message1.setReceiver("02");
            message1.setCreatedAt(LocalDate.now());
            message1.setChat(chat1);

            // Initialize the list of messages in the Chat entity
            chat1.setMessages(new ArrayList<>());
            // Add the message to the chat's list of messages
            chat1.getMessages().add(message1);

            // Save the Chat entity, which will cascade the save operation to the associated messages
            chatRepository.save(chat1);
        };
    }
}
