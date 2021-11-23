package com.example.healthbackend.webapp.service_class.service;

import com.example.healthbackend.webapp.entity.PatientRegistration;
import com.example.healthbackend.webapp.helperclass.PatientListingData;
import com.example.healthbackend.webapp.helperclass.PatientsList;

import java.util.Date;
import java.util.List;

public interface PatientRegistrationService {

    PatientRegistration savePatientRegistration(PatientRegistration patientRegistration);
    Boolean isPatientID(String patientId);
    PatientsList getPatientListing(Date visitationDate);
}
