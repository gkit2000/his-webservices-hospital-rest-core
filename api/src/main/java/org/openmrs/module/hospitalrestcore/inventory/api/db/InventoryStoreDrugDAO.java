package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugIndent;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugDAO extends SingleClassDAO {

 public List<InventoryStoreDrugIndent> listAllInventoryStoreDrug() throws DAOException;

 public Integer countStoreDrugFromGeneralStore(String storeName, String indentStatus, String indentName, String fromDate,
                                               String toDate) throws DAOException;

 public List<InventoryStoreDrugIndent> listStoreDrugFromGeneralStore(String storeName, String indentStatus,
                                                                     String indentName, String fromDate, String toDate, int min, int max) throws DAOException;

 public InventoryStoreDrugIndent getInventoryStoreDrugByUuidString(String uuid) throws DAOException;
}
