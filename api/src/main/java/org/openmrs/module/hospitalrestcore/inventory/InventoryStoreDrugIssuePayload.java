package org.openmrs.module.hospitalrestcore.inventory;

/**
 * @author Mujuzi Moses
 *
 */

public class InventoryStoreDrugIssuePayload {

    private String patientName;
    private Integer total;
    private float waiverPercentage;
    private Integer totalPayable;
    private String comment;
    private Integer amountPaid;
    private Integer balanceReturned;


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public float getWaiverPercentage() {
        return waiverPercentage;
    }

    public void setWaiverPercentage(float waiverPercentage) {
        this.waiverPercentage = waiverPercentage;
    }

    public Integer getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(Integer totalPayable) {
        this.totalPayable = totalPayable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Integer amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Integer getBalanceReturned() {
        return balanceReturned;
    }

    public void setBalanceReturned(Integer balanceReturned) {
        this.balanceReturned = balanceReturned;
    }
}
