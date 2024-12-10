import axios from 'axios';

// Axios-instans för Backend
export const backendInstance = axios.create({
    baseURL: 'https://localhost:8001', // Backendens bas-URL
    headers: {
        'Content-Type': 'application/json',
    },
});

// Axios-instans för Authentication
export const authInstance = axios.create({
    baseURL: 'https://localhost:8002', // Authentication bas-URL
    headers: {
        'Content-Type': 'application/json',
    },
});

// Axios-instans för Message
export const messageInstance = axios.create({
    baseURL: 'https://localhost:8003', // Message bas-URL
    headers: {
        'Content-Type': 'application/json',
    },
});

// Axios-instans för Image
export const imageInstance = axios.create({
    baseURL: 'https://localhost:8004', // Image bas-URL
    headers: {
        'Content-Type': 'application/json',
    },
});

// Axios-instans för Search
export const searchInstance = axios.create({
    baseURL: 'https://localhost:8005', // Search bas-URL
    headers: {
        'Content-Type': 'application/json',
    },
});
