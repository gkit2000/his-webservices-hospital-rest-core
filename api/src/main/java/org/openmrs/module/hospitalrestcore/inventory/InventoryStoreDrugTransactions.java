package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugTransactions implements Serializable, Comparable<InventoryStoreDrugTransactions>{

    private String drugName;
    private String drugCategory;
    private String drugFormulation;
    private Integer currentQuantity;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugCategory() {
        return drugCategory;
    }

    public void setDrugCategory(String drugCategory) {
        this.drugCategory = drugCategory;
    }

    public String getDrugFormulation() {
        return drugFormulation;
    }

    public void setDrugFormulation(String drugFormulation) {
        this.drugFormulation = drugFormulation;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    @Override
    public int compareTo(InventoryStoreDrugTransactions o) {
        return (this.drugName).compareTo(o.drugName);
    }
}
