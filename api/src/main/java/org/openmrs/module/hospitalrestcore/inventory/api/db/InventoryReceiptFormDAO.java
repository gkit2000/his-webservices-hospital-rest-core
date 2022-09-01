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

 public Integer countReceiptsToGeneralStore(String companyName, String fromDate, String toDate)
         throws DAOException;

 public List<InventoryReceiptForm> listReceiptsToGeneralStore(String companyName,
                                                              String fromDate, String toDate, int min, int max) throws DAOException;

}
