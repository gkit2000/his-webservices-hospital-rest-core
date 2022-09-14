package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransaction;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugTransactionDAO extends SingleClassDAO {

    public List<InventoryStoreDrugTransaction> listAllInventoryStoreDrugTransaction() throws DAOException;
}
