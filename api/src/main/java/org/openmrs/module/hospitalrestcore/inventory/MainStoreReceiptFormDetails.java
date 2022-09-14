package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Mujuzi Moses
 *
 */

public class MainStoreReceiptFormDetails implements Serializable, Comparable<MainStoreReceiptFormDetails> {

    private String companyName;
    private BigDecimal billAmount;
    private String receiptDate;
    private String receiptNumber;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Override
    public int compareTo(MainStoreReceiptFormDetails o) {
        return (this.companyName).compareTo(o.companyName);
    }

}
