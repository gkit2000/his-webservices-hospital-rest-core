/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class TenderBillPayload {

	private String companyUuid;
	private List<TenderDetails> tenderDetails;
	
	/**
	 * @return the companyUuid
	 */
	public String getCompanyUuid() {
		return companyUuid;
	}
	/**
	 * @param companyUuid the companyUuid to set
	 */
	public void setCompanyUuid(String companyUuid) {
		this.companyUuid = companyUuid;
	}
	/**
	 * @return the tenderDetails
	 */
	public List<TenderDetails> getTenderDetails() {
		return tenderDetails;
	}
	/**
	 * @param tenderDetails the tenderDetails to set
	 */
	public void setTenderDetails(List<TenderDetails> tenderDetails) {
		this.tenderDetails = tenderDetails;
	}

}
