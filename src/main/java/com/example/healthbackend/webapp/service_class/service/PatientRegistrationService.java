package com.example.healthbackend.webapp.service_class.service;

import com.example.healthbackend.webapp.entity.PatientRegistration;

public interface PatientRegistrationService {

    PatientRegistration savePatientRegistration(PatientRegistration patientRegistration);
    Boolean isPatientID(String patientId);
}
