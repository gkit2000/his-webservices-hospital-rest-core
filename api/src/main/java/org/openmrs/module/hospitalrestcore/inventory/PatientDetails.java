package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class PatientDetails implements Serializable,
        Comparable<PatientDetails> {

    private Integer patientId;
    private String patientIdentifier;
    private String patientName;
    private String category;
    private String gender;
    private Integer age;
    private String createdOn;


    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public int compareTo(PatientDetails o) {
        return (this.patientIdentifier).compareTo(o.patientIdentifier);
    }
}
