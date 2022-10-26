package org.openmrs.module.hospitalrestcore.inventory;

import java.math.BigDecimal;

/**
 * @author Mujuzi Moses
 *
 */

public class MainStoreReceiptDetails{


    private BigDecimal totalCD;
    private BigDecimal totalCGST;
    private BigDecimal totalSGST;
    private BigDecimal totalAmount;
    private BigDecimal totalAmountAfterGST;

    public BigDecimal getTotalCD() {
        return totalCD;
    }

    public void setTotalCD(BigDecimal totalCD) {
        this.totalCD = totalCD;
    }

    public BigDecimal getTotalCGST() {
        return totalCGST;
    }

    public void setTotalCGST(BigDecimal totalCGST) {
        this.totalCGST = totalCGST;
    }

    public BigDecimal getTotalSGST() {
        return totalSGST;
    }

    public void setTotalSGST(BigDecimal totalSGST) {
        this.totalSGST = totalSGST;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmountAfterGST() {
        return totalAmountAfterGST;
    }

    public void setTotalAmountAfterGST(BigDecimal totalAmountAfterGST) {
        this.totalAmountAfterGST = totalAmountAfterGST;
    }
}
