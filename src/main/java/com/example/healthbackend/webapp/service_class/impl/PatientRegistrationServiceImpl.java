package com.example.healthbackend.webapp.service_class.impl;

import com.example.healthbackend.webapp.entity.PatientRegistration;
import com.example.healthbackend.webapp.helperclass.*;
import com.example.healthbackend.webapp.repository.PatientRegistrationRepository;
import com.example.healthbackend.webapp.service_class.service.PatientRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PatientRegistrationServiceImpl implements PatientRegistrationService {

    @Autowired
    private PatientRegistrationRepository patientRegistrationRepository;

    @Autowired
    private PatientsVitalsServiceImpl patientsVitalsService;

    @Override
    public PatientRegistration savePatientRegistration(PatientRegistration patientRegistration) {
        return patientRegistrationRepository.save(patientRegistration);
    }

    @Override
    public Boolean isPatientID(String patientId) {
        return patientRegistrationRepository.existsByPatientId(patientId);
    }

    @Override
    public PatientsList getPatientListing(Date visitationDate) {

        List<PatientListingData> patientListingDataList = patientsVitalsService.getPatientsVitalsList(visitationDate);

        return new PatientsList(
                patientListingDataList.size(),
                null, null,
                patientListingDataList);
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

    public PatientListingData getPatientData(String patientUUID){

        Optional<PatientRegistration> optionalPatientRegistration = patientRegistrationRepository.findById(patientUUID);
        if (optionalPatientRegistration.isPresent()){

            PatientRegistration patientRegistration = optionalPatientRegistration.get();
            String firstName = patientRegistration.getFirstName();
            String lastName = patientRegistration.getLastName();
            String fullNames = firstName +  " " + lastName;

            Date dateOfBirth = patientRegistration.getDateOfBirth();
            Formatter formatter = new Formatter();
            int age = formatter.calculateAge(dateOfBirth);

            return new PatientListingData(fullNames, age, 0);
        }else {
            return null;
        }

    }


}
