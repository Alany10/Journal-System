import React from 'react';
import { useNavigate } from 'react-router-dom'; // För navigering till andra sidor

const PractitionerDashboard = () => {
    const navigate = useNavigate(); // För att hantera navigeringen

    // Funktioner för att navigera till de respektive sidorna
    const handleCreateDiagnosis = () => {
        navigate('/create-diagnosis'); // Omdirigerar till en sida för att skapa diagnos
    };

    const handleCreateEncounter = () => {
        navigate('/create-encounter');
    };

    const handleCreateObservation = () => {
        navigate('/create-observation');
    };

    const handleEstablishDiagnos = () => {
        navigate('/establish-diagnos');
    };


    return (
        <div>
            <h2>Welcome to the Practitioner Dashboard</h2>
            <div>
                <button onClick={handleCreateDiagnosis}>Create Diagnosis</button>
            </div>
            <div>
                <button onClick={handleCreateEncounter}>Create Encounter</button>
            </div>
            <div>
                <button onClick={handleCreateObservation}>Create Observation</button>
            </div>
            <div>
                <button onClick={handleEstablishDiagnos}>Establish Diagnosis</button>
            </div>
        </div>
    );
};

export default PractitionerDashboard;
