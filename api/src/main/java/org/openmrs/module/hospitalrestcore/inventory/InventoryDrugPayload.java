/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory;

import java.util.List;

/**
 * @author Ghanshyam
 *
 */
public class InventoryDrugPayload {

	private String name;
	private List<String> formulationUuids;
	private String unitUuid;
	private String categoryUuid;
	private int attributeId;
	private String inventoryDrugUuid;
	private String drugUuid;
	
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
	 * @return the formulationUuids
	 */
	public List<String> getFormulationUuids() {
		return formulationUuids;
	}

	/**
	 * @param formulationUuids the formulationUuids to set
	 */
	public void setFormulationUuids(List<String> formulationUuids) {
		this.formulationUuids = formulationUuids;
	}

	/**
	 * @return the unitUuid
	 */
	public String getUnitUuid() {
		return unitUuid;
	}

	/**
	 * @param unitUuid the unitUuid to set
	 */
	public void setUnitUuid(String unitUuid) {
		this.unitUuid = unitUuid;
	}

	/**
	 * @return the categoryUuid
	 */
	public String getCategoryUuid() {
		return categoryUuid;
	}

	/**
	 * @param categoryUuid the categoryUuid to set
	 */
	public void setCategoryUuid(String categoryUuid) {
		this.categoryUuid = categoryUuid;
	}

	/**
	 * @return the attributeId
	 */
	public int getAttributeId() {
		return attributeId;
	}

	/**
	 * @param attributeId the attributeId to set
	 */
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	/**
	 * @return the inventoryDrugUuid
	 */
	public String getInventoryDrugUuid() {
		return inventoryDrugUuid;
	}

	/**
	 * @param inventoryDrugUuid the inventoryDrugUuid to set
	 */
	public void setInventoryDrugUuid(String inventoryDrugUuid) {
		this.inventoryDrugUuid = inventoryDrugUuid;
	}

	/**
	 * @return the drugUuid
	 */
	public String getDrugUuid() {
		return drugUuid;
	}

	/**
	 * @param drugUuid the drugUuid to set
	 */
	public void setDrugUuid(String drugUuid) {
		this.drugUuid = drugUuid;
	}
}
