package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.Concept;
import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class OpdDrugOrderDetail extends CustomBaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private OpdDrugOrder opdDrugOrder;
    private InventoryDrug inventoryDrug;
    private InventoryDrugFormulation inventoryDrugFormulation;
    private Concept frequency;
    private Integer noOfDays;
    private String comments;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OpdDrugOrder getOpdDrugOrder() {
        return opdDrugOrder;
    }

    public void setOpdDrugOrder(OpdDrugOrder opdDrugOrder) {
        this.opdDrugOrder = opdDrugOrder;
    }

    public InventoryDrug getInventoryDrug() {
        return inventoryDrug;
    }

    public void setInventoryDrug(InventoryDrug inventoryDrug) {
        this.inventoryDrug = inventoryDrug;
    }

    public InventoryDrugFormulation getInventoryDrugFormulation() {
        return inventoryDrugFormulation;
    }

    public void setInventoryDrugFormulation(InventoryDrugFormulation inventoryDrugFormulation) {
        this.inventoryDrugFormulation = inventoryDrugFormulation;
    }

    public Concept getFrequency() {
        return frequency;
    }

    public void setFrequency(Concept frequency) {
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
}
