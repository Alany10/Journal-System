import React from 'react';
import ReactDOM from 'react-dom/client'; // Importera från 'react-dom/client'
import './Index.css';
import App from './app/App.jsx';

// Skapa en root och använd .render() istället för den gamla metoden
const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);
