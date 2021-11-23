package com.example.healthbackend.webapp.controller;

import com.example.healthbackend.webapp.helperclass.ErrorMessage;
import com.example.healthbackend.webapp.helperclass.PatientsFormData;
import com.example.healthbackend.webapp.helperclass.PatientsVitalsData;
import com.example.healthbackend.webapp.helperclass.Results;
import com.example.healthbackend.webapp.service_class.impl.PatientsFormServiceImpl;
import com.example.healthbackend.webapp.service_class.impl.PatientsVitalsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientFormController {

    @Autowired
    private PatientsFormServiceImpl patientsFormService;

    @RequestMapping(value = "/api/v1/patient/add-forms", method = RequestMethod.POST)
    public ResponseEntity addPatients(@RequestBody PatientsFormData patientsFormData){

        Results addedPatients = patientsFormService.savePatientsForm(patientsFormData);
        int statusCode = addedPatients.getStatusCode();
        var results = addedPatients.getDetails();
        var message = results.toString();

        if (statusCode == 200){
            return new ResponseEntity(message, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new ErrorMessage(message));

        }

    }

}
