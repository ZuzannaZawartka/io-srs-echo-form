import axios from 'axios';

// Create axios instance with base URL
const api = axios.create({
    baseURL: '/api', // Use relative path to leverage Vite proxy
    headers: {
        'Content-Type': 'application/json',
    },
    withCredentials: true, // Important for session cookies
});

export default api;
