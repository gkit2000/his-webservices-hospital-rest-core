package org.openmrs.module.hospitalrestcore.inventory;

import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.hospitalrestcore.api.HospitalRestCoreService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
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

    private InventoryDrugCategory drug;
    private InventoryDrugFormulation formulation;

    private Integer rate;
    private Integer quantity;
    private BigDecimal vat;
    private BigDecimal sgst;
    private BigDecimal cgst;
    private BigDecimal mrPrice;;

    private String batchNo;
    private String companyName;
    private Date dateManufacture;
    private Date dateExpiry;
    private Date receiptDate;

    private float waiverPercentage;

    private Boolean retired = false;

    @Autowired
    protected DbSessionFactory sessionFactory;

    @Autowired
    InventoryDrugFormulation drugFormulation = new InventoryDrugFormulation();

    @Autowired
    InventoryDrugCategory drugCategory = new InventoryDrugCategory();

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
            drugCategory.setName(drug);
            drugCategory.setCreatedDate(new Date());
            drugCategory.setCreatedBy(Context.getAuthenticatedUser());
            hospitalRestCoreService.saveOrUpdateInventoryDrugCategory(drugCategory);
            this.drug = drugCategory;
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
            if (Objects.equals("" + drugFormulation.getName() + "-" + drugFormulation.getDozage() + " mg", formulation)) {
                drugFormulation1 = drugFormulation;
            }
        }

        if (drugFormulation1.getName() == null) {
            drugFormulation.setName(formulation);
            drugFormulation.setCreatedDate(new Date());
            drugFormulation.setCreatedBy(Context.getAuthenticatedUser());
            hospitalRestCoreService.saveOrUpdateInventoryDrugFormulation(drugFormulation);
            this.formulation = drugFormulation;
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
}
