// src/components/Login.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // För att navigera efter inloggning
import { Link } from 'react-router-dom'; // Importera Link för att navigera utan att ladda om sidan
import {backendInstance} from './AxiosConfig'; // Importera Axios för att skicka API-anrop

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
            const url = '/user/login';
            const response = await backendInstance.post(url, { email: email, password: password, role: userType });
            const token = response.data.token;

            // Hämta användarens ID med token i Authorization-headern
            const userIdResponse = await backendInstance.get(`/user/getIdByEmail/${email}`, {
                headers: {
                    Authorization: token
                }
            });

            const userId = userIdResponse.data;

            const user = {
                email: email,
                id: userId,
                role: userType,
                token: token
            };

            // Uppdatera användardatan i App.js
            setUser(user);
            localStorage.setItem('user', JSON.stringify(user));

            // Omdirigera användaren till rätt dashboard
            if (user.role === 'patient') {
                navigate('/patient/dashboard'); // Navigera till patientens dashboard
            } else {
                navigate('/practitioner/dashboard'); // Navigera till praktikerns dashboard
            }
        } catch (error) {
            setError('Invalid email, password or role');
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
                    <label>Role:</label>
                    <select value={userType} onChange={(e) => setUserType(e.target.value)}>
                        <option value="patient">Patient</option>
                        <option value="doctor">Doctor</option>
                        <option value="other">Other</option>
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
