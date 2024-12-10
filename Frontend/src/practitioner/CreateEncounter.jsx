// src/components/CreateEncounter.jsx
import React, { useState, useEffect } from 'react';
import {backendInstance} from "../home/AxiosConfig.jsx"; // Anpassa för din axios-konfiguration

const CreateEncounter = () => {
    const [dateTime, setDateTime] = useState('');
    const [patientId, setPatientId] = useState('');
    const [patients, setPatients] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Hämta alla patienter vid komponentens laddning
        const fetchPatients = async () => {
            try {
                const response = await backendInstance.get('/user/getAllPatients');
                setPatients(response.data);
            } catch (err) {
                setError("Failed to load patients");
            }
        };
        fetchPatients();
    }, []);
    const practitionerId = JSON.parse(localStorage.getItem('user'))?.id; // Hämtar användarens ID (praktikern ID)

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        const encounterData = {
            dateTime,
            patientId: parseInt(patientId),
            practitionerId: practitionerId,
        };

        try {
            await backendInstance.post('/encounter/create', encounterData);
            alert("Encounter created successfully!");
        } catch (err) {
            setError("Error creating encounter");
        }
    };

    return (
        <div>
            <h2>Create a New Encounter</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Date and Time:</label>
                    <input
                        type="datetime-local"
                        value={dateTime}
                        onChange={(e) => setDateTime(e.target.value)}
                        required
                    />
                </div>

                <div>
                    <label>Patient:</label>
                    <select
                        value={patientId}
                        onChange={(e) => setPatientId(e.target.value)}
                        required
                    >
                        <option value="">Select a patient</option>
                        {patients.map((patient) => (
                            <option key={patient.id} value={patient.id}>
                                {patient.email}
                            </option>
                        ))}
                    </select>
                </div>

                <button type="submit">Create Encounter</button>
            </form>

            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

export default CreateEncounter;
