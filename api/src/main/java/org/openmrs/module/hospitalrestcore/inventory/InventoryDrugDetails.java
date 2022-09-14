/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory;

import org.openmrs.Drug;
import org.openmrs.module.hospitalrestcore.BaseDetails;

/**
 * @author Ghanshyam
 *
 */
public class InventoryDrugDetails extends BaseDetails {

	private Integer id;
	private String name;
	private InventoryDrugUnitDetails inventoryDrugUnitDetails;
	private InventoryDrugCategoryDetails inventoryDrugCategoryDetails;
	private InventoryDrugFormulationDetails inventoryDrugFormulationDetails;
	private Integer drugId;
	private String drugName;
	private String drugUuid;
	private Drug drugCore;
	private int attribute;
	private Integer reorderQty;
	private Integer consumption;

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
	 * @return the inventoryDrugUnitDetails
	 */
	public InventoryDrugUnitDetails getInventoryDrugUnitDetails() {
		return inventoryDrugUnitDetails;
	}

	/**
	 * @param inventoryDrugUnitDetails the inventoryDrugUnitDetails to set
	 */
	public void setInventoryDrugUnitDetails(InventoryDrugUnitDetails inventoryDrugUnitDetails) {
		this.inventoryDrugUnitDetails = inventoryDrugUnitDetails;
	}

	/**
	 * @return the inventoryDrugCategoryDetails
	 */
	public InventoryDrugCategoryDetails getInventoryDrugCategoryDetails() {
		return inventoryDrugCategoryDetails;
	}

	/**
	 * @param inventoryDrugCategoryDetails the inventoryDrugCategoryDetails to set
	 */
	public void setInventoryDrugCategoryDetails(InventoryDrugCategoryDetails inventoryDrugCategoryDetails) {
		this.inventoryDrugCategoryDetails = inventoryDrugCategoryDetails;
	}

	/**
	 * @return the inventoryDrugFormulationDetails
	 */
	public InventoryDrugFormulationDetails getInventoryDrugFormulationDetails() {
		return inventoryDrugFormulationDetails;
	}

	/**
	 * @param inventoryDrugFormulationDetails the inventoryDrugFormulationDetails to
	 *                                        set
	 */
	public void setInventoryDrugFormulationDetails(InventoryDrugFormulationDetails inventoryDrugFormulationDetails) {
		this.inventoryDrugFormulationDetails = inventoryDrugFormulationDetails;
	}

	/**
	 * @return the drugId
	 */
	public Integer getDrugId() {
		return drugId;
	}

	/**
	 * @param drugId the drugId to set
	 */
	public void setDrugId(Integer drugId) {
		this.drugId = drugId;
	}

	/**
	 * @return the drugName
	 */
	public String getDrugName() {
		return drugName;
	}

	/**
	 * @param drugName the drugName to set
	 */
	public void setDrugName(String drugName) {
		this.drugName = drugName;
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

	/**
	 * @return the drugCore
	 */
	public Drug getDrugCore() {
		return drugCore;
	}

	/**
	 * @param drugCore the drugCore to set
	 */
	public void setDrugCore(Drug drugCore) {
		this.drugCore = drugCore;
	}

	/**
	 * @return the attribute
	 */
	public int getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the reorderQty
	 */
	public Integer getReorderQty() {
		return reorderQty;
	}

	/**
	 * @param reorderQty the reorderQty to set
	 */
	public void setReorderQty(Integer reorderQty) {
		this.reorderQty = reorderQty;
	}

	/**
	 * @return the consumption
	 */
	public Integer getConsumption() {
		return consumption;
	}

	/**
	 * @param consumption the consumption to set
	 */
	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}
}
