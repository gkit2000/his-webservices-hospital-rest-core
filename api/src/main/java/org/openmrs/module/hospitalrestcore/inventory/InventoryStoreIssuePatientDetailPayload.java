package org.openmrs.module.hospitalrestcore.inventory;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreIssuePatientDetailPayload {

    private String patientIdentifier;
    private String patientName;
    private String category;
    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
