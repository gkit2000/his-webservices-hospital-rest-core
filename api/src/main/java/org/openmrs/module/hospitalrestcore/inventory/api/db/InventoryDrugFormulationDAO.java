/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.api.db;

import java.util.List;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugFormulation;

/**
 * @author Ghanshyam
 *
 */
public interface InventoryDrugFormulationDAO extends SingleClassDAO {

	public InventoryDrugFormulation getInventoryDrugFormulationByUuidString(String uuid) throws DAOException;

	public List<InventoryDrugFormulation> listAllInventoryDrugFormulation() throws DAOException;
	
}
