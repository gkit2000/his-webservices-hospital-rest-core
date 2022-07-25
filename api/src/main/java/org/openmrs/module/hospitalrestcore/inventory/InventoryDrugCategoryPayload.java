/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory;

/**
 * @author Ghanshyam
 *
 */
public class InventoryDrugCategoryPayload {

	private String name;
	private String description;
	private String uuid;
	private Boolean retired = false;

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
	 * @return the retired
	 */
	public Boolean getRetired() {
		return retired;
	}

	/**
	 * @param retired the retired to set
	 */
	public void setRetired(Boolean retired) {
		this.retired = retired;
	}

}
