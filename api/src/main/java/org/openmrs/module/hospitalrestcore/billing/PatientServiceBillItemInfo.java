/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.math.BigDecimal;

/**
 * @author Ghanshyam
 *
 */
public class PatientServiceBillItemInfo {

	private Integer patientServiceBillItemId;
	private BigDecimal unitPrice;
	private BigDecimal amount;
	private BigDecimal actualAmount;
	private Integer quantity;
	private String name;
	private String createdDate;

	/**
	 * @return the patientServiceBillItemId
	 */
	public Integer getPatientServiceBillItemId() {
		return patientServiceBillItemId;
	}

	/**
	 * @param patientServiceBillItemId the patientServiceBillItemId to set
	 */
	public void setPatientServiceBillItemId(Integer patientServiceBillItemId) {
		this.patientServiceBillItemId = patientServiceBillItemId;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
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
	 * @return the actualAmount
	 */
	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount the actualAmount to set
	 */
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
