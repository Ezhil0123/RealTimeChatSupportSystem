import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import App from './App';

global.fetch = jest.fn();

describe('Chat Support App', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  test('renders chat interface', () => {
    render(<App />);
    expect(screen.getByText(/Support Agent/i)).toBeInTheDocument();
    expect(screen.getByPlaceholderText(/Type your message/i)).toBeInTheDocument();
  });

  test('creates session on mount', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({
        id: 1,
        customerId: 'cust123',
        agentId: 'agent456',
        status: 'ACTIVE'
      })
    });

    render(<App />);
    
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/sessions',
        expect.objectContaining({
          method: 'POST',
          headers: { 'Content-Type': 'application/json' }
        })
      );
    });
  });
});