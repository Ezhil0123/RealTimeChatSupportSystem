import React from 'react';
import './ChatHeader.css';

const ChatHeader = ({ otherUser, currentSession, onEndChat }) => {
  const isOnline = currentSession && currentSession.status === 'ACTIVE';
  
  return (
    <div className="chat-header">
      <div className="user-info">
        <div className="user-avatar">
          {otherUser.name.charAt(0).toUpperCase()}
        </div>
        <div className="user-details">
          <h3 className="user-name">{otherUser.name}</h3>
          <span className={`status ${isOnline ? 'online' : 'offline'}`}>
            {isOnline ? 'Online' : 'Offline'}
          </span>
        </div>
      </div>
      
      <div className="header-actions">
        <button 
          className="end-chat-btn"
          onClick={onEndChat}
          disabled={!currentSession || currentSession.status === 'CLOSED'}
        >
          End Chat
        </button>
      </div>
    </div>
  );
};

export default ChatHeader;