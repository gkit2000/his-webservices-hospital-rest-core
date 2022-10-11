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
	private String brandName;
	private InventoryDrugUnitDetails inventoryDrugUnitDetails;
	private InventoryDrugCategoryDetails inventoryDrugCategoryDetails;
	private InventoryDrugFormulationDetails inventoryDrugFormulationDetails;
	private Integer drugId;
	private String genericName;
	private String genericUuid;
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
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	 * @return the genericName
	 */
	public String getGenericName() {
		return genericName;
	}

	/**
	 * @param genericName the genericName to set
	 */
	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	/**
	 * @return the genericUuid
	 */
	public String getGenericUuid() {
		return genericUuid;
	}

	/**
	 * @param genericUuid the genericUuid to set
	 */
	public void setGenericUuid(String genericUuid) {
		this.genericUuid = genericUuid;
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
