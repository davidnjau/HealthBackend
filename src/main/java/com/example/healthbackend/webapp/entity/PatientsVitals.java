package com.example.healthbackend.webapp.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PatientsVitals {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uui_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private String id;

    private String patientUUID;

    private Date visitDate;

    private double weightInKgs;

    private double heightInCm;

    public PatientsVitals() {
    }

    public PatientsVitals(String patientUUID, Date visitDate, double weightInKgs, double heightInCm) {
        this.patientUUID = patientUUID;
        this.visitDate = visitDate;
        this.weightInKgs = weightInKgs;
        this.heightInCm = heightInCm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientUUID() {
        return patientUUID;
    }

    public void setPatientUUID(String patientUUID) {
        this.patientUUID = patientUUID;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public double getWeightInKgs() {
        return weightInKgs;
    }

    public void setWeightInKgs(double weightInKgs) {
        this.weightInKgs = weightInKgs;
    }

    public double getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(double heightInCm) {
        this.heightInCm = heightInCm;
    }
}

