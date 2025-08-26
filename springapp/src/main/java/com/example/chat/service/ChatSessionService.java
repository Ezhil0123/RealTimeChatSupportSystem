package com.example.chat.service;

import com.example.chat.model.ChatSession;
import com.example.chat.repository.ChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChatSessionService {
    
    @Autowired
    private ChatSessionRepository chatSessionRepository;

    public ChatSession createSession(ChatSession session) {
        return chatSessionRepository.save(session);
    }

    public ChatSession getSessionById(Long id) {
        Optional<ChatSession> sessionOpt = chatSessionRepository.findById(id);
        return sessionOpt.orElse(null);
    }

    public ChatSession closeSession(Long id) {
        Optional<ChatSession> sessionOpt = chatSessionRepository.findById(id);
        if (sessionOpt.isPresent()) {
            ChatSession session = sessionOpt.get();
            session.setEndTime(LocalDateTime.now());
            session.setStatus("CLOSED");
            return chatSessionRepository.save(session);
        }
        return null;
    }
}