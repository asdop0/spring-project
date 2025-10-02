package com.asd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asd.model.Message;
import com.asd.model.User;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiver(User user);
    List<Message> findAllBySender(User user);
}