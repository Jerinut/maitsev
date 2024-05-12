package com.maitsev.chatservice.chat.repository;

import com.maitsev.chatservice.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, String> {

    @Query("SELECT c FROM Chat c WHERE c.user1Id = :userId OR c.user2Id = :userId")
    List<Chat> findByUser1IdOrUser2Id(@Param("userId") String userId);
}