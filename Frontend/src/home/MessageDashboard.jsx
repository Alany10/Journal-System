import React, { useState, useEffect } from 'react';
import axios from '../home/AxiosConfig'; // Anpassa för din axios-konfiguration

const MessageDashboard = () => {
    const [messages, setMessages] = useState([]);
    const [selectedMessage, setSelectedMessage] = useState(null); // För att hålla reda på det valda meddelandet
    const [error, setError] = useState(null);
    const [showSendMessageForm, setShowSendMessageForm] = useState(false);
    const [recipients, setRecipients] = useState([]);
    const [newMessage, setNewMessage] = useState({ title: '', text: '', recipientId: '' });

    // Hämta userId och roll från localStorage
    const userId = JSON.parse(localStorage.getItem('user'))?.id;
    const userRole = JSON.parse(localStorage.getItem('user'))?.role;

    // Hämta alla meddelanden vid sidladdning
    useEffect(() => {
        handleViewAll(); // Hämta alla meddelanden vid första renderingen

        // Beroende på användarroll, hämta alla mottagare
        if (userRole === 'patient') {
            fetchPractitioners(); // Om användaren är patient, hämta alla praktiker
        } else {
            fetchPatients(); // Om användaren är praktiker, hämta alla patienter
        }
    }, [userRole, userId]);

    // Hämta alla praktiker om användaren är en patient
    const fetchPractitioners = async () => {
        try {
            const response = await axios.get('/user/getAllPractitioners');
            setRecipients(response.data);
        } catch (error) {
            setError("Failed to fetch practitioners.");
        }
    };

    // Hämta alla patienter om användaren är en praktiker
    const fetchPatients = async () => {
        try {
            const response = await axios.get('/user/getAllPatients');
            setRecipients(response.data);
        } catch (error) {
            setError("Failed to fetch patients.");
        }
    };

    // Hämta användarnamn baserat på roll och id
    const getUserName = async (userId) => {
        const endpoint =  `/user/get/${userId}`;
        const response = await axios.get(endpoint);
        return response.data.name;
    };

    // Funktion för att hämta alla meddelanden
    const handleViewAll = async () => {
        try {
            setShowSendMessageForm(false); // Döljer formuläret
            const endpoint = `/message/getAllReceived/${userId}`
            const response = await axios.get(endpoint);

            const messagesWithNames = await Promise.all(response.data.map(async (message) => {
                let senderName = '';
                let receiverName = '';

                if (message.sender === 'PATIENT') {
                    senderName = await getUserName(message.patientId);
                    receiverName = await getUserName(message.practitionerId);
                } else {
                    senderName = await getUserName(message.practitionerId);
                    receiverName = await getUserName(message.patientId);
                }

                return { ...message, senderName, receiverName };
            }));

            setMessages(messagesWithNames);
            setError(null);
        } catch {
            setError("Failed to fetch all messages.");
        }
    };

    // Funktion för att visa alla olästa meddelanden
    const handleViewUnread = async () => {
        try {
            setShowSendMessageForm(false);
            const endpoint = `/message/getAllUnread/${userId}`;
            const response = await axios.get(endpoint);

            const messagesWithNames = await Promise.all(response.data.map(async (message) => {
                let senderName = '';
                let receiverName = '';

                if (message.sender === 'PATIENT') {
                    senderName = await getUserName(message.patientId);
                    receiverName = await getUserName(message.practitionerId);
                } else {
                    senderName = await getUserName(message.practitionerId);
                    receiverName = await getUserName(message.patientId);
                }

                return { ...message, senderName, receiverName };
            }));

            setMessages(messagesWithNames);
            setError(null);
        } catch {
            setError("Failed to fetch unread messages.");
        }
    };

    // Funktion för att visa alla skickade meddelanden
    const handleViewSent = async () => {
        try {
            setShowSendMessageForm(false);
            const endpoint = `/message/getAllSent/${userId}`;
            const response = await axios.get(endpoint);

            const messagesWithNames = await Promise.all(response.data.map(async (message) => {
                let senderName = '';
                let receiverName = '';

                if (message.sender === 'PATIENT') {
                    senderName = await getUserName(message.patientId);
                    receiverName = await getUserName(message.practitionerId);
                } else {
                    senderName = await getUserName(message.practitionerId);
                    receiverName = await getUserName(message.patientId);
                }

                return { ...message, senderName, receiverName };
            }));

            setMessages(messagesWithNames);
            setError(null);
        } catch {
            setError("Failed to fetch sent messages.");
        }
    };


    // Hantera när ett meddelande är valt för att visa hela innehållet
    const handleViewMessageDetails = async (message) => {
        setSelectedMessage(message);

        // Kontrollera om användaren är samma som avsändaren
        if (
            (userRole.toUpperCase() === message.sender)
        ) return;

        // Skicka API-anrop för att markera meddelandet som läst
        try {
            await axios.put(`/message/read/${message.id}`);
        } catch (error) {
            setError("Failed to mark the message as read.");
        }
    };

    // Hantera skicka meddelande
    const handleSendMessage = () => {
        setShowSendMessageForm(true);
    };

    // Hantera skicka meddelande formulär
    const handleSendMessageSubmit = async (e) => {
        e.preventDefault();


        const messageDTO = {
            title: newMessage.title,
            text: newMessage.text,
            sender: userRole,
            patientId: userRole === 'patient' ? userId : newMessage.recipientId,
            practitionerId: (userRole === 'doctor' || userRole === 'other') ? userId : newMessage.recipientId,
        };

        try {
            await axios.post('/message/create', messageDTO);
            setShowSendMessageForm(false);
            setNewMessage({ title: '', text: '', recipientId: '' });
            handleViewAll(); // Uppdatera meddelandelistan
            setError(null);
            alert("Message sent successfully!")
        } catch {
            setError("Failed to send message.");
        }
    };

    return (
        <div>
            <h2>Message Dashboard</h2>
            <button onClick={handleSendMessage}>Send Message</button>
            <button onClick={handleViewAll}>View All Received</button>
            <button onClick={handleViewUnread}>View All Unread</button>
            <button onClick={handleViewSent}>View All Sent</button>

            {error && <p style={{ color: 'red' }}>{error}</p>}

            {/* Visa meddelandelistan endast om formuläret inte visas */}
            {!showSendMessageForm && (
                <div>
                    <h3>Messages:</h3>
                    {messages.length > 0 ? (
                        <ul>
                            {messages.map((message) => (
                                <li key={message.id}>
                                    <strong onClick={() => handleViewMessageDetails(message)} style={{ cursor: 'pointer' }}>
                                        {message.title}
                                    </strong>
                                    {/* Visa meddelandeinfo om det är valt */}
                                    {selectedMessage?.id === message.id && (
                                        <div>
                                            <p>{message.text}</p>
                                            <p><em>Sender: {message.senderName}</em></p>
                                            <p><em>Receiver: {message.receiverName}</em></p>
                                            <p><em>{new Date(message.dateTime).toLocaleString()}</em></p>
                                        </div>
                                    )}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No messages to display.</p>
                    )}
                </div>
            )}

            {/* Skicka-meddelande-formuläret visas här */}
            {showSendMessageForm && (
                <div>
                    <h3>Send New Message</h3>
                    <form onSubmit={handleSendMessageSubmit}>
                        <div>
                            <label>Title:</label>
                            <input
                                type="text"
                                value={newMessage.title}
                                onChange={(e) => setNewMessage({ ...newMessage, title: e.target.value })}
                                required
                            />
                        </div>
                        <div>
                            <label>Text:</label>
                            <textarea
                                value={newMessage.text}
                                onChange={(e) => setNewMessage({ ...newMessage, text: e.target.value })}
                                required
                            ></textarea>
                        </div>
                        <div>
                            <label>Recipient:</label>
                            <select
                                value={newMessage.recipientId}
                                onChange={(e) => setNewMessage({ ...newMessage, recipientId: e.target.value })}
                                required
                            >
                                <option value="">Select a recipient</option>
                                {recipients.map((recipient) => (
                                    <option key={recipient.id} value={recipient.id}>
                                        {recipient.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <button type="submit">Send</button>
                        <button type="button" onClick={() => setShowSendMessageForm(false)}>Cancel</button>
                    </form>
                </div>
            )}
        </div>
    );
};

export default MessageDashboard;
