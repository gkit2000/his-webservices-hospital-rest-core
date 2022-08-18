package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryReceiptForm;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryReceiptFormDAO extends SingleClassDAO {

 public InventoryReceiptForm getInventoryReceiptFormByUuidString(String uuid) throws DAOException;

 public List<InventoryReceiptForm> listAllInventoryReceiptForm() throws DAOException;

}
