package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugIndentDetails implements Serializable, Comparable<InventoryStoreDrugIndentDetails> {

    private String category;
    private String drugName;
    private String formulation;
    private Integer quantity;


    public String getCategory() {
        return category;
    }

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int compareTo(InventoryStoreDrugIndentDetails o) {
        return (this.drugName).compareTo(o.drugName);
    }
}
