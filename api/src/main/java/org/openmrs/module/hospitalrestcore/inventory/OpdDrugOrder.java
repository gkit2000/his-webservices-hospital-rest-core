package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class OpdDrugOrder extends CustomBaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer opdDrugOrderId;
    private Patient patient;
    private Encounter encounter;
    private int orderStatus;
    private int cancelStatus;
    private int orderFrom;
    private String referralWardName;


    public Integer getOpdDrugOrderId() {
        return opdDrugOrderId;
    }

    public void setOpdDrugOrderId(Integer opdDrugOrderId) {
        this.opdDrugOrderId = opdDrugOrderId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(int cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public int getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(int orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getReferralWardName() {
        return referralWardName;
    }

    public void setReferralWardName(String referralWardName) {
        this.referralWardName = referralWardName;
    }
}
