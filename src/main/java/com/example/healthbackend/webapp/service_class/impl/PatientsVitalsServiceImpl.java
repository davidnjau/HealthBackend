package com.example.healthbackend.webapp.service_class.impl;

import com.example.healthbackend.webapp.entity.PatientRegistration;
import com.example.healthbackend.webapp.entity.PatientsVitals;
import com.example.healthbackend.webapp.helperclass.*;
import com.example.healthbackend.webapp.repository.PatientsVitalsRepository;
import com.example.healthbackend.webapp.service_class.service.PatientsVitalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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
    public List<PatientsVitals> getPatientsVitalsList(Date visitationDate) {

        Formatter formatter = new Formatter();
        List<PatientsVitals> datedList = new ArrayList<>();
        List<PatientsVitals> vitalsList = patientsVitalsRepository.findAll();
        for (PatientsVitals patientsVitals : vitalsList){

            Date date = patientsVitals.getVisitDate();

            boolean isEqual = formatter.compareDates(visitationDate, date);
            if (isEqual){
                datedList.add(patientsVitals);
            }

        }

        return datedList;
    }


    private Results addPatientsVitals(PatientsVitalsData patientsVitalsData){

        Formatter formatter = new Formatter();

        String patientUUID = patientsVitalsData.getPatientUUID();
        Date visitationDate = formatter.changeDateFormat(patientsVitalsData.getVisitDate());

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

    public PatientsList getPatientsDataList(String visitationDate){

        Formatter formatter = new Formatter();
        List<PatientListingData> patientListingDataList = new ArrayList<>();

        Date visitDate = formatter.convertDateMillis(visitationDate);
//        String newVisitDate =  formatter.changeDateFormat2(visitDate);
//        System.out.println("+++++++xx " + newVisitDate);

        List<PatientsVitals> patientsVitalsList = getPatientsVitalsList(visitDate);
        System.out.println("+++++++ " + patientsVitalsList);

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

        return new PatientsList(
                patientListingDataList.size(),
                null, null,
                patientListingDataList);


    }


}
