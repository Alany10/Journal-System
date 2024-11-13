import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // För att navigera till PatientDetails
import axios from "../home/AxiosConfig.jsx"; // Anpassa för din axios-konfiguration

const ViewPatients = () => {
    const [patients, setPatients] = useState([]);      // Lista över alla patienter
    const [selectedPatientId, setSelectedPatientId] = useState(''); // Id för vald patient
    const [error, setError] = useState(null);          // För att hantera fel
    const navigate = useNavigate();                    // Hook för navigering

    useEffect(() => {
        // Hämta alla patienter när komponenten laddas
        const fetchPatients = async () => {
            try {
                const response = await axios.get('/patient/getAll');
                setPatients(response.data);
            } catch (err) {
                setError("Failed to load patients");
            }
        };
        fetchPatients();
    }, []);

    // Funktion som hanterar knappen "View"
    const handleViewPatient = () => {
        if (selectedPatientId) {
            navigate(`/patient-details/${selectedPatientId}`); // Navigera till PatientDetails med id:t
        }
    };

    return (
        <div>
            <h2>Select a Patient</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>} {/* Visar eventuella fel */}

            <div>
                <label>Select a Patient:</label>
                <select
                    value={selectedPatientId}
                    onChange={(e) => setSelectedPatientId(e.target.value)}
                >
                    <option value="">-- Select Patient --</option>
                    {patients.map((patient) => (
                        <option key={patient.id} value={patient.id}>
                            {patient.name} (Email: {patient.email})
                        </option>
                    ))}
                </select>
            </div>

            <button
                onClick={handleViewPatient}
                disabled={!selectedPatientId} // Inaktivera knappen om ingen patient är vald
            >
                View Patient
            </button>
        </div>
    );
};

export default ViewPatients;
