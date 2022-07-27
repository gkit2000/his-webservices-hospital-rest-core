/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.api.db;

import java.util.List;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategory;

/**
 * @author Ghanshyam
 *
 */
public interface InventoryDrugCategoryDAO extends SingleClassDAO {

	public InventoryDrugCategory getInventoryDrugCategoryByUuidString(String uuid) throws DAOException;

	public List<InventoryDrugCategory> listAllInventoryDrugCategory() throws DAOException;
}
