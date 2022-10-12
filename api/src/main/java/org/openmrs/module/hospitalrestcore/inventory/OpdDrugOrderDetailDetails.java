package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class OpdDrugOrderDetailDetails implements Serializable, Comparable<OpdDrugOrderDetailDetails> {

    private String drugName;
    private String formulation;
    private String frequency;
    private Integer noOfDays;
    private String comments;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public int compareTo(OpdDrugOrderDetailDetails o) {
        return (this.drugName).compareTo(o.drugName);
    }
}
