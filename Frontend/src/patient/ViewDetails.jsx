import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // Importera useNavigate för navigering
import axios from "../home/AxiosConfig.jsx"; // Anpassa för din axios-konfiguration

const ViewDetails = () => {
    const [patient, setPatient] = useState(null); // State för patientdetaljer
    const [error, setError] = useState(null);     // För felhantering
    const [selectedDiagnosis, setSelectedDiagnosis] = useState(null); // För vald diagnos
    const navigate = useNavigate(); // För att navigera till en annan sida

    useEffect(() => {
        // Hämta patientens id från localStorage
        const user = JSON.parse(localStorage.getItem('user'));
        const id = user ? user.id : null;

        if (!id) {
            setError("No patient ID found in localStorage");
            return;
        }

        // Funktion för att hämta patientdetaljer baserat på id
        const fetchPatientDetails = async () => {
            try {
                const response = await axios.get(`/user/get/${id}`);
                setPatient(response.data);
            } catch (err) {
                setError("Failed to load patient details");
            }
        };
        fetchPatientDetails();
    }, []);

    const handleDiagnosisClick = (diagnosis) => {
        setSelectedDiagnosis(diagnosis.id); // Uppdaterar vald diagnos
    };

    const handleNavigate = () => {
        if (selectedDiagnosis) {
            navigate(`/diagnos-details/${selectedDiagnosis}`); // Navigera till ny sida med diagnosens id
        }
    };

    if (error) {
        return <p style={{ color: 'red' }}>{error}</p>; // Visar fel om något går fel
    }

    if (!patient) {
        return <p>Loading patient details...</p>; // Visar "Loading" tills patient laddas
    }

    return (
        <div>
            <h2>Patient Details</h2>
            <p><strong>Name:</strong> {patient.name}</p>
            <p><strong>Email:</strong> {patient.email}</p>
            <p><strong>PhoneNr:</strong> {patient.phoneNr}</p>

            {/* Lista över diagnoser */}
            <h3>Diagnoses</h3>
            {patient.diagnoses && patient.diagnoses.length > 0 ? (
                <ul>
                    {patient.diagnoses.map(diagnosis => (
                        <li
                            key={diagnosis.id}
                            onClick={() => handleDiagnosisClick(diagnosis)}
                            className={diagnosis.id === selectedDiagnosis ? 'selected' : ''}
                        >
                            {diagnosis.name}, <strong>Status:</strong> {diagnosis.diagnosStatus}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No diagnoses available.</p>
            )}

            {/* Knapp som endast går att klicka på om en diagnos är vald */}
            <button
                onClick={handleNavigate}
                disabled={!selectedDiagnosis} // Knappen är inaktiverad om ingen diagnos är vald
            >
                Go to Diagnosis Details
            </button>
        </div>
    );
};

export default ViewDetails;
