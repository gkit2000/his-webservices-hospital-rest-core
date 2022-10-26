package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugReceiptDetail extends CustomBaseOpenmrsObject  implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private InventoryStoreDrugReceipt receipt;
    private BigDecimal sgst;
    private BigDecimal sgstAmount;
    private BigDecimal cgst;
    private BigDecimal cgstAmount;
    private BigDecimal mrPrice;
    private BigDecimal cd;
    private BigDecimal cdAmount;
    private BigDecimal totalAmount;
    private BigDecimal amountAfterGST;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InventoryStoreDrugReceipt getReceipt() {
        return receipt;
    }

    public void setReceipt(InventoryStoreDrugReceipt receipt) {
        this.receipt = receipt;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public BigDecimal getSgstAmount() {
        return sgstAmount;
    }

    public void setSgstAmount(BigDecimal sgstAmount) {
        this.sgstAmount = sgstAmount;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public BigDecimal getCgstAmount() {
        return cgstAmount;
    }

    public void setCgstAmount(BigDecimal cgstAmount) {
        this.cgstAmount = cgstAmount;
    }

    public BigDecimal getMrPrice() {
        return mrPrice;
    }

    public void setMrPrice(BigDecimal mrPrice) {
        this.mrPrice = mrPrice;
    }

    public BigDecimal getCd() {
        return cd;
    }

    public void setCd(BigDecimal cd) {
        this.cd = cd;
    }

    public BigDecimal getCdAmount() {
        return cdAmount;
    }

    public void setCdAmount(BigDecimal cdAmount) {
        this.cdAmount = cdAmount;
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
}
