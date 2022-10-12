/**
 * 
 */
package org.openmrs.module.hospitalrestcore.abdm.controller.m2;

/**
 * @author Ghanshyam
 *
 */
public class AuthFetchModesPayload {
	private String requestId;
	private String timestamp;
	private Queryf query;

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
	public Queryf getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(Queryf query) {
		this.query = query;
	}
}

class Queryf {
	private String id;
	private String purpose;
	private Requesterf requester;

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
	 * @return the requester
	 */
	public Requesterf getRequester() {
		return requester;
	}

	/**
	 * @param requester the requester to set
	 */
	public void setRequester(Requesterf requester) {
		this.requester = requester;
	}
}

class Requesterf {
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
