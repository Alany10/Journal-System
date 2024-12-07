// src/AxiosConfig.jsx
import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:30001', // Backendens bas-URL
    headers: {
        'Content-Type': 'application/json',
    },
});

export default axiosInstance;

