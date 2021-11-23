package com.example.healthbackend.webapp.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PatientsForm {

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

    private Date visitationDate;

    private String generalHealth;

    private String weightDiet;

    private String takingDrugs;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    public PatientsForm() {
    }

    public PatientsForm(String patientUUID, Date visitationDate, String generalHealth, String weightDiet, String takingDrugs, String comments) {
        this.patientUUID = patientUUID;
        this.visitationDate = visitationDate;
        this.generalHealth = generalHealth;
        this.weightDiet = weightDiet;
        this.takingDrugs = takingDrugs;
        this.comments = comments;
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

    public Date getVisitationDate() {
        return visitationDate;
    }

    public void setVisitationDate(Date visitationDate) {
        this.visitationDate = visitationDate;
    }

    public String getGeneralHealth() {
        return generalHealth;
    }

    public void setGeneralHealth(String generalHealth) {
        this.generalHealth = generalHealth;
    }

    public String getWeightDiet() {
        return weightDiet;
    }

    public void setWeightDiet(String weightDiet) {
        this.weightDiet = weightDiet;
    }

    public String getTakingDrugs() {
        return takingDrugs;
    }

    public void setTakingDrugs(String takingDrugs) {
        this.takingDrugs = takingDrugs;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

