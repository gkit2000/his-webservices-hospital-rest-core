/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Hospital-core module.
 *
 *  Hospital-core module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Hospital-core module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hospital-core module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.hospitalrestcore.billing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.openmrs.Patient;
import org.openmrs.User;

/**
 *
 */
public class PatientServiceBill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer patientServiceBillId;

	private Patient patient;

	private User creator;

	private BigDecimal amount;

	private BigDecimal actualAmount;

	private Boolean printed = false;

	private Boolean voided = false;

	private Date voidedDate;

	private Date createdDate;

	private String description;

	private User voidedBy;

	private String patientCategory;

	private String patientSubcategory;

	private String comment;

	private BillingReceipt receipt;

	private String billType;

	private Set<PatientServiceBillItem> billItems;

	private float waiverPercentage;

	private float waiverAmount;

	private BigDecimal amountPayable;

	private Integer amountGiven;

	private Integer amountReturned;

	private Boolean edited = false;

	private User editedBy;

	private Date editedDate;

	/**
	 * @return
	 */
	public Integer getPatientServiceBillId() {
		return patientServiceBillId;
	}

	/**
	 * @param patientServiceBillId
	 */
	public void setPatientServiceBillId(Integer patientServiceBillId) {
		this.patientServiceBillId = patientServiceBillId;
	}

	/**
	 * @return
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * @param patient
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * @return
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return
	 */
	public Boolean getPrinted() {
		return printed;
	}

	/**
	 * @param printed
	 */
	public void setPrinted(Boolean printed) {
		this.printed = printed;
	}

	/**
	 * @return
	 */
	public Boolean getVoided() {
		return voided;
	}

	/**
	 * @param voided
	 */
	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	/**
	 * @return
	 */
	public Date getVoidedDate() {
		return voidedDate;
	}

	/**
	 * @param voidedDate
	 */
	public void setVoidedDate(Date voidedDate) {
		this.voidedDate = voidedDate;
	}

	/**
	 * @return
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the voidedBy
	 */
	public User getVoidedBy() {
		return voidedBy;
	}

	/**
	 * @param voidedBy the voidedBy to set
	 */
	public void setVoidedBy(User voidedBy) {
		this.voidedBy = voidedBy;
	}

	/**
	 * @param item
	 */
	public void addBillItem(PatientServiceBillItem item) {
		if (billItems == null)
			billItems = new HashSet<PatientServiceBillItem>();
		billItems.add(item);
	}

	/**
	 * @return
	 */
	public Set<PatientServiceBillItem> getBillItems() {
		return billItems;
	}

	/**
	 * @param billItems
	 */
	public void setBillItems(Set<PatientServiceBillItem> billItems) {
		this.billItems = billItems;
	}

	/**
	 * @return
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return
	 */
	public BillingReceipt getReceipt() {
		return receipt;
	}

	/**
	 * @param receipt
	 */
	public void setReceipt(BillingReceipt receipt) {
		this.receipt = receipt;
	}

	/**
	 * @return
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return
	 */
	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount
	 */
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	/**
	 * @return
	 */
	public String getPatientCategory() {
		return patientCategory;
	}

	/**
	 * @param patientCategory
	 */
	public void setPatientCategory(String patientCategory) {
		this.patientCategory = patientCategory;
	}

	/**
	 * @return
	 */
	public String getPatientSubcategory() {
		return patientSubcategory;
	}

	/**
	 * @param patientSubcategory
	 */
	public void setPatientSubcategory(String patientSubcategory) {
		this.patientSubcategory = patientSubcategory;
	}

	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return
	 */
	public float getWaiverPercentage() {
		return waiverPercentage;
	}

	/**
	 * @param waiverPercentage
	 */
	public void setWaiverPercentage(float waiverPercentage) {
		this.waiverPercentage = waiverPercentage;
	}

	/**
	 * @return
	 */
	public float getWaiverAmount() {
		return waiverAmount;
	}

	/**
	 * @param waiverAmount
	 */
	public void setWaiverAmount(float waiverAmount) {
		this.waiverAmount = waiverAmount;
	}

	/**
	 * @return
	 */
	public BigDecimal getAmountPayable() {
		return amountPayable;
	}

	/**
	 * @param amountPayable
	 */
	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}

	/**
	 * @return
	 */
	public Integer getAmountGiven() {
		return amountGiven;
	}

	/**
	 * @param amountGiven
	 */
	public void setAmountGiven(Integer amountGiven) {
		this.amountGiven = amountGiven;
	}

	/**
	 * @return
	 */
	public Integer getAmountReturned() {
		return amountReturned;
	}

	/**
	 * @param amountReturned
	 */
	public void setAmountReturned(Integer amountReturned) {
		this.amountReturned = amountReturned;
	}

	/**
	 * @return the edited
	 */
	public Boolean getEdited() {
		return edited;
	}

	/**
	 * @param edited the edited to set
	 */
	public void setEdited(Boolean edited) {
		this.edited = edited;
	}

	/**
	 * @return the editedDate
	 */
	public Date getEditedDate() {
		return editedDate;
	}

	/**
	 * @param editedDate the editedDate to set
	 */
	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}

	/**
	 * @return the editedBy
	 */
	public User getEditedBy() {
		return editedBy;
	}

	/**
	 * @param editedBy the editedBy to set
	 */
	public void setEditedBy(User editedBy) {
		this.editedBy = editedBy;
	}

}
