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
	private boolean voided = false;
	private String voidedBy;
	private String voidedDate;
	private boolean edited = false;
	private String editedBy;
	private String editedDate;
	private String parentServicesName;

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

	/**
	 * @return the voided
	 */
	public boolean isVoided() {
		return voided;
	}

	/**
	 * @param voided the voided to set
	 */
	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	/**
	 * @return the voidedDate
	 */
	public String getVoidedDate() {
		return voidedDate;
	}

	/**
	 * @param voidedDate the voidedDate to set
	 */
	public void setVoidedDate(String voidedDate) {
		this.voidedDate = voidedDate;
	}

	/**
	 * @return the voidedBy
	 */
	public String getVoidedBy() {
		return voidedBy;
	}

	/**
	 * @param voidedBy the voidedBy to set
	 */
	public void setVoidedBy(String voidedBy) {
		this.voidedBy = voidedBy;
	}

	/**
	 * @return the edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * @param edited the edited to set
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * @return the editedDate
	 */
	public String getEditedDate() {
		return editedDate;
	}

	/**
	 * @param editedDate the editedDate to set
	 */
	public void setEditedDate(String editedDate) {
		this.editedDate = editedDate;
	}

	/**
	 * @return the editedBy
	 */
	public String getEditedBy() {
		return editedBy;
	}

	/**
	 * @param editedBy the editedBy to set
	 */
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	/**
	 * @return the parentServicesName
	 */
	public String getParentServicesName() {
		return parentServicesName;
	}

	/**
	 * @param parentServicesName the parentServicesName to set
	 */
	public void setParentServicesName(String parentServicesName) {
		this.parentServicesName = parentServicesName;
	}
}
