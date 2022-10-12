/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm.controller.m2;

/**
 * @author Ghanshyam
 *
 */
public class AuthOnNotifyPayload {
	private String requestId;
	private String timestamp;
	private Acknowledgementon acknowledgement;
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
	 * @return the acknowledgement
	 */
	public Acknowledgementon getAcknowledgement() {
		return acknowledgement;
	}

	/**
	 * @param acknowledgement the acknowledgement to set
	 */
	public void setAcknowledgement(Acknowledgementon acknowledgement) {
		this.acknowledgement = acknowledgement;
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

class Acknowledgementon {
	private String status;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}