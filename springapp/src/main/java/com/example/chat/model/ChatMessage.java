package com.example.chat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Sender ID is required")
    @Column(name = "sender_id", nullable = false)
    private String senderId;
    
    @NotBlank(message = "Receiver ID is required")
    @Column(name = "receiver_id", nullable = false)
    private String receiverId;
    
    @NotBlank(message = "Message content cannot be empty")
    @Size(max = 500, message = "Message content cannot exceed 500 characters")
    @Column(name = "content", nullable = false, length = 500)
    private String content;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @Column(name = "is_read")
    private Boolean isRead = false;
    
    @Column(name = "session_id")
    private Long sessionId;

    public ChatMessage() {
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    public ChatMessage(String senderId, String receiverId, String content, Long sessionId) {
        this();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sessionId = sessionId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
}