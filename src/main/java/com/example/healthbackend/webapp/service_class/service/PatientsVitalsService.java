package com.example.healthbackend.webapp.service_class.service;

import com.example.healthbackend.webapp.entity.PatientsVitals;
import com.example.healthbackend.webapp.helperclass.PatientsVitalsData;
import com.example.healthbackend.webapp.helperclass.Results;

import java.util.Date;

public interface PatientsVitalsService {

    Results savePatientsVitals(PatientsVitalsData patientsVitalsData);
    boolean isPatientVisits(String patientId, Date visitationDate);
}
