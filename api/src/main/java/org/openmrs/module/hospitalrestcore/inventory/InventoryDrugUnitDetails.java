/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.module.hospitalrestcore.BaseDetails;

/**
 * @author Ghanshyam
 *
 */
public class InventoryDrugUnitDetails  extends BaseDetails {

	private Integer id;
	private String name;
	private String description;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

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


}