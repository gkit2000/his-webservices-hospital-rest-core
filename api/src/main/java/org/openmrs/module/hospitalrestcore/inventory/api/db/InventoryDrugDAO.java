/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.api.db;

import java.util.List;
import org.openmrs.Drug;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrug;

/**
 * @author Ghanshyam
 *
 */
public interface InventoryDrugDAO extends SingleClassDAO {

	public List<InventoryDrug> listAllInventoryDrug() throws DAOException;

	public InventoryDrug getInventoryDrugByUuidString(String uuid) throws DAOException;

	public int countListDrug(Integer categoryId, String name) throws DAOException;

	public List<InventoryDrug> listDrug(Integer categoryId, String name, int min, int max) throws DAOException;

	public Drug getDrugByUuid(String uuid) throws DAOException;

}
