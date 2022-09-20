package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugIndent;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugIndentDAO extends SingleClassDAO {

    public List<InventoryStoreDrugIndent> listAllInventoryStoreDrugIndent() throws DAOException;

    public Integer countStoreDrugIndent(Integer storeId, String storeName, String indentStatus, String indentName, String fromDate,
                                        String toDate) throws DAOException;

    public List<InventoryStoreDrugIndent> listStoreDrugIndent(Integer storeId, String storeName, String indentStatus,
                                                              String indentName, String fromDate, String toDate, int min, int max) throws DAOException;

    public InventoryStoreDrugIndent getInventoryStoreDrugIndentByUuidString(String uuid) throws DAOException;
}
