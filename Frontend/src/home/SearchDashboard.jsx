import React, { useState, useEffect } from 'react';
import {backendInstance, searchInstance} from "./AxiosConfig.jsx"; // Anpassa för din axios-konfiguration

const SearchDashboard = () => {
    // States för att hålla data och inputvärden
    const [patientsByName, setPatientsByName] = useState([]);
    const [patientsByDoctor, setPatientsByDoctor] = useState([]);
    const [patientsByDiagnos, setPatientsByDiagnos] = useState([]);
    const [patientsByEncounter, setPatientsByEncounter] = useState([]);

    const [name, setName] = useState('');
    const [diagnosId, setDiagnosId] = useState('');
    const [encounterId, setEncounterId] = useState('');

    // States för diagnoser och encounters
    const [diagnosList, setDiagnosList] = useState([]);
    const [encounterList, setEncounterList] = useState([]);

    // Hämta userId och roll från localStorage
    const userId = JSON.parse(localStorage.getItem('user'))?.id;
    const userRole = JSON.parse(localStorage.getItem('user'))?.role;
    const token = JSON.parse(localStorage.getItem('user'))?.token;

    // Hämta diagnoser och encounters vid laddning
    useEffect(() => {
        const fetchData = async () => {
            try {
                const diagnosResponse = await backendInstance.get('/diagnos/getAll',{
                    headers: {
                        Authorization: token
                    }
                });
                setDiagnosList(diagnosResponse.data);

                const encounterResponse = await backendInstance.get('/encounter/getAll',{
                    headers: {
                        Authorization: token
                    }
                });
                setEncounterList(encounterResponse.data);
            } catch (error) {
                console.error('Error fetching diagnoses and encounters:', error);
            }
        };

        fetchData();
    }, []);

    // Hitta patienter baserat på namn
    const searchByName = async () => {
        try {
            const response = await searchInstance.get(`/search/patients/${name}`);
            setPatientsByName(response.data);
        } catch (error) {
            console.error('Error fetching patients by name:', error);
        }
    };

    // Hitta patienter baserat på läkare
    const searchByDoctor = async () => {
        try {
            const response = await searchInstance.get(`/search/patients/doctor/${userId}`);
            setPatientsByDoctor(response.data);
        } catch (error) {
            console.error('Error fetching patients by doctor:', error);
        }
    };

    // Hitta patienter baserat på diagnos
    const searchByDiagnos = async () => {
        try {
            const response = await searchInstance.get(`/search/patients/diagnos/${diagnosId}`);
            setPatientsByDiagnos(response.data);
        } catch (error) {
            console.error('Error fetching patients by diagnos:', error);
        }
    };

    // Hitta patienter baserat på encounter
    const searchByEncounter = async () => {
        try {
            const response = await searchInstance.get(`/search/patients/encounter/${encounterId}`);
            setPatientsByEncounter(response.data);
        } catch (error) {
            console.error('Error fetching patients by encounter:', error);
        }
    };

    return (
        <div>
            <h1>Search Dashboard</h1>

            {/* Sök efter patienter baserat på namn */}
            <div>
                <h2>Search Patients by Name</h2>
                <input
                    type="text"
                    placeholder="Enter patient name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <button onClick={searchByName}>Search by Name</button>
                <ul>
                    {patientsByName.map((patient) => (
                        <li key={patient.id}>{patient.email}</li>
                    ))}
                </ul>
            </div>

            {/* Sök efter patienter baserat på läkare */}
            {userRole === 'doctor' && (
                <div>
                    <h2>Search Patients by Doctor</h2>
                    <button onClick={searchByDoctor}>Search by Doctor</button>
                    <ul>
                        {patientsByDoctor.map((patient) => (
                            <li key={patient.id}>{patient.email}</li>
                        ))}
                    </ul>
                </div>
            )}

            {/* Sök efter patienter baserat på diagnos */}
            <div>
                <h2>Search Patients by Diagnos</h2>
                <select
                    value={diagnosId}
                    onChange={(e) => setDiagnosId(e.target.value)}
                >
                    <option value="">Select a diagnos</option>
                    {diagnosList.map((diagnos) => (
                        <option key={diagnos.id} value={diagnos.id}>
                            {diagnos.name}
                        </option>
                    ))}
                </select>
                <button onClick={searchByDiagnos}>Search by Diagnos</button>
                <ul>
                    {patientsByDiagnos.map((patient) => (
                        <li key={patient.id}>{patient.email}</li>
                    ))}
                </ul>
            </div>

            {/* Sök efter patienter baserat på encounter */}
            <div>
                <h2>Search Patients by Encounter</h2>
                <select
                    value={encounterId}
                    onChange={(e) => setEncounterId(e.target.value)}
                >
                    <option value="">Select an encounter</option>
                    {encounterList.map((encounter) => (
                        <option key={encounter.id} value={encounter.id}>
                            {encounter.dateTime}
                        </option>
                    ))}
                </select>
                <button onClick={searchByEncounter}>Search by Encounter</button>
                <ul>
                    {patientsByEncounter.map((patient) => (
                        <li key={patient.id}>{patient.email}</li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default SearchDashboard;
