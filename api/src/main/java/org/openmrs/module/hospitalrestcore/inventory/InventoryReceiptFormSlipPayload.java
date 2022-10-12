package org.openmrs.module.hospitalrestcore.inventory;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryReceiptFormSlipPayload {

    private String receiptNumber;
    private Integer receiptAmount;
    private String vendorName;

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Integer getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Integer receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}
