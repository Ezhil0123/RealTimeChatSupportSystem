package com.example.chat.controller;

import com.example.chat.model.ChatMessage;
import com.example.chat.service.ChatMessageService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:8081")
public class ChatMessageController {
    
    @Autowired
    private ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<ChatMessage> createMessage(@Valid @RequestBody ChatMessage message) {
        try {
            ChatMessage createdMessage = chatMessageService.createMessage(message);
            return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<List<ChatMessage>> getMessagesBySessionId(@PathVariable Long sessionId) {
        try {
            List<ChatMessage> messages = chatMessageService.getMessagesBySessionId(sessionId);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{messageId}/read")
    public ResponseEntity<ChatMessage> markMessageAsRead(@PathVariable Long messageId) {
        try {
            ChatMessage updatedMessage = chatMessageService.markAsRead(messageId);
            if (updatedMessage != null) {
                return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}