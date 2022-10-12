package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugReceipt;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugReceiptDAO extends SingleClassDAO {


    List<InventoryStoreDrugReceipt> listAllInventoryStoreDrugReceipt() throws DAOException;

    Integer countStoreDrugReceipt(String vendorName, String fromDate, String toDate) throws DAOException;

    List<InventoryStoreDrugReceipt> listStoreDrugReceipt(String vendorName, String fromDate, String toDate, int min, int max) throws DAOException;

    List<InventoryStoreDrugReceipt> getInventoryStoreDrugReceiptByName(String name) throws DAOException;
}
