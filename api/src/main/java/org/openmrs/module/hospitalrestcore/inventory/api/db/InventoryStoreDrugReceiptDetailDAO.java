package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugReceiptDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugReceiptDetailDAO extends SingleClassDAO {

    List<InventoryStoreDrugReceiptDetail> getStoreDrugReceiptDetailByReceiptId(Integer receiptId) throws DAOException;
}
