package com.example.chat;

import com.example.chat.model.ChatMessage;
import com.example.chat.model.ChatSession;
import com.example.chat.service.ChatMessageService;
import com.example.chat.service.ChatSessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional
class ChatSupportApplicationTests {

    @Autowired
    private ChatSessionService chatSessionService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Test
    void contextLoads() {
        assertThat(chatSessionService).isNotNull();
        assertThat(chatMessageService).isNotNull();
    }

    @Test
    void testCreateChatSession() {
        ChatSession session = new ChatSession("cust123", "agent456");
        ChatSession savedSession = chatSessionService.createSession(session);
        
        assertThat(savedSession).isNotNull();
        assertThat(savedSession.getId()).isNotNull();
        assertThat(savedSession.getCustomerId()).isEqualTo("cust123");
        assertThat(savedSession.getAgentId()).isEqualTo("agent456");
        assertThat(savedSession.getStatus()).isEqualTo("ACTIVE");
        assertThat(savedSession.getStartTime()).isNotNull();
    }

    @Test
    void testCreateChatMessage() {
        ChatSession session = new ChatSession("cust123", "agent456");
        ChatSession savedSession = chatSessionService.createSession(session);
        
        ChatMessage message = new ChatMessage("cust123", "agent456", "Hello, I need help!", savedSession.getId());
        ChatMessage savedMessage = chatMessageService.createMessage(message);
        
        assertThat(savedMessage).isNotNull();
        assertThat(savedMessage.getId()).isNotNull();
        assertThat(savedMessage.getContent()).isEqualTo("Hello, I need help!");
        assertThat(savedMessage.getSenderId()).isEqualTo("cust123");
        assertThat(savedMessage.getReceiverId()).isEqualTo("agent456");
        assertThat(savedMessage.getIsRead()).isFalse();
        assertThat(savedMessage.getTimestamp()).isNotNull();
    }
}