package com.example.healthbackend.webapp.service_class.impl;

import com.example.healthbackend.webapp.entity.PatientRegistration;
import com.example.healthbackend.webapp.entity.PatientsVitals;
import com.example.healthbackend.webapp.helperclass.*;
import com.example.healthbackend.webapp.repository.PatientRegistrationRepository;
import com.example.healthbackend.webapp.service_class.service.PatientRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        Formatter formatter = new Formatter();

        boolean isPatientId = isPatientID(patientRegistrationData.getPatientId());
        if (!isPatientId){

            PatientRegistration patientRegistration = new PatientRegistration(
                    patientRegistrationData.getPatientId(),
                    formatter.changeDateFormat(patientRegistrationData.getRegistrationDate()),
                    patientRegistrationData.getFirstName(),
                    patientRegistrationData.getLastName(),
                    formatter.changeDateFormat(patientRegistrationData.getDateOfBirth()),
                    patientRegistrationData.getGender());
            PatientRegistration addedPatient = savePatientRegistration(patientRegistration);
            if (addedPatient != null){
                return new Results(200, "Patient has been saved successfully.");
            }else {
                return new Results(400, "Patient cannot be saved. Please try again after sometime.");

            }

        }else {
            return new Results(400, "The patient id already exists.");
        }


    }

    public PatientListingData getPatientData(String patientUUID){

        Formatter formatter = new Formatter();

        Optional<PatientRegistration> optionalPatientRegistration = patientRegistrationRepository.findById(patientUUID);
        if (optionalPatientRegistration.isPresent()){

            PatientRegistration patientRegistration = optionalPatientRegistration.get();
            String firstName = patientRegistration.getFirstName();
            String lastName = patientRegistration.getLastName();
            String fullNames = firstName +  " " + lastName;

            Date dateOfBirth = formatter.changeDateFormat(patientRegistration.getDateOfBirth());
            int age = formatter.calculateAge(dateOfBirth);

            return new PatientListingData(fullNames, age, 0);
        }else {
            return null;
        }

    }




}
