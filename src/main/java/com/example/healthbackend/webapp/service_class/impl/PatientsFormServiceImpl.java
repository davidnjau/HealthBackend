package com.example.healthbackend.webapp.service_class.impl;

import com.example.healthbackend.webapp.entity.PatientsForm;
import com.example.healthbackend.webapp.helperclass.Formatter;
import com.example.healthbackend.webapp.helperclass.PatientsFormData;
import com.example.healthbackend.webapp.helperclass.Results;
import com.example.healthbackend.webapp.repository.PatientsFormRepository;
import com.example.healthbackend.webapp.service_class.service.PatientsFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PatientsFormServiceImpl implements PatientsFormService {

    @Autowired
    private PatientsFormRepository patientsFormRepository;

    @Override
    public Results savePatientsForm(PatientsFormData patientsFormData) {
        return addPatientsForm(patientsFormData);
    }

    @Override
    public boolean isPatientForm(String patientId, Date visitationDate) {
        return patientsFormRepository.existsByPatientUUIDAndVisitationDate(patientId, visitationDate);
    }

    private Results addPatientsForm(PatientsFormData patientsFormData){

        Formatter formatter = new Formatter();

        String patientUUID = patientsFormData.getPatientUUID();
        Date visitDate = formatter.changeDateFormat(patientsFormData.getVisitDate());
        String generalHealth = patientsFormData.getGeneralHealth();
        String comments = patientsFormData.getComments();

        String weightDiet = patientsFormData.getWeightDiet();
        String takingDrugs = patientsFormData.getTakingDrugs();

       boolean isPatientVisitation = isPatientForm(patientUUID, visitDate);
       if (!isPatientVisitation){
           //Does not exist

           PatientsForm patientsForm = new PatientsForm(
                   patientUUID, visitDate, generalHealth, weightDiet, takingDrugs, comments);
           patientsFormRepository.save(patientsForm);

           return new Results(200, "Patient's form data has been saved successfully.");

       }else {
           //Exists
           return new Results(400, "Please select a new visitation date for the patient.");
       }
    }

}
