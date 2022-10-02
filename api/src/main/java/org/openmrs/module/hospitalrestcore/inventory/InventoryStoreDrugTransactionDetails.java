package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugTransactionDetails implements Serializable, Comparable<InventoryStoreDrugTransactionDetails> {

    private String drugName;
    private String drugFormulation;
    private Integer quantity;
    private Integer currentQuantity;
    private String batchNo;
    private String companyName;
    private String uuid;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugFormulation() {
        return drugFormulation;
    }

    public void setDrugFormulation(String drugFormulation) {
        this.drugFormulation = drugFormulation;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int compareTo(InventoryStoreDrugTransactionDetails o) {
        return (this.drugName).compareTo(o.drugName);
    }
}
