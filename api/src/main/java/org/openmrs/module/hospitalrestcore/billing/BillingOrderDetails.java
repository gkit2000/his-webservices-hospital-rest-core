/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class BillingOrderDetails {
	
//private String billType;
private float total;
private float waiverPercentage;
private BigDecimal totalAmountPayable;
private Integer amountGiven;
private Integer amountReturned;
private String comment;
private List<OrderServiceDetails> orderServiceDetails;

/**
 * @return the total
 */
public float getTotal() {
	return total;
}
/**
 * @param total the total to set
 */
public void setTotal(float total) {
	this.total = total;
}
/**
 * @return the waiverPercentage
 */
public float getWaiverPercentage() {
	return waiverPercentage;
}
/**
 * @param waiverPercentage the waiverPercentage to set
 */
public void setWaiverPercentage(float waiverPercentage) {
	this.waiverPercentage = waiverPercentage;
}
/**
 * @return the totalAmountPayable
 */
public BigDecimal getTotalAmountPayable() {
	return totalAmountPayable;
}
/**
 * @param totalAmountPayable the totalAmountPayable to set
 */
public void setTotalAmountPayable(BigDecimal totalAmountPayable) {
	this.totalAmountPayable = totalAmountPayable;
}
/**
 * @return the amountGiven
 */
public Integer getAmountGiven() {
	return amountGiven;
}
/**
 * @param amountGiven the amountGiven to set
 */
public void setAmountGiven(Integer amountGiven) {
	this.amountGiven = amountGiven;
}
/**
 * @return the amountReturned
 */
public Integer getAmountReturned() {
	return amountReturned;
}
/**
 * @param amountReturned the amountReturned to set
 */
public void setAmountReturned(Integer amountReturned) {
	this.amountReturned = amountReturned;
}
/**
 * @return the comment
 */
public String getComment() {
	return comment;
}
/**
 * @param comment the comment to set
 */
public void setComment(String comment) {
	this.comment = comment;
}
/**
 * @return the orderServiceDetails
 */
public List<OrderServiceDetails> getOrderServiceDetails() {
	return orderServiceDetails;
}
/**
 * @param orderServiceDetails the orderServiceDetails to set
 */
public void setOrderServiceDetails(List<OrderServiceDetails> orderServiceDetails) {
	this.orderServiceDetails = orderServiceDetails;
}

}
