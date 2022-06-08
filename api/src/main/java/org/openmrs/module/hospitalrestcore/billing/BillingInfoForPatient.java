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
public class BillingInfoForPatient {

	private Integer billId;
	private String description;
	private String billType;
	private String billingDate;
	private String voidedBy;
	private String voidedDate;
	private boolean billVoided;
	private BigDecimal amount;
	private BigDecimal actualAmount;
	private String comment;
	private float waiverPercentage;
	private float waiverAmount;
	private BigDecimal amountPayable;
	private Integer amountGiven;
	private Integer amountReturned;
	private List<PatientServiceBillItemInfo> patientServiceBillItemInfo;
	private boolean edited;
	private String editedBy;
	private String editedDate;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return the billingDate
	 */
	public String getBillingDate() {
		return billingDate;
	}

	/**
	 * @param billingDate the billingDate to set
	 */
	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
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
	 * @return the billVoided
	 */
	public boolean isBillVoided() {
		return billVoided;
	}

	/**
	 * @param billVoided the billVoided to set
	 */
	public void setBillVoided(boolean billVoided) {
		this.billVoided = billVoided;
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
	 * @return the waiverAmount
	 */
	public float getWaiverAmount() {
		return waiverAmount;
	}

	/**
	 * @param waiverAmount the waiverAmount to set
	 */
	public void setWaiverAmount(float waiverAmount) {
		this.waiverAmount = waiverAmount;
	}

	/**
	 * @return the amountPayable
	 */
	public BigDecimal getAmountPayable() {
		return amountPayable;
	}

	/**
	 * @param amountPayable the amountPayable to set
	 */
	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
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
	 * @return the patientServiceBillItemInfo
	 */
	public List<PatientServiceBillItemInfo> getPatientServiceBillItemInfo() {
		return patientServiceBillItemInfo;
	}

	/**
	 * @param patientServiceBillItemInfo the patientServiceBillItemInfo to set
	 */
	public void setPatientServiceBillItemInfo(List<PatientServiceBillItemInfo> patientServiceBillItemInfo) {
		this.patientServiceBillItemInfo = patientServiceBillItemInfo;
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
}
