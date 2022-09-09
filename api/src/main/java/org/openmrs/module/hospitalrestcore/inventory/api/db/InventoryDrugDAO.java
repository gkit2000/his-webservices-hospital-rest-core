package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrug;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryDrugDAO extends SingleClassDAO {

    public List<InventoryDrug> listAllInventoryDrug() throws DAOException;

}
