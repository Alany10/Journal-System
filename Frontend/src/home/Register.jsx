// src/components/Register.jsx
import React, { useState } from 'react';
import axios from './AxiosConfig'; // Importera Axios för att skicka API-anrop
import { useNavigate } from 'react-router-dom'; // För att navigera efter registrering

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState(''); // Lägg till namn
    const [phoneNr, setPhoneNr] = useState(''); // Lägg till telefonnummer
    const [role, setRole] = useState('patient'); // Standard är 'patient'
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        setError(null);

        const userData = {
            email,
            password,
            name,
            phoneNr,
            role,
        };

        try {
            // Gör ett POST-anrop till respektive API
            const url = '/user/create';
            const response = await axios.post(url, userData); // Skicka tillbaka skapade användaren //TODO
            // Efter lyckad registrering, omdirigera till login-sidan
            navigate('/');
        } catch (error) {
            setError('Error during registration');
        }
    };

    return (
        <div>
            <h2>Register</h2>
            <form onSubmit={handleRegister}>
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
                    <label>Name:</label> {/* Lägg till namnfält */}
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Phone Number:</label> {/* Lägg till telefonnummerfält */}
                    <input
                        type="text"
                        value={phoneNr}
                        onChange={(e) => setPhoneNr(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Role:</label>
                    <select value={role} onChange={(e) => setRole(e.target.value)}>
                        <option value="patient">Patient</option>
                        <option value="doctor">Doctor</option>
                        <option value="other">Other</option>
                    </select>
                </div>
                <button type="submit">Register</button>
            </form>

            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

export default Register;
