// src/App.js
import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // För att hantera routing
import Login from '../home/Login.jsx';
import PatientDashboard from '../patient/PatientDashboard.jsx'; // Patientens dashboard
import PractitionerDashboard from '../practitioner/PractitionerDashboars.jsx'; // Praktikerns dashboard
import Register from '../home/Register.jsx'; // Importera Registreringskomponenten
import CreateDiagnos from "../practitioner/CreateDiagnos.jsx";
import CreateEncounter from "../practitioner/CreateEncounter.jsx";
import CreateObservation from "../practitioner/CreateObservation.jsx";
import EstablishDiagnos from "../practitioner/EstablishDiagnos.jsx";

function App() {
    const [user, setUser] = useState(null); // Skapa en state för användaren (null betyder inte inloggad)

    return (

        <Router>
            <div className="App">
                <Routes>
                    {/* Om användaren inte är inloggad, visa login-komponenten */}
                    <Route path="/" element={<Login setUser={setUser} />} />

                    {/* Om användaren är inloggad som patient, visa patientens dashboard */}
                    <Route
                        path="/patient/dashboard"
                        element={user && user.role === 'patient' ? <PatientDashboard /> : <Login setUser={setUser} />}
                    />

                    {/* Om användaren är inloggad som praktiker, visa praktikerns dashboard */}
                    <Route
                        path="/practitioner/dashboard"
                        element={user && user.role === 'practitioner' ? <PractitionerDashboard /> : <Login setUser={setUser} />}
                    />

                    {/* Registreringssidan */}
                    <Route path="/register" element={<Register />} />
                    <Route path="/create-diagnos" element={<CreateDiagnos />} />
                    <Route path="/create-encounter" element={<CreateEncounter />} />
                    <Route path="/create-observation" element={<CreateObservation />} />
                    <Route path="/establish-diagnos" element={<EstablishDiagnos />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
