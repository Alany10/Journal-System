import { useNavigate } from 'react-router-dom'; // För navigering till andra sidor
import React, { useState, useEffect } from 'react';

const PractitionerDashboard = () => {
    const navigate = useNavigate(); // För att hantera navigeringen
    const [userRole, setUserRole] = useState(null); // State för att lagra användarens roll

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user) {
            setUserRole(user.role);
        }
    }, []); // Lägg till ett tomt beroende för att köra detta bara vid mount

    // Funktioner för att navigera till de respektive sidorna
    const handleCreateDiagnos = () => {
        navigate('/create-diagnos');
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

    const handleViewPatients = () => {
        navigate('/view-patients');
    };

    return (
        <div>
            <h2>Welcome to the Practitioner Dashboard</h2>
            <div>
                <button onClick={handleCreateDiagnos}>Create Diagnosis</button>
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

            {/* Visa "View Patients" om användaren är DOCTOR */}
            {userRole === 'doctor' && (
                <div>
                    <button onClick={handleViewPatients}>View Patients</button>
                </div>
            )}
            <div>
                <button onClick={handleMessage}>Message Dashboard</button>
            </div>

        </div>
    );
};

export default PractitionerDashboard;
