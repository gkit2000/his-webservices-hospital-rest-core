/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm.controller.m2;

/**
 * @author Ghanshyam
 *
 */
public class AuthOnConfirmPayload {
	private String requestId;
	private String timestamp;
	private Authoc auth;
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
	public Authoc getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(Authoc auth) {
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

class Authoc {
	private String accessToken;
	private Validity validity;
	private Patient patient;

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the validity
	 */
	public Validity getValidity() {
		return validity;
	}

	/**
	 * @param validity the validity to set
	 */
	public void setValidity(Validity validity) {
		this.validity = validity;
	}

	/**
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}

class Erroroc {
	private int code;
	private String message;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}

class Requester {
	private String type;
	private int id;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}