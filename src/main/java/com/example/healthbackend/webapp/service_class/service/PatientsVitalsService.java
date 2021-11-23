package com.example.healthbackend.webapp.service_class.service;

import com.example.healthbackend.webapp.entity.PatientsVitals;
import com.example.healthbackend.webapp.helperclass.PatientListingData;
import com.example.healthbackend.webapp.helperclass.PatientsVitalsData;
import com.example.healthbackend.webapp.helperclass.Results;

import java.util.Date;
import java.util.List;

public interface PatientsVitalsService {

    Results savePatientsVitals(PatientsVitalsData patientsVitalsData);
    boolean isPatientVisits(String patientId, Date visitationDate);
    List<PatientListingData> getPatientsVitalsList(Date visitationDate);
}
