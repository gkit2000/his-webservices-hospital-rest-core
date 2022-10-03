package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreIssuePatientDetail extends CustomBaseOpenmrsObject implements Serializable,
        Comparable<InventoryStoreIssuePatientDetail> {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String patientIdentifier;
    private String patientName;
    private String category;
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int compareTo(InventoryStoreIssuePatientDetail o) {
        return (this.id).compareTo(o.id);
    }
}
