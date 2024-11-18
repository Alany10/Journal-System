// src/components/Register.jsx
import React, { useState } from 'react';
import axios from './AxiosConfig'; // Importera Axios för att skicka API-anrop
import { useNavigate } from 'react-router-dom'; // För att navigera efter registrering

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState(''); // Lägg till namn
    const [phoneNr, setPhoneNr] = useState(''); // Lägg till telefonnummer
    const [userType, setUserType] = useState('patient'); // Standard är 'patient'
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        setError(null);

        // Skapa objektet baserat på userType
        const userData = {
            email,
            password,
            name,
            phoneNr,
            role: userType === 'patient' ? undefined : userType, // Om användaren inte är patient, sätt rollen
        };

        try {
            // Beroende på om användaren är patient eller practitioner (doctor eller other), gör ett POST-anrop till respektive API
            const url = userType === 'patient' ? '/patient/create' : '/practitioner/create';
            const response = await axios.post(url, userData); // Skicka tillbaka skapade användaren

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
                    <label>User Type:</label>
                    <select value={userType} onChange={(e) => setUserType(e.target.value)}>
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
