package org.openmrs.module.hospitalrestcore.inventory;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryReceiptFormPayload {

    private InventoryDrugCategory drug;
    private InventoryDrugFormulation formulation;

    private Integer rate;
    private Integer quantity;
    private BigDecimal vat;
    private BigDecimal sgst;
    private BigDecimal cgst;
    private BigDecimal mrPrice;
    private BigDecimal totalAmount;
    private BigDecimal billAmount;

    private String batchNo;
    private String companyName;
    private String uuid;
    private Date dateManufacture;
    private Date dateExpiry;
    private Date receiptDate;
    private String receiptNumber;

    private float waiverPercentage;

    private Boolean retired = false;

    @Autowired
    InventoryDrugFormulation drugFormulation = new InventoryDrugFormulation();

    @Autowired
    InventoryDrugCategory drugCategory = new InventoryDrugCategory();

    HospitalRestCoreService hospitalRestCoreService = Context.getService(HospitalRestCoreService.class);

    SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

    private final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    private final char[] ALPHANUMERIC = (LETTERS + LETTERS.toUpperCase() + "0123456789").toCharArray();

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public float getWaiverPercentage() {
        return waiverPercentage;
    }

    public void setWaiverPercentage(float waiverPercentage) {
        this.waiverPercentage = waiverPercentage;
    }

    public Boolean getRetired() {
        return retired;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public InventoryDrugCategory getDrug() {
        return drug;
    }

    public void setDrug(String drug) {

        InventoryDrugCategory drugCategory1 = new InventoryDrugCategory();
        List<InventoryDrugCategory> categoryList = hospitalRestCoreService.listAllInventoryDrugCategory();

        for (InventoryDrugCategory category : categoryList) {
            if (Objects.equals(category.getName(), drug)) {
                drugCategory1 = category;
            }
        }

        if (drugCategory1.getName() == null) {
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_DRUG_CATEGORY, drug));
        } else {
            this.drug = drugCategory1;
        }
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

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) throws ParseException {
        this.receiptDate = formatterExt.parse(receiptDate);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount() {
        int rate = getRate();
        int quantity = getQuantity();
        BigDecimal cgst = getCgst();
        BigDecimal sgst = getSgst();

        BigDecimal r1 = BigDecimal.valueOf((long) rate * quantity);
        BigDecimal r2 = (r1.multiply(cgst)).divide(BigDecimal.valueOf(100),2, RoundingMode.CEILING);
        BigDecimal r3 = (r1.multiply(sgst)).divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING);

        this.totalAmount = r1.add(r2).add(r3);
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber() {

        String receiptNumber = "";
        receiptNumber = generateRandomAlphanumeric(4) + "-" +
                generateRandomAlphanumeric(3) + "-" + generateRandomAlphanumeric(4);
        List<InventoryReceiptForm> forms = hospitalRestCoreService.listAllInventoryReceiptForm();

        if (forms != null && forms.size() > 0) {
            for (InventoryReceiptForm form : forms) {
                if (Objects.equals(formatterExt.format(form.getReceiptDate()), formatterExt.format(receiptDate))) {
                    if (Objects.equals(form.getCompanyName(), companyName)) {
                        receiptNumber = form.getReceiptNumber();
                    }
                }
            }
        }
        this.receiptNumber = receiptNumber;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount() {
        Object tot = getTotalAmount();
        BigDecimal billAmount = (BigDecimal) tot;
        List<InventoryReceiptForm> forms = hospitalRestCoreService.listAllInventoryReceiptForm();

        if (forms != null && forms.size() > 0) {
            for (InventoryReceiptForm form : forms) {
                if (Objects.equals(form.getReceiptNumber(), getReceiptNumber())) {
                    Object tot1 = form.getTotalAmount();
                    billAmount = billAmount.add((BigDecimal) tot1);
                }
            }

        }

        this.billAmount = billAmount;
    }

    public String generateRandomAlphanumeric(int length) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(ALPHANUMERIC[new Random().nextInt(ALPHANUMERIC.length)]);
        }
        return builder.toString();
    }
}
