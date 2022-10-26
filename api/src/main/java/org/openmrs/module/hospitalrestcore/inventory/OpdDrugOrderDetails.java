package org.openmrs.module.hospitalrestcore.inventory;

import java.io.Serializable;

/**
 * @author Mujuzi Moses
 *
 */

public class OpdDrugOrderDetails implements Serializable, Comparable<OpdDrugOrderDetails> {

    private Integer orderId;
    private String orderDate;
    private String sentFrom;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    @Override
    public int compareTo(OpdDrugOrderDetails o) {
        return (this.orderDate).compareTo(o.orderDate);
    }
}
