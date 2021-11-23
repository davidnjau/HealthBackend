package com.example.healthbackend.webapp.repository;

import com.example.healthbackend.webapp.entity.PatientRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRegistrationRepository extends JpaRepository<PatientRegistration, String> {

    boolean existsByPatientId(String patientId);

}
