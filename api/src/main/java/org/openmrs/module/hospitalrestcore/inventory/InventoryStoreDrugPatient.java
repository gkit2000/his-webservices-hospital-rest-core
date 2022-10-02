package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.Patient;
import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugPatient extends CustomBaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private InventoryStore store;
    private Patient patient;
    private String name;
    private String prescription;
    private String identifier;
    private Integer total;
    private float waiverPercentage;
    private Integer totalPayable;
    private Integer amountPaid;
    private Integer balanceReturned;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InventoryStore getStore() {
        return store;
    }

    public void setStore(InventoryStore store) {
        this.store = store;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public float getWaiverPercentage() {
        return waiverPercentage;
    }

    public void setWaiverPercentage(float waiverPercentage) {
        this.waiverPercentage = waiverPercentage;
    }

    public Integer getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(Integer totalPayable) {
        this.totalPayable = totalPayable;
    }

    public Integer getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Integer getBalanceReturned() {
        return balanceReturned;
    }

    public void setBalanceReturned(Integer balanceReturned) {
        this.balanceReturned = balanceReturned;
    }
}
