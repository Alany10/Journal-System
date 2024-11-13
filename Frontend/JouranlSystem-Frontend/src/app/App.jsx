// src/App.js
import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // För att hantera routing
import Login from '../home/Login.jsx';
import PatientDashboard from '../patient/PatientDashboard.jsx'; // Patientens dashboard
import PractitionerDashboard from '../practitioner/PractitionerDashboard.jsx'; // Praktikerns dashboard
import Register from '../home/Register.jsx'; // Importera Registreringskomponenten
import CreateDiagnos from "../practitioner/CreateDiagnos.jsx";
import CreateEncounter from "../practitioner/CreateEncounter.jsx";
import CreateObservation from "../practitioner/CreateObservation.jsx";
import EstablishDiagnos from "../practitioner/EstablishDiagnos.jsx";
import ViewPatients from "../practitioner/ViewPatients.jsx";
import PatientDetails from "../practitioner/PatientDetails.jsx";
import DiagnosDetails from "../home/DiagnosDetails.jsx";
import ViewDetails from "../patient/ViewDetails.jsx";

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
                        element={user && (user.role === 'doctor' ||  user.role === 'other') ? <PractitionerDashboard /> : <Login setUser={setUser} />}
                    />
                    <Route path="/register" element={<Register />} />

                    <Route path="/create-diagnos" element={<CreateDiagnos />} />
                    <Route path="/create-encounter" element={<CreateEncounter />} />
                    <Route path="/create-observation" element={<CreateObservation />} />
                    <Route path="/establish-diagnos" element={<EstablishDiagnos />} />
                    <Route path="/view-patients" element={<ViewPatients />} />
                    <Route path="/patient-details/:id" element={<PatientDetails />} />
                    <Route path="/diagnos-details/:id" element={<DiagnosDetails />} />
                    <Route path="/view-details/" element={<ViewDetails />} />


                </Routes>
            </div>
        </Router>
    );
}

export default App;
