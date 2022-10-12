package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugOrderIssueDetail extends CustomBaseOpenmrsObject implements Serializable,
        Comparable<InventoryStoreDrugOrderIssueDetail>{

    private static final long serialVersionUID = 1L;
    private Integer id;
    private InventoryDrug drug;
    private InventoryDrugFormulation formulation;
    private Integer quantity;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InventoryDrug getDrug() {
        return drug;
    }

    public void setDrug(InventoryDrug drug) {
        this.drug = drug;
    }

    public InventoryDrugFormulation getFormulation() {
        return formulation;
    }

    public void setFormulation(InventoryDrugFormulation formulation) {
        this.formulation = formulation;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(InventoryStoreDrugOrderIssueDetail o) {
        return (this.id).compareTo(o.id);
    }
}
