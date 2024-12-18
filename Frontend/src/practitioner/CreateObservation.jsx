// src/components/CreateObservation.jsx
import React, { useState, useEffect } from 'react';
import {backendInstance} from "../home/AxiosConfig.jsx"; // Anpassa för din axios-konfiguration

const CreateObservation = () => {
    const [description, setDescription] = useState('');
    const [patientId, setPatientId] = useState('');
    const [patients, setPatients] = useState([]);
    const [encounters, setEncounters] = useState([]);
    const [diagnoses, setDiagnoses] = useState([]);
    const [selectedEncounter, setSelectedEncounter] = useState('');
    const [selectedDiagnosis, setSelectedDiagnosis] = useState('');
    const [error, setError] = useState(null);

    const token = JSON.parse(localStorage.getItem('user'))?.token;

    // Hämta alla patienter vid komponentens laddning
    useEffect(() => {
        const fetchInformations = async () => {
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
        fetchInformations();
    }, []);

    // Hämta encounters och diagnoser när en patient är vald
    useEffect(() => {
        if (patientId) {
            const fetchEncounterAndDiagnoses = async () => {
                try {
                    const encounterResponse = await backendInstance.get(`/encounter/getAllByPatient/${patientId}`,{
                        headers: {
                            Authorization: token
                        }
                    });
                    const diagnosesResponse = await backendInstance.get(`/diagnos/getAllByPatient/${patientId}`,{
                        headers: {
                            Authorization: token
                        }
                    });
                    setEncounters(encounterResponse.data);
                    setDiagnoses(diagnosesResponse.data);
                } catch (err) {
                    setError("Failed to load encounters or diagnoses");
                }
            };
            fetchEncounterAndDiagnoses();
        }
    }, [patientId]);

    const practitionerId = JSON.parse(localStorage.getItem('user'))?.id; // Hämtar användarens ID (praktikern ID)

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        const observationData = {
            description,
            patientId: parseInt(patientId),
            practitionerId: practitionerId,
            encounterId: selectedEncounter,
            diagnosId: selectedDiagnosis
        };

        try {
            await backendInstance.post('/observation/create', observationData,{
                headers: {
                    Authorization: token
                }
            });
            alert("Observation created successfully!");
        } catch (err) {
            setError("Error creating observation");
        }
    };

    return (
        <div>
            <h2>Create new Observation</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Description:</label>
                    <input
                        type="text"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
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

                {patientId && (
                    <div>
                        <label>Select Encounter:</label>
                        <select
                            value={selectedEncounter}
                            onChange={(e) => setSelectedEncounter(e.target.value)}
                            required
                        >
                            <option value="">Select an Encounter</option>
                            {encounters.map((encounter) => (
                                <option key={encounter.id} value={encounter.id}>
                                     (Date: {encounter.dateTime})
                                </option>
                            ))}
                        </select>
                    </div>
                )}

                {patientId && (
                    <div>
                        <label>Select Diagnosis:</label>
                        <select
                            value={selectedDiagnosis}
                            onChange={(e) => setSelectedDiagnosis(e.target.value)}
                            required
                        >
                            <option value="">Select a Diagnosis</option>
                            {diagnoses.map((diagnosis) => (
                                <option key={diagnosis.id} value={diagnosis.id}>
                                    {diagnosis.name}
                                </option>
                            ))}
                        </select>
                    </div>
                )}

                <button type="submit">Create Observation</button>
            </form>

            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

export default CreateObservation;
