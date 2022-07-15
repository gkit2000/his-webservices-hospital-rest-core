/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.api.db;

import java.util.List;

import org.openmrs.Role;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryDrugCategory;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStore;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugTransactionDetail;

/**
 * @author Ghanshyam
 *
 */
public interface InventoryStoreDAO extends SingleClassDAO {

	public InventoryStore getStoreByCollectionRole(List<Role> roles) throws DAOException;

	public Integer countViewStockBalance(Integer storeId, Integer categoryId, String drugName, String fromDate,
			String toDate, boolean isExpiry) throws DAOException;

	public List<InventoryStoreDrugTransactionDetail> listViewStockBalance(Integer storeId, Integer categoryId,
			String drugName, String fromDate, String toDate, boolean isExpiry, int min, int max) throws DAOException;

	public List<InventoryDrugCategory> listDrugCategory(String name, int min, int max) throws DAOException;

}
