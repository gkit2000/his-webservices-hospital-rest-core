/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory;

import java.util.List;

import org.openmrs.Role;

/**
 * @author Ghanshyam
 *
 */
public class InventoryStoreResponseDetails {

	private List<Role> roles;
	private List<InventoryStoreDetails> inventoryStoreDetails;

	/**
	 * @return
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the inventoryStoreDetails
	 */
	public List<InventoryStoreDetails> getInventoryStoreDetails() {
		return inventoryStoreDetails;
	}

	/**
	 * @param inventoryStoreDetails the inventoryStoreDetails to set
	 */
	public void setInventoryStoreDetails(List<InventoryStoreDetails> inventoryStoreDetails) {
		this.inventoryStoreDetails = inventoryStoreDetails;
	}

}
