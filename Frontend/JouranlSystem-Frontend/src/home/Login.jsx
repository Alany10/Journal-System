// src/components/Login.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // För att navigera efter inloggning
import { Link } from 'react-router-dom'; // Importera Link för att navigera utan att ladda om sidan
import axios from './AxiosConfig'; // Importera Axios för att skicka API-anrop

const Login = ({ setUser }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [userType, setUserType] = useState('patient'); // Standard är 'patient'
    const [error, setError] = useState(null);
    const navigate = useNavigate(); // För att navigera användaren efter inloggning

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const url = userType === 'patient' ? '/patient/login' : '/practitioner/login';
            const response = await axios.post(url, { email, password });

            const user = {
                id: response.data.id,
                role: userType,
            };

            // Uppdatera användardatan i App.js
                                                                          setUser(user);
                                                                          localStorage.setItem('user', JSON.stringify(user))

            // Omdirigera användaren till rätt dashboard
            if (user.role === 'patient') {
                navigate('/patient/dashboard'); // Navigera till patientens dashboard
            } else {
                navigate('/practitioner/dashboard'); // Navigera till praktikerns dashboard
            }

        } catch (error) {
            setError('Invalid email or password');
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <div>
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>User Type:</label>
                    <select value={userType} onChange={(e) => setUserType(e.target.value)}>
                        <option value="patient">Patient</option>
                        <option value="practitioner">Practitioner</option>
                    </select>
                </div>
                <button type="submit">Login</button>
            </form>

            {error && <p style={{ color: 'red' }}>{error}</p>}

            {/* Lägg till en länk till registreringssidan */}
            <div>
                <p>Don't have an account? <Link to="/register">Sign up here</Link></p>
            </div>
        </div>
    );
};

export default Login;
