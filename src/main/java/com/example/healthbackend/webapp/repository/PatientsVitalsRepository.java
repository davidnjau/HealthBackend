package com.example.healthbackend.webapp.repository;

import com.example.healthbackend.webapp.entity.PatientsVitals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PatientsVitalsRepository extends JpaRepository<PatientsVitals, String> {

    boolean existsByPatientUUIDAndVisitDate(String patientUUID, Date visitationDate);
    List<PatientsVitals> findByVisitDate(Date visitationDate);
}
