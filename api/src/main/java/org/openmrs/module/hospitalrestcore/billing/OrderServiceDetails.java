/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

/**
 * @author Ghanshyam
 *
 */
public class OrderServiceDetails {

	private Integer opdOrderId;
	private Integer quantity;
	private Boolean billed;

	/**
	 * @return the opdOrderId
	 */
	public Integer getOpdOrderId() {
		return opdOrderId;
	}

	/**
	 * @param opdOrderId the opdOrderId to set
	 */
	public void setOpdOrderId(Integer opdOrderId) {
		this.opdOrderId = opdOrderId;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the billed
	 */
	public Boolean getBilled() {
		return billed;
	}

	/**
	 * @param billed the billed to set
	 */
	public void setBilled(Boolean billed) {
		this.billed = billed;
	}

}
