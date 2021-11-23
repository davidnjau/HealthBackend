package com.example.healthbackend.webapp.service_class.service;

import com.example.healthbackend.webapp.helperclass.PatientsFormData;
import com.example.healthbackend.webapp.helperclass.Results;

import java.util.Date;

public interface PatientsFormService {

    Results savePatientsForm(PatientsFormData patientsFormData);
    boolean isPatientForm(String patientId, Date visitationDate);

}
