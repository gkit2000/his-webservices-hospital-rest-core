package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryReceiptFormDetails implements Serializable, Comparable<InventoryReceiptFormDetails> {

    private String drugName;
    private String formulationName;

    private Integer rate;
    private Integer quantity;
    private BigDecimal vat;
    private BigDecimal sgst;
    private BigDecimal cgst;
    private BigDecimal mrPrice;

    private String batchNo;
    private String companyName;
    private String dateManufacture;
    private String dateExpiry;
    private String receiptDate;

    private float waiverPercentage;

    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private BigDecimal amountAfterGST;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getFormulationName() {
        return formulationName;
    }

    public void setFormulationName(String formulationName) {
        this.formulationName = formulationName;
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

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public BigDecimal getMrPrice() {
        return mrPrice;
    }

    public void setMrPrice(BigDecimal mrPrice) {
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmountAfterGST() {
        return amountAfterGST;
    }

    public void setAmountAfterGST(BigDecimal amountAfterGST) {
        this.amountAfterGST = amountAfterGST;
    }

    @Override
    public int compareTo(InventoryReceiptFormDetails o) {
        return (this.companyName).compareTo(o.companyName);
    }
}
