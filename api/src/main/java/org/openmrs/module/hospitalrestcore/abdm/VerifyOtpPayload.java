/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm;

/**
 * @author Ghanshyam
 *
 */
public class VerifyOtpPayload {

	private Integer otp;
	private String txnId;

	/**
	 * @return the otp
	 */
	public Integer getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(Integer otp) {
		this.otp = otp;
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
