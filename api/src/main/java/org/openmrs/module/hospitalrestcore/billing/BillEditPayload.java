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
public class BillEditPayload {

	private Integer billId;

	private List<Integer> removableBillItemIds;
	
	private BigDecimal amount;
	
	private float waiverPercentage;

	private Integer amountToBeReturned;

	private String comment;

	/**
	 * @return the billId
	 */
	public Integer getBillId() {
		return billId;
	}

	/**
	 * @param billId the billId to set
	 */
	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	/**
	 * @return the removableBillItemIds
	 */
	public List<Integer> getRemovableBillItemIds() {
		return removableBillItemIds;
	}

	/**
	 * @param removableBillItemIds the removableBillItemIds to set
	 */
	public void setRemovableBillItemIds(List<Integer> removableBillItemIds) {
		this.removableBillItemIds = removableBillItemIds;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	 * @return the amountToBeReturned
	 */
	public Integer getAmountToBeReturned() {
		return amountToBeReturned;
	}

	/**
	 * @param amountToBeReturned the amountToBeReturned to set
	 */
	public void setAmountToBeReturned(Integer amountToBeReturned) {
		this.amountToBeReturned = amountToBeReturned;
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
}
