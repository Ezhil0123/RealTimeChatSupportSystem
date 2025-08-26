package com.example.chat.controller;

import com.example.chat.model.ChatSession;
import com.example.chat.service.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = {"http://localhost:8081", "https://real-time-chat-support-system.vercel.app"})
public class ChatSessionController {
    
    @Autowired
    private ChatSessionService chatSessionService;

    @PostMapping
    public ResponseEntity<ChatSession> createSession(@Valid @RequestBody ChatSession session) {
        try {
            ChatSession createdSession = chatSessionService.createSession(session);
            return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatSession> getSessionById(@PathVariable Long id) {
        try {
            ChatSession session = chatSessionService.getSessionById(id);
            if (session != null) {
                return new ResponseEntity<>(session, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<ChatSession> closeSession(@PathVariable Long id) {
        try {
            ChatSession closedSession = chatSessionService.closeSession(id);
            if (closedSession != null) {
                return new ResponseEntity<>(closedSession, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}