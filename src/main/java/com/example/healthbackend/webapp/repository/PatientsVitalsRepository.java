package com.example.healthbackend.webapp.repository;

import com.example.healthbackend.webapp.entity.PatientsVitals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PatientsVitalsRepository extends JpaRepository<PatientsVitals, String> {

    boolean existsByPatientUUIDAndVisitDate(String patientUUID, Date visitationDate);

//    @Query("select u from PatientsVitals u where u.visitDate like %?1")
//    List<PatientsVitals> findAllByVisitDate(String visitationDate);
}
