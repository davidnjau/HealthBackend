package com.example.healthbackend.webapp.service_class.impl;

import com.example.healthbackend.webapp.entity.PatientRegistration;
import com.example.healthbackend.webapp.helperclass.ErrorMessage;
import com.example.healthbackend.webapp.helperclass.PatientRegistrationData;
import com.example.healthbackend.webapp.helperclass.Results;
import com.example.healthbackend.webapp.helperclass.SuccessMessage;
import com.example.healthbackend.webapp.repository.PatientRegistrationRepository;
import com.example.healthbackend.webapp.service_class.service.PatientRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientRegistrationServiceImpl implements PatientRegistrationService {

    @Autowired
    private PatientRegistrationRepository patientRegistrationRepository;

    @Override
    public PatientRegistration savePatientRegistration(PatientRegistration patientRegistration) {
        return patientRegistrationRepository.save(patientRegistration);
    }

    @Override
    public Boolean isPatientID(String patientId) {
        return patientRegistrationRepository.existsByPatientId(patientId);
    }

    public Results addPatientRegistrationData(PatientRegistrationData patientRegistrationData){

        boolean isPatientId = isPatientID(patientRegistrationData.getPatientId());
        if (!isPatientId){
            PatientRegistration patientRegistration = new PatientRegistration(
                    patientRegistrationData.getPatientId(),
                    patientRegistrationData.getRegistrationDate(),
                    patientRegistrationData.getFirstName(),
                    patientRegistrationData.getLastName(),
                    patientRegistrationData.getDateOfBirth(),
                    patientRegistrationData.getGender());
            PatientRegistration addedPatient = savePatientRegistration(patientRegistration);
            if (addedPatient != null){
                return new Results(200, new SuccessMessage("Patient has been saved successfully."));
            }else {
                return new Results(400, new ErrorMessage("Patient cannot be saved. Please try again after sometime."));

            }

        }else {
            return new Results(400, "The patient id already exists.");
        }


    }

}
