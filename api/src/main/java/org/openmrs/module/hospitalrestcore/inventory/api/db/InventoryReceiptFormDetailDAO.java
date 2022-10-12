package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptFormDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryReceiptFormDetailDAO extends SingleClassDAO {

    List<InventoryReceiptFormDetail> listAllInventoryReceiptFormDetail() throws DAOException;
}
