// src/components/CreateDiagnos.jsx
import React, { useState, useEffect } from 'react';
import {backendInstance} from "../home/AxiosConfig.jsx"; // Anpassa för din axios-konfiguration

const CreateDiagnos = () => {
    const [name, setName] = useState('');
    const [patientId, setPatientId] = useState('');
    const [patients, setPatients] = useState([]);
    const [error, setError] = useState(null);

    const token = JSON.parse(localStorage.getItem('user'))?.token;

    useEffect(() => {
        // Hämta alla patienter vid komponentens laddning
        const fetchPatients = async () => {
            try {
                const response = await backendInstance.get('/user/getAllPatients',{
                    headers: {
                        Authorization: token
                    }
                });
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

        const diagnosData = {
            name: name,
            patientId: patientId,
            practitionerId: practitionerId,
        };

        try {
            await backendInstance.post('/diagnos/create', diagnosData,{
                headers: {
                    Authorization: token
                }
            });
            alert("Diagnos created successfully!");
        } catch (err) {
            setError("Error creating diagnos");
        }
    };

    return (
        <div>
            <h2>Create a New Diagnos</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name of diagnos:</label>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
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

                <button type="submit">Create Diagnos</button>
            </form>

            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

export default CreateDiagnos;
