package com.example.healthbackend.webapp.controller;

import com.example.healthbackend.webapp.helperclass.ErrorMessage;
import com.example.healthbackend.webapp.helperclass.PatientRegistrationData;
import com.example.healthbackend.webapp.helperclass.Results;
import com.example.healthbackend.webapp.helperclass.SuccessMessage;
import com.example.healthbackend.webapp.service_class.impl.PatientRegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientRegistrationController {

    @Autowired
    private PatientRegistrationServiceImpl patientRegistrationService;

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

}