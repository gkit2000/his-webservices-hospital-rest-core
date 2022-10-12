/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm.controller.m2;

import java.util.Date;

/**
 * @author Ghanshyam
 *
 */
public class Validity {
	private String purpose;
	private Requester requester;
	private Date expiry;
	private String limit;
	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}
	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	/**
	 * @return the requester
	 */
	public Requester getRequester() {
		return requester;
	}
	/**
	 * @param requester the requester to set
	 */
	public void setRequester(Requester requester) {
		this.requester = requester;
	}
	/**
	 * @return the expiry
	 */
	public Date getExpiry() {
		return expiry;
	}
	/**
	 * @param expiry the expiry to set
	 */
	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	/**
	 * @return the limit
	 */
	public String getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}
}
