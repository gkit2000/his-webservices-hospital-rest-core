package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrug extends CustomBaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private InventoryStore store;
    private InventoryDrug drug;
    private InventoryDrugFormulation formulation;
    private Integer currentQuantity;
    private Integer receiptQuantity;
    private Integer issueQuantity;
    private Integer statusIndent; // =1 done =0 not yet
    private Integer reorderQuantity;
    private Integer openingBalance;
    private Integer closingBalance;
    private Integer status; // =1 done =0 not yet


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

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Integer getReceiptQuantity() {
        return receiptQuantity;
    }

    public void setReceiptQuantity(Integer receiptQuantity) {
        this.receiptQuantity = receiptQuantity;
    }

    public Integer getIssueQuantity() {
        return issueQuantity;
    }

    public void setIssueQuantity(Integer issueQuantity) {
        this.issueQuantity = issueQuantity;
    }

    public Integer getStatusIndent() {
        return statusIndent;
    }

    public void setStatusIndent(Integer statusIndent) {
        this.statusIndent = statusIndent;
    }

    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public Integer getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Integer openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Integer getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Integer closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
