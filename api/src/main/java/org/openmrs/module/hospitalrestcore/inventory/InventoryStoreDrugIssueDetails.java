package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugIssueDetails implements Serializable,
        Comparable<InventoryStoreDrugIssueDetails> {

    private String drugName;
    private String formulation;
    private Integer quantity;
    private BigDecimal mrp;

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

    public BigDecimal getMrp() {
        return mrp;
    }

    public void setMrp(BigDecimal mrp) {
        this.mrp = mrp;
    }

    @Override
    public int compareTo(InventoryStoreDrugIssueDetails o) {
        return 0;
    }
}
