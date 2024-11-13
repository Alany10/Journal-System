import { useNavigate } from 'react-router-dom'; // För navigering till andra sidor
import React, { useState, useEffect } from 'react';

const PatientDashboard = () => {
    const navigate = useNavigate(); // För att hantera navigeringen
    const [userRole, setUserRole] = useState(null); // Lägg till en state för användarens roll

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user) {
            setUserRole(user.role); // Sätt användarrollen om användaren är inloggad
        }
    }, []); // Kör enbart vid komponentens mount

    // Funktioner för att navigera till de respektive sidorna
    const handleViewDetails = () => {
        navigate('/view-details');
    };

    return (
        <div>
            <h2>Welcome to the Patient Dashboard</h2>
            {userRole === 'patient' && (
                <div>
                    <button onClick={handleViewDetails}>View Details</button>
                </div>
            )}
        </div>
    );
};

export default PatientDashboard;
