package org.openmrs.module.hospitalrestcore.inventory;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryReceiptForm extends CustomBaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String drugName;
    private Set<InventoryDrugFormulation> formulations;
    private InventoryDrugCategory category;

    private Integer rate;
    private Integer quantity;
    private Integer VAT;
    private Integer sgst;
    private Integer cgst;
    private Integer mrPrice;

    private String batchNo;
    private String companyName;
    private String dateManufacture;
    private String dateExpiry;
    private String receiptDate;

    private float waiverPercentage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDrugNameShort() {
        return StringUtils.isNotBlank(drugName) && drugName.length() > 30 ? drugName.substring(0, 30) + "..." : drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Set<InventoryDrugFormulation> getFormulations() {
        return formulations;
    }

    public void setFormulations(Set<InventoryDrugFormulation> formulations) {
        this.formulations = formulations;
    }

    public InventoryDrugCategory getCategory() {
        return category;
    }

    public void setCategory(InventoryDrugCategory category) {
        this.category = category;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getVAT() {
        return VAT;
    }

    public void setVAT(Integer VAT) {
        this.VAT = VAT;
    }

    public Integer getSgst() {
        return sgst;
    }

    public void setSgst(Integer sgst) {
        this.sgst = sgst;
    }

    public Integer getCgst() {
        return cgst;
    }

    public void setCgst(Integer cgst) {
        this.cgst = cgst;
    }

    public Integer getMrPrice() {
        return mrPrice;
    }

    public void setMrPrice(Integer mrPrice) {
        this.mrPrice = mrPrice;
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

    public String getDateManufacture() {
        return dateManufacture;
    }

    public void setDateManufacture(String dateManufacture) {
        this.dateManufacture = dateManufacture;
    }

    public String getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public float getWaiverPercentage() {
        return waiverPercentage;
    }

    public void setWaiverPercentage(float waiverPercentage) {
        this.waiverPercentage = waiverPercentage;
    }
}