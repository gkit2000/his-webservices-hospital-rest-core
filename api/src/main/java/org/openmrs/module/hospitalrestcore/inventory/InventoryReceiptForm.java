package org.openmrs.module.hospitalrestcore.inventory;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryReceiptForm extends CustomBaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private InventoryDrug drug;
    private InventoryDrugFormulation formulation;

    private Integer rate;
    private Integer quantity;
    private BigDecimal VAT;
    private BigDecimal sgst;
    private BigDecimal cgst;
    private BigDecimal mrPrice;

    private String batchNo;
    private String companyName;
    private Date dateManufacture;
    private Date dateExpiry;
    private Date receiptDate;

    private float waiverPercentage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyNameShort() {
        return StringUtils.isNotBlank(companyName) && companyName.length() > 10 ? companyName.substring(0, 7) + "..."
                : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getWaiverPercentage() {
        return waiverPercentage;
    }

    public void setWaiverPercentage(float waiverPercentage) {
        this.waiverPercentage = waiverPercentage;
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

    public BigDecimal getVAT() {
        return VAT;
    }

    public void setVAT(BigDecimal VAT) {
        this.VAT = VAT;
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

    public Date getDateManufacture() {
        return dateManufacture;
    }

    public void setDateManufacture(Date dateManufacture) {
        this.dateManufacture = dateManufacture;
    }

    public Date getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(Date dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }
}