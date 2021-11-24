package com.example.healthbackend.webapp.controller;

import com.example.healthbackend.webapp.entity.PatientsVitals;
import com.example.healthbackend.webapp.helperclass.*;
import com.example.healthbackend.webapp.service_class.impl.PatientRegistrationServiceImpl;
import com.example.healthbackend.webapp.service_class.impl.PatientsVitalsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PatientRegistrationController {

    @Autowired
    private PatientRegistrationServiceImpl patientRegistrationService;

    @Autowired
    private PatientsVitalsServiceImpl patientsVitalsService;

    @RequestMapping(value = "/api/v1/patient/register-patient", method = RequestMethod.POST)
    public ResponseEntity addPatients(@RequestBody PatientRegistrationData patientRegistrationData){

        Results addedPatients = patientRegistrationService.addPatientRegistrationData(patientRegistrationData);
        if (addedPatients != null){

            int statusCode = addedPatients.getStatusCode();
            var results = addedPatients.getDetails();
            var message = results.toString();

            if (statusCode == 200){
                return new ResponseEntity(new SuccessMessage(message), HttpStatus.OK);
            }else {
                return ResponseEntity.badRequest().body(new ErrorMessage(message));
            }

        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage("Error adding the patient. Please try again."));

        }

    }

    @RequestMapping(value = "/api/v1/patient/get-patient-listing/{visitationDate}", method = RequestMethod.GET)
    public ResponseEntity getPatientDetails(@PathVariable("visitationDate") String visitationDate){

        PatientsList patientDataList = patientsVitalsService.getPatientsDataList(visitationDate);
        return new ResponseEntity(patientDataList, HttpStatus.OK);

    }

}
