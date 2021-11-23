package com.example.healthbackend.webapp.repository;

import com.example.healthbackend.webapp.entity.PatientsForm;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.util.Date;

public interface PatientsFormRepository extends JpaRepository<PatientsForm, String> {

    boolean existsByPatientUUIDAndVisitationDate(String patientUUID, Date visitationDate);
}
