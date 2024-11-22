import React, { useState, useEffect } from 'react';
import axios from "../home/AxiosConfig.jsx"; // Anpassa för din axios-konfiguration

const EstablishDiagnos = () => {
    const [status, setStatus] = useState(''); // Status för diagnos
    const [diagnosId, setDiagnosId] = useState(''); // Vald diagnos
    const [diagnoses, setDiagnoses] = useState([]); // Listan på diagnoser
    const [error, setError] = useState(null); // För att hantera fel

    useEffect(() => {
        // Hämta alla diagnoser vid komponentens laddning
        const fetchDiagnoses = async () => {
            try {
                const response = await axios.get(`/diagnos/getAllByPractitioner/${practitionerId}`);
                setDiagnoses(response.data);  // Sätter listan på diagnoser
            } catch (err) {
                setError("Failed to load diagnoses");
            }
        };
        fetchDiagnoses();
    }, []);

    const practitionerId = JSON.parse(localStorage.getItem('user'))?.id; // Hämtar användarens ID (praktikern ID)

    const handleSubmit = async (e) => {
        e.preventDefault(); // Förhindrar standardbeteendet för formuläret
        setError(null); // Återställer eventuella tidigare fel
        try {
            await axios.put(`/diagnos/establish/${diagnosId}`, status);
            alert("Diagnosis established successfully!");
        } catch (err) {
            setError("Error establishing diagnosis");
        }
    };

    return (
        <div>
            <h2>Establish Diagnoses</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Diagnos:</label>
                    <select
                        value={diagnosId}
                        onChange={(e) => setDiagnosId(e.target.value)}
                        required
                    >
                        <option value="">Select a diagnosis</option>
                        {diagnoses.map((diagnos) => (
                            <option key={diagnos.id} value={diagnos.id}>
                                {diagnos.name}
                            </option>
                        ))}
                    </select>
                </div>

                <div>
                    <label>Status:</label>
                    <select
                        value={status}
                        onChange={(e) => setStatus(e.target.value)}
                        disabled={!diagnosId}  // Inaktivera statusväljaren om ingen diagnos är vald
                    >
                        <option value="">Select a status</option>
                        <option value="confirmed">Confirmed</option>
                        <option value="ongoing">Ongoing</option>
                        <option value="off">Off</option>
                    </select>
                </div>

                <button
                    type="submit"
                    disabled={!diagnosId || !status}  // Inaktivera submit-knappen om ingen diagnos eller status är vald
                >
                    Establish Diagnosis
                </button>
            </form>

            {error && <p style={{ color: 'red' }}>{error}</p>} {/* Visar eventuella fel */}
        </div>
    );
};

export default EstablishDiagnos;
