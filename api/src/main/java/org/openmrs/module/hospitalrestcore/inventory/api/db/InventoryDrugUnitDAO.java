/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.api.db;

import java.util.List;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugUnit;

/**
 * @author Ghanshyam
 *
 */
public interface InventoryDrugUnitDAO extends SingleClassDAO {

	public InventoryDrugUnit getInventoryDrugUnitByUuidString(String uuid) throws DAOException;

	public List<InventoryDrugUnit> listAllInventoryDrugUnit() throws DAOException;
}
