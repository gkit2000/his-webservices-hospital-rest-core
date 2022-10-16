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
import java.util.HashSet;
import java.util.Set;

import org.openmrs.module.hospitalrestcore.CustomBaseOpenmrsObject;

/**
 *
 */
public class TenderBill extends CustomBaseOpenmrsObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer tenderBillId;

	private BigDecimal amount;

	private String description;

	private Boolean printed = false;

	private Company company;

	private BillingReceipt billingReceipt;

	private Set<TenderBillItem> billItems;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getPrinted() {
		return printed;
	}

	public void setPrinted(Boolean printed) {
		this.printed = printed;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTenderBillId() {
		return tenderBillId;
	}

	public void setTenderBillId(Integer tenderBillId) {
		this.tenderBillId = tenderBillId;
	}

	public Set<TenderBillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(Set<TenderBillItem> billItems) {
		this.billItems = billItems;
	}

	public void addBillItem(TenderBillItem billItem) {
		if (billItems == null)
			billItems = new HashSet<TenderBillItem>();
		billItems.add(billItem);
	}

	/**
	 * @return the billingReceipt
	 */
	public BillingReceipt getBillingReceipt() {
		return billingReceipt;
	}

	/**
	 * @param billingReceipt the billingReceipt to set
	 */
	public void setBillingReceipt(BillingReceipt billingReceipt) {
		this.billingReceipt = billingReceipt;
	}

}
