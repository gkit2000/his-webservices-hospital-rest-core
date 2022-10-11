/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm.controller.m2;

import java.util.Date;

/**
 * @author Ghanshyam
 *
 */
public class AuthOnInitPayload {
	private String requestId;
	private String timestamp;
	private Authoi auth;
	private Error error;
	private Resp resp;

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the auth
	 */
	public Authoi getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(Authoi auth) {
		this.auth = auth;
	}

	/**
	 * @return the error
	 */
	public Error getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(Error error) {
		this.error = error;
	}

	/**
	 * @return the resp
	 */
	public Resp getResp() {
		return resp;
	}

	/**
	 * @param resp the resp to set
	 */
	public void setResp(Resp resp) {
		this.resp = resp;
	}

}

class Authoi {
	private String transactionId;
	private String mode;
	private Metaoi meta;

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the meta
	 */
	public Metaoi getMeta() {
		return meta;
	}

	/**
	 * @param meta the meta to set
	 */
	public void setMeta(Metaoi meta) {
		this.meta = meta;
	}

}

class Metaoi {
	private String hint;
	private Date expiry;

	/**
	 * @return the hint
	 */
	public String getHint() {
		return hint;
	}

	/**
	 * @param hint the hint to set
	 */
	public void setHint(String hint) {
		this.hint = hint;
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
}