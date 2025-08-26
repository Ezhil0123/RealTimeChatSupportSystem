import React, { useState } from 'react';
import './MessageInput.css';

const MessageInput = ({ onSendMessage, disabled }) => {
  const [message, setMessage] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (message.trim() && !disabled) {
      onSendMessage(message);
      setMessage('');
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSubmit(e);
    }
  };

  return (
    <div className="message-input-container">
      <form onSubmit={handleSubmit} className="message-form">
        <textarea
          className="message-textarea"
          placeholder={disabled ? "Chat is closed" : "Type your message..."}
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyPress={handleKeyPress}
          disabled={disabled}
          maxLength={500}
          rows={1}
        />
        <button 
          type="submit" 
          className="send-button"
          disabled={!message.trim() || disabled}
        >
          Send
        </button>
      </form>
      <div className="character-count">
        {message.length}/500
      </div>
    </div>
  );
};

export default MessageInput;