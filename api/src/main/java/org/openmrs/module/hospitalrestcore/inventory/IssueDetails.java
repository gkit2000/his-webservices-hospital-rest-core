package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class IssueDetails implements Serializable, Comparable<IssueDetails> {

    private Integer totalAmount;
    private float discount;
    private Integer discountAmount;
    private Integer totalAmountPayable;

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getTotalAmountPayable() {
        return totalAmountPayable;
    }

    public void setTotalAmountPayable(Integer totalAmountPayable) {
        this.totalAmountPayable = totalAmountPayable;
    }

    @Override
    public int compareTo(IssueDetails o) {
        return 0;
    }
}
