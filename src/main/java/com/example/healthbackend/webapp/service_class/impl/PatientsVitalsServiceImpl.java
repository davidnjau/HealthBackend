package com.example.healthbackend.webapp.service_class.impl;

import com.example.healthbackend.webapp.entity.PatientRegistration;
import com.example.healthbackend.webapp.entity.PatientsVitals;
import com.example.healthbackend.webapp.helperclass.Formatter;
import com.example.healthbackend.webapp.helperclass.PatientListingData;
import com.example.healthbackend.webapp.helperclass.PatientsVitalsData;
import com.example.healthbackend.webapp.helperclass.Results;
import com.example.healthbackend.webapp.repository.PatientsVitalsRepository;
import com.example.healthbackend.webapp.service_class.service.PatientsVitalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PatientsVitalsServiceImpl implements PatientsVitalsService {

    @Autowired
    private PatientsVitalsRepository patientsVitalsRepository;

    @Autowired
    private PatientRegistrationServiceImpl patientRegistrationService;

    @Override
    public Results savePatientsVitals(PatientsVitalsData patientsVitalsData) {
        return addPatientsVitals(patientsVitalsData);
    }

    @Override
    public boolean isPatientVisits(String patientId, Date visitationDate) {
        return patientsVitalsRepository.existsByPatientUUIDAndVisitDate(patientId, visitationDate);
    }

    @Override
    public List<PatientListingData> getPatientsVitalsList(Date visitationDate) {
        return getPatientsDataList(visitationDate);
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

    private List<PatientListingData> getPatientsDataList(Date visitationDate){

        Formatter formatter = new Formatter();
        List<PatientListingData> patientListingDataList = new ArrayList<>();

        List<PatientsVitals> patientsVitalsList = patientsVitalsRepository.findByVisitDate(visitationDate);
        for (PatientsVitals patientsVitals : patientsVitalsList) {
            double weightKgs = patientsVitals.getWeightInKgs();
            double heightInCm = patientsVitals.getHeightInCm();
            String patientUUID = patientsVitals.getPatientUUID();
            double bmi = formatter.calculateBMI(weightKgs, heightInCm);

            PatientListingData patientData = patientRegistrationService.getPatientData(patientUUID);
            if (patientData != null){
                PatientListingData returnData = new PatientListingData(patientData.getUserName(), patientData.getAge(), bmi);
                patientListingDataList.add(returnData);
            }

        }

        return patientListingDataList;

    }
}
