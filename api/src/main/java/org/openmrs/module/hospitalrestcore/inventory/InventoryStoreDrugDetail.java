package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugDetail implements Serializable, Comparable<InventoryStoreDrugDetail>{

    private String drug;
    private String formulation;
    private Integer currentQuantity;
    private Integer receiptQuantity;
    private Integer issueQuantity;
    private Integer reorderQuantity;
    private Integer openingBalance;
    private Integer closingBalance;

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
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

    @Override
    public int compareTo(InventoryStoreDrugDetail o) {
        return (this.drug).compareTo(o.drug);
    }
}
