/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm;

/**
 * @author Ghanshyam
 *
 */
public class GenerateMobileOTPForAadhaarPayload {

	private String mobile;
	private String txnId;

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the txnId
	 */
	public String getTxnId() {
		return txnId;
	}

	/**
	 * @param txnId the txnId to set
	 */
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

}
