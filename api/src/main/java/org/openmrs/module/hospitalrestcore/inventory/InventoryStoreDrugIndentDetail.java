package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugIndentDetail extends CustomBaseOpenmrsObject implements Serializable,
        Comparable<InventoryStoreDrugIndentDetail> {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private InventoryStoreDrugIndent indent;
    private InventoryDrug drug;
    private InventoryDrugFormulation formulation;
    private Integer quantity;
    private Integer mainStoreTransfer;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InventoryStoreDrugIndent getIndent() {
        return indent;
    }

    public void setIndent(InventoryStoreDrugIndent indent) {
        this.indent = indent;
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

    public Integer getMainStoreTransfer() {
        return mainStoreTransfer;
    }

    public void setMainStoreTransfer(Integer mainStoreTransfer) {
        this.mainStoreTransfer = mainStoreTransfer;
    }

    @Override
    public int compareTo(InventoryStoreDrugIndentDetail o) {
        return (this.id).compareTo(o.id);
    }
}