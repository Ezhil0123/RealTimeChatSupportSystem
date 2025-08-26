import React, { useState, useEffect } from 'react';
import ChatWindow from './components/ChatWindow';
import MessageInput from './components/MessageInput';
import ChatHeader from './components/ChatHeader';
import './App.css';

const API_BASE_URL = 'https://realtimechatsupportsystem.onrender.com/api';

const mockUsers = {
  customer: { id: "cust123", name: "John Doe" },
  agent: { id: "agent456", name: "Support Agent" }
};

function App() {
  const [messages, setMessages] = useState([]);
  const [currentSession, setCurrentSession] = useState(null);
  const [currentUser, setCurrentUser] = useState(mockUsers.customer);
  const [otherUser, setOtherUser] = useState(mockUsers.agent);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    initializeChat();
  }, []);

  const initializeChat = async () => {
    setLoading(true);
    try {
      const sessionData = {
        customerId: mockUsers.customer.id,
        agentId: mockUsers.agent.id
      };

      const response = await fetch(`${API_BASE_URL}/sessions`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(sessionData)
      });

      if (response.ok) {
        const session = await response.json();
        setCurrentSession(session);
        loadMessages(session.id);
      } else {
        setError('Failed to create chat session');
      }
    } catch (err) {
      setError('Network error: Unable to connect to server');
    } finally {
      setLoading(false);
    }
  };

  const loadMessages = async (sessionId) => {
    try {
      const response = await fetch(`${API_BASE_URL}/messages/${sessionId}`);
      if (response.ok) {
        const messagesData = await response.json();
        setMessages(messagesData);
      }
    } catch (err) {
      console.error('Failed to load messages:', err);
    }
  };

  const sendMessage = async (content) => {
    if (!currentSession || !content.trim()) return;

    setLoading(true);
    try {
      const messageData = {
        senderId: currentUser.id,
        receiverId: otherUser.id,
        content: content.trim(),
        sessionId: currentSession.id
      };

      const response = await fetch(`${API_BASE_URL}/messages`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(messageData)
      });

      if (response.ok) {
        const newMessage = await response.json();
        setMessages(prevMessages => [...prevMessages, newMessage]);
        setError(null);
      } else {
        setError('Failed to send message');
      }
    } catch (err) {
      setError('Network error: Unable to send message');
    } finally {
      setLoading(false);
    }
  };

  const endChat = async () => {
    if (!currentSession) return;

    try {
      const response = await fetch(`${API_BASE_URL}/sessions/${currentSession.id}/close`, {
        method: 'PUT'
      });

      if (response.ok) {
        const closedSession = await response.json();
        setCurrentSession(closedSession);
        setError(null);
        alert('Chat session has been ended');
      } else {
        setError('Failed to end chat session');
      }
    } catch (err) {
      setError('Network error: Unable to end chat');
    }
  };

  const switchUser = () => {
    if (currentUser.id === mockUsers.customer.id) {
      setCurrentUser(mockUsers.agent);
      setOtherUser(mockUsers.customer);
    } else {
      setCurrentUser(mockUsers.customer);
      setOtherUser(mockUsers.agent);
    }
  };

  return (
    <div className="app">
      <div className="chat-container">
        <ChatHeader 
          otherUser={otherUser}
          currentSession={currentSession}
          onEndChat={endChat}
        />
        
        {error && (
          <div className="error-message">
            {error}
            <button onClick={() => setError(null)} className="close-error">×</button>
          </div>
        )}

        <ChatWindow 
          messages={messages}
          currentUserId={currentUser.id}
          users={mockUsers}
        />
        
        <MessageInput 
          onSendMessage={sendMessage}
          disabled={loading || !currentSession || currentSession.status === 'CLOSED'}
        />
        
        <div className="user-controls">
          <button onClick={switchUser} className="switch-user-btn">
            Switch to {currentUser.id === mockUsers.customer.id ? 'Agent' : 'Customer'} View
          </button>
          <span className="current-user">Current User: {currentUser.name}</span>
        </div>
      </div>
    </div>
  );
}

export default App;