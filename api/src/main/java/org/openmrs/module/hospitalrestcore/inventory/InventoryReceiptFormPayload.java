package org.openmrs.module.hospitalrestcore.inventory;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryReceiptFormPayload {

    private InventoryDrug drug;
    private InventoryDrugFormulation formulation;

    private Integer rate;
    private Integer quantity;
    private BigDecimal vat;
    private BigDecimal sgst;
    private BigDecimal cgst;
    private BigDecimal mrPrice;

    private String batchNo;
    private String companyName;
    private Date dateManufacture;
    private Date dateExpiry;
    private Date receiptDate;

    private float waiverPercentage;

    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private BigDecimal amountAfterGST;

    HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

    SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

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

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) throws ParseException{

        this.receiptDate = formatterExt.parse(receiptDate);
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

    public void setDrug(String drug) {

        InventoryDrug inventoryDrug = new InventoryDrug();
        List<InventoryDrug> drugList = hospitalRestCoreService.listAllInventoryDrug();

        for (InventoryDrug d : drugList)
            if (Objects.equals(d.getName(), drug))
                inventoryDrug = d;

        if (StringUtils.isBlank(inventoryDrug.getName()))
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG, drug));
        else
            this.drug = inventoryDrug;
    }

    public InventoryDrugFormulation getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {

        InventoryDrugFormulation drugFormulation1 = new InventoryDrugFormulation();
        List<InventoryDrugFormulation> formulationList = hospitalRestCoreService.listAllInventoryDrugFormulation();

        for (InventoryDrugFormulation drugFormulation : formulationList) {
            if (Objects.equals(drugFormulation.getName(), formulation)) {
                drugFormulation1 = drugFormulation;
            }
        }

        if (drugFormulation1.getName() == null) {
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_FORMULATION, formulation));
        } else {
            this.formulation = drugFormulation1;
        }
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

    public Date getDateManufacture() {
        return dateManufacture;
    }

    public void setDateManufacture(String dateManufacture) throws ParseException {
        this.dateManufacture = formatterExt.parse(dateManufacture);
    }

    public Date getDateExpiry() {
        return dateExpiry;
    }

    public void setDateExpiry(String dateExpiry) throws ParseException {
        this.dateExpiry = formatterExt.parse(dateExpiry);
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice() {

        BigDecimal rate = BigDecimal.valueOf(getRate());
        BigDecimal quantity = BigDecimal.valueOf(getQuantity());
        BigDecimal cd = BigDecimal.valueOf(getWaiverPercentage());
        BigDecimal cdAmount = rate.multiply(cd);
        BigDecimal totalAmount = getTotalAmount();
        BigDecimal unitPrice = totalAmount.subtract(cdAmount).divide(quantity, 2, RoundingMode.CEILING);

        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount() {
        int rate = getRate();
        int quantity = getQuantity();

        BigDecimal totalAmount = BigDecimal.valueOf((long) rate * quantity);

        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmountAfterGST() {
        return amountAfterGST;
    }

    public void setAmountAfterGST() {
        BigDecimal rate = BigDecimal.valueOf(getRate());
        BigDecimal quantity = BigDecimal.valueOf(getQuantity());
        BigDecimal vat = getVat() != null ? getVat() : BigDecimal.ZERO;
        BigDecimal cgst = getCgst() != null ? getCgst() : BigDecimal.ZERO;
        BigDecimal sgst = getSgst() != null ? getSgst() : BigDecimal.ZERO;
        BigDecimal cd = BigDecimal.valueOf(getWaiverPercentage());
        BigDecimal cgstAmount = rate.multiply(cgst);
        BigDecimal sgstAmount = rate.multiply(sgst);
        BigDecimal cdAmount = rate.multiply(cd);
        BigDecimal totalAmount = getTotalAmount();
        BigDecimal vatAmount = rate.divide(BigDecimal.valueOf(100),2, RoundingMode.CEILING).multiply(vat).multiply(quantity);
        BigDecimal amountAfterGST = totalAmount.add(cgstAmount).add(sgstAmount).add(vatAmount).subtract(cdAmount);

        this.amountAfterGST = amountAfterGST;
    }
}
