/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm.controller.m2;

import java.util.ArrayList;

/**
 * @author Ghanshyam
 *
 */
public class AuthOnFetchModesPayload {
	private String requestId;
	private String timestamp;
	private Authofm auth;
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
	public Authofm getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(Authofm auth) {
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

class Authofm {
	private String purpose;
	private ArrayList<String> modes;

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
	 * @return the modes
	 */
	public ArrayList<String> getModes() {
		return modes;
	}

	/**
	 * @param modes the modes to set
	 */
	public void setModes(ArrayList<String> modes) {
		this.modes = modes;
	}
}