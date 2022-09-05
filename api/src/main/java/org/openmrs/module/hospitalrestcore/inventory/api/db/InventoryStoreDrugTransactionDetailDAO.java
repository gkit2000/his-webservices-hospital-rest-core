package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransactionDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugTransactionDetailDAO extends SingleClassDAO {

    public List<InventoryStoreDrugTransactionDetail> listAllStoreDrugTransactionDetail() throws DAOException;

    public InventoryStoreDrugTransactionDetail getDrugTransactionDetailByUuidString(String uuid) throws DAOException;
}
