import React, { useEffect, useRef } from 'react';
import './ChatWindow.css';

const ChatWindow = ({ messages, currentUserId, users }) => {
  const messagesEndRef = useRef(null);

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const formatTime = (timestamp) => {
    return new Date(timestamp).toLocaleTimeString([], { 
      hour: '2-digit', 
      minute: '2-digit' 
    });
  };

  const getSenderName = (senderId) => {
    return Object.values(users).find(user => user.id === senderId)?.name || 'Unknown';
  };

  if (messages.length === 0) {
    return (
      <div className="chat-window">
        <div className="no-messages">
          <p>No messages yet. Start the conversation!</p>
        </div>
      </div>
    );
  }

  return (
    <div className="chat-window">
      <div className="messages-container">
        {messages.map((message) => {
          const isCurrentUser = message.senderId === currentUserId;
          const senderName = getSenderName(message.senderId);
          
          return (
            <div 
              key={message.id} 
              className={`message ${isCurrentUser ? 'sent' : 'received'}`}
            >
              <div className="message-header">
                <span className="sender-name">{senderName}</span>
                <span className="message-time">
                  {formatTime(message.timestamp)}
                </span>
              </div>
              <div className="message-content">
                {message.content}
              </div>
              {message.isRead && (
                <div className="message-status">Read</div>
              )}
            </div>
          );
        })}
        <div ref={messagesEndRef} />
      </div>
    </div>
  );
};

export default ChatWindow;