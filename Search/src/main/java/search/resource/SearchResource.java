package search.resource;

import io.smallrye.mutiny.Uni;
import search.model.Diagnos;
import search.model.Encounter;
import search.model.Observation;
import search.model.User;
import search.service.SearchService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.util.List;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchResource {

    @Inject
    SearchService searchService;

    // Hitta patienter baserat på namn
    @GET
    @Path("/patients/{name}")
    public Uni<List<User>> findPatientsByName(@PathParam("name") String name) {
        return searchService.findPatientsByName(name);
    }

    // Hitta diagnoser baserat på patient
    @GET
    @Path("/diagnoses/patient/{patientId}")
    public Uni<List<Diagnos>> findDiagnosesByPatient(@PathParam("patientId") int patientId) {
        User patient = new User();
        patient.setId(patientId);
        return searchService.findDiagnosesByPatient(patient);
    }

    // Hitta diagnoser baserat på status
    @GET
    @Path("/diagnoses/status/{status}")
    public Uni<List<Diagnos>> findDiagnosesByStatus(@PathParam("status") String status) {
        return searchService.findDiagnosesByStatus(status);
    }

    // Hitta diagnoser för en viss läkare
    @GET
    @Path("/diagnoses/practitioner/{practitionerId}")
    public Uni<List<Diagnos>> findDiagnosesByPractitioner(@PathParam("practitionerId") int practitionerId) {
        User practitioner = new User();
        practitioner.setId(practitionerId);
        return searchService.findDiagnosesByPractitioner(practitioner);
    }

    // Hitta encounters för en läkare under en specifik dag
    @GET
    @Path("/encounters/practitioner/{practitionerId}/date/{date}")
    public Uni<List<Encounter>> findEncountersByPractitionerAndDate(@PathParam("practitionerId") int practitionerId, @PathParam("date") String dateStr) {
        User practitioner = new User();
        practitioner.setId(practitionerId);
        LocalDateTime date = LocalDateTime.parse(dateStr); // format YYYY-MM-DDTHH:MM:SS
        return searchService.findEncountersByPractitionerAndDate(practitioner, date);
    }

    // Hitta alla encounters för en viss patient
    @GET
    @Path("/encounters/patient/{patientId}")
    public Uni<List<Encounter>> findEncountersByPatient(@PathParam("patientId") int patientId) {
        User patient = new User();
        patient.setId(patientId);
        return searchService.findEncountersByPatient(patient);
    }

    // Hitta observationer för en diagnos
    @GET
    @Path("/observations/diagnos/{diagnosId}")
    public Uni<List<Observation>> findObservationsByDiagnos(@PathParam("diagnosId") int diagnosId) {
        Diagnos diagnos = new Diagnos();
        diagnos.setId(diagnosId);
        return searchService.findObservationsByDiagnos(diagnos);
    }

    // Hitta alla läkare
    @GET
    @Path("/doctors")
    public Uni<List<User>> findDoctors() {
        return searchService.findDoctors();
    }

    // Hitta alla patienter som tillhör en läkare baserat på diagnos
    @GET
    @Path("/patients/doctor/{doctorId}")
    public Uni<List<User>> findPatientsByDoctor(@PathParam("doctorId") int doctorId) {
        User doctor = new User();
        doctor.setId(doctorId);
        return searchService.findPatientsByDoctor(doctor);
    }

    // Hitta patienter baserat på diagnos
    @GET
    @Path("/patients/diagnos/{diagnosId}")
    public Uni<List<User>> findPatientsByDiagnos(@PathParam("diagnosId") int diagnosId) {
        Diagnos diagnos = new Diagnos();
        diagnos.setId(diagnosId);
        return searchService.findPatientsByDiagnos(diagnos);
    }

    // Hitta patienter baserat på encounter
    @GET
    @Path("/patients/encounter/{encounterId}")
    public Uni<List<User>> findPatientsByEncounter(@PathParam("encounterId") int encounterId) {
        Encounter encounter = new Encounter();
        encounter.setId(encounterId);
        return searchService.findPatientsByEncounter(encounter);
    }
}
