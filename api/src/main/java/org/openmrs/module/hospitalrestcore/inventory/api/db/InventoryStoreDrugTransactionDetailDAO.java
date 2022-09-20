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

    public List<InventoryStoreDrugTransactionDetail> listStoreDrugTransactionDetail(Integer storeId, String category, String drugName,
                                                                                    String fromDate, String toDate, int min, int max) throws DAOException;

    public Integer countViewStockBalanceExpiry(Integer storeId, String category, String drugName, String fromDate, String toDate) throws DAOException;
}