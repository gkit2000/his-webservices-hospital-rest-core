package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugReceiptDetails implements Serializable, Comparable<InventoryStoreDrugReceiptDetails>{

    private String drugName;
    private String formulationName;

    private Integer receiptQuantity;
    private Integer rate;
    private BigDecimal unitPrice;
    private BigDecimal vat;
    private BigDecimal mrPrice;
    private String batchNo;
    private float cd;
    private Integer cdAmount;
    private BigDecimal cgst;
    private Integer cgstAmount;
    private BigDecimal sgst;
    private Integer sgstAmount;
    private String dateExpiry;
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

    public Integer getReceiptQuantity() {
        return receiptQuantity;
    }

    public void setReceiptQuantity(Integer receiptQuantity) {
        this.receiptQuantity = receiptQuantity;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
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

    public float getCd() {
        return cd;
    }

    public void setCd(float cd) {
        this.cd = cd;
    }

    public Integer getCdAmount() {
        return cdAmount;
    }

    public void setCdAmount(Integer cdAmount) {
        this.cdAmount = cdAmount;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public Integer getCgstAmount() {
        return cgstAmount;
    }

    public void setCgstAmount(Integer cgstAmount) {
        this.cgstAmount = cgstAmount;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public Integer getSgstAmount() {
        return sgstAmount;
    }

    public void setSgstAmount(Integer sgstAmount) {
        this.sgstAmount = sgstAmount;
    }

    public String getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) {
        this.dateExpiry = dateExpiry;
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
    public int compareTo(InventoryStoreDrugReceiptDetails o) {
        return (this.drugName).compareTo(o.drugName);
    }
}
