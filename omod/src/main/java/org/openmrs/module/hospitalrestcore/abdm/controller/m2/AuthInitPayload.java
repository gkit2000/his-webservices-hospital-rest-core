package org.openmrs.module.hospitalrestcore.abdm.controller.m2;

public class AuthInitPayload {
	private String requestId;
	private String timestamp;
	private Queryi query;

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
	 * @return the query
	 */
	public Queryi getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(Queryi query) {
		this.query = query;
	}
}

class Queryi {
	private String id;
	private String purpose;
	private String authMode;
	private Requesteri requester;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * @return the authMode
	 */
	public String getAuthMode() {
		return authMode;
	}

	/**
	 * @param authMode the authMode to set
	 */
	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}

	/**
	 * @return the requester
	 */
	public Requesteri getRequester() {
		return requester;
	}

	/**
	 * @param requester the requester to set
	 */
	public void setRequester(Requesteri requester) {
		this.requester = requester;
	}
}

class Requesteri {
	private String type;
	private String id;

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
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
