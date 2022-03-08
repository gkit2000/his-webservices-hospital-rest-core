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

	// these below property is for walking patient or random test addition in the
	// test order
	private String serviceConceptUUid;

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

	/**
	 * @return the serviceConceptUUid
	 */
	public String getServiceConceptUUid() {
		return serviceConceptUUid;
	}

	/**
	 * @param serviceConceptUUid the serviceConceptUUid to set
	 */
	public void setServiceConceptUUid(String serviceConceptUUid) {
		this.serviceConceptUUid = serviceConceptUUid;
	}

}
