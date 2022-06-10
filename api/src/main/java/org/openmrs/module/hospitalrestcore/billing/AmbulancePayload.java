/**
 * 
 */
package org.openmrs.module.hospitalrestcore.billing;

/**
 * @author Ghanshyam
 *
 */
public class AmbulancePayload {
	
	private String name;

	private String uuid;

	private String description;

	private boolean retired;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the retired
	 */
	public boolean isRetired() {
		return retired;
	}

	/**
	 * @param retired the retired to set
	 */
	public void setRetired(boolean retired) {
		this.retired = retired;
	}
}
