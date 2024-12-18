import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom'; // För att hämta ID från URL
import {backendInstance} from "./AxiosConfig.jsx"; // Anpassa för din axios-konfiguration

const DiagnosDetails = () => {
    const { id } = useParams();  // Hämta diagnos-id från URL-parametrar
    const [observations, setObservations] = useState([]); // State för observationer
    const [error, setError] = useState(null); // För felhantering
    const [encounterDates, setEncounterDates] = useState({}); // State för encounterDate för varje observation

    const token = JSON.parse(localStorage.getItem('user'))?.token;

    // Hämta observationer relaterade till diagnosen
    useEffect(() => {
        const fetchObservationDetails = async () => {
            try {
                const response = await backendInstance.get(`/observation/getAllByDiagnos/${id}`,{
                    headers: {
                        Authorization: token
                    }
                });
                setObservations(response.data);  // Uppdatera med listan av observationer

                // För varje observation, hämta encounter date
                const encounters = {};
                for (const observation of response.data) {
                        const encounterResponse = await backendInstance.get(`/encounter/get/${observation.encounterId}`, {
                            headers: {
                                Authorization: token
                            }
                        });
                    encounters[observation.id] = encounterResponse.data.dateTime;
                }
                setEncounterDates(encounters);
            } catch (err) {
                setError("Failed to load observation details"); // Hantera fel
            }
        };
        fetchObservationDetails();
    }, [id]);  // Kör om diagnosId förändras

    if (error) {
        return <p style={{ color: 'red' }}>{error}</p>; // Visar fel om något går fel
    }

    if (observations.length === 0) {
        return <p>No observations available</p>; // Visar "Loading" tills observationer laddas
    }

    return (
        <div>
            <h2>Diagnosis Observations</h2>

            {/* Lista över alla observationer */}
            <h3>Observations:</h3>
            {observations.length > 0 ? (
                <ul>
                    {observations.map(observation => (
                        <li key={observation.id}>
                            <p><strong>Description:</strong> {observation.description}</p>
                            <p><strong>Encounter time:</strong> {encounterDates[observation.id] ? new Date(encounterDates[observation.id]).toLocaleString() : "Loading..."}</p>
                            <hr />
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No observations available for this diagnosis.</p>
            )}
        </div>
    );
};

export default DiagnosDetails;
