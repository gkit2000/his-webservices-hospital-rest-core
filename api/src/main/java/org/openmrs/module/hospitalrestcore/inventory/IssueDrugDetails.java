package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class IssueDrugDetails implements Serializable, Comparable<IssueDrugDetails> {

    private String dateExpiry;
    private String dateManufacture;
    private String companyName;
    private String batchNo;
    private Integer quantityAvailable;


    public String getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    public String getDateManufacture() {
        return dateManufacture;
    }

    public void setDateManufacture(String dateManufacture) {
        this.dateManufacture = dateManufacture;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public int compareTo(IssueDrugDetails o) {
        return (this.dateManufacture).compareTo(o.dateManufacture);
    }
}
