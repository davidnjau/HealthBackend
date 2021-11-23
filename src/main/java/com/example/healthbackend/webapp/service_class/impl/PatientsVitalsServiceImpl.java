package com.example.healthbackend.webapp.service_class.impl;

import com.example.healthbackend.webapp.entity.PatientsVitals;
import com.example.healthbackend.webapp.helperclass.Formatter;
import com.example.healthbackend.webapp.helperclass.PatientsVitalsData;
import com.example.healthbackend.webapp.helperclass.Results;
import com.example.healthbackend.webapp.repository.PatientsVitalsRepository;
import com.example.healthbackend.webapp.service_class.service.PatientsVitalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PatientsVitalsServiceImpl implements PatientsVitalsService {

    @Autowired
    private PatientsVitalsRepository patientsVitalsRepository;

    @Override
    public Results savePatientsVitals(PatientsVitalsData patientsVitalsData) {
        return addPatientsVitals(patientsVitalsData);
    }

    @Override
    public boolean isPatientVisits(String patientId, Date visitationDate) {
        return patientsVitalsRepository.existsByPatientUUIDAndVisitDate(patientId, visitationDate);
    }


    private Results addPatientsVitals(PatientsVitalsData patientsVitalsData){

        String patientUUID = patientsVitalsData.getPatientUUID();
        Date visitationDate = patientsVitalsData.getVisitDate();

        try {
            boolean isPatientVisitDate = isPatientVisits(patientUUID, visitationDate);
            if (!isPatientVisitDate){
                //Does not exist
                PatientsVitals patientsVitals = new PatientsVitals(
                        patientUUID,
                        visitationDate,
                        patientsVitalsData.getWeightInKgs(),
                        patientsVitalsData.getHeightInCm());
                patientsVitalsRepository.save(patientsVitals);

                Formatter formatter = new Formatter();
                double bmi = formatter.calculateBMI(patientsVitals.getWeightInKgs(), patientsVitals.getHeightInCm());
                PatientsVitalsData addedPatientsVitalsData = new PatientsVitalsData(patientUUID, visitationDate,
                        patientsVitals.getWeightInKgs(), patientsVitals.getHeightInCm(), bmi);

                return new Results(200, addedPatientsVitalsData);
            }else {
                //The entry exists
                return new Results(400, "Please select a new visitation date for this patient.");
            }
        }catch (Exception e){
            return new Results(500, "There was an issue accessing the db. Please try after sometime.");
        }



    }
}