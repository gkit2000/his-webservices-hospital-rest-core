/**
 * 
 */
package org.openmrs.module.hospitalrestcore.inventory.api.db;

import java.util.List;

import org.openmrs.Role;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.*;

/**
 * @author Ghanshyam
 *
 */
public interface InventoryStoreDAO extends SingleClassDAO {
	
	public List<Role> getAllRoles() throws DAOException;
	
	public Role getRoleByUuid(String uuid) throws DAOException;

	public InventoryStore getStoreByCollectionRole(List<Role> roles) throws DAOException;

	public InventoryStore getInventoryStoreByUuid(String uuid) throws DAOException;

	public List<InventoryStore> listAllInventoryStore() throws DAOException;

	public Integer countViewStockBalance(Integer storeId, Integer categoryId, String drugName, String fromDate,
			String toDate, boolean isExpiry) throws DAOException;

	public List<InventoryStoreDrugTransactionDetail> listViewStockBalance(Integer storeId, Integer categoryId,
			String drugName, String fromDate, String toDate, boolean isExpiry, int min, int max) throws DAOException;

	public List<InventoryDrugCategory> listDrugCategory(String name, int min, int max) throws DAOException;

	public Integer countStoreItemViewStockBalance(Integer storeId, Integer categoryId, String itemName, String fromDate,
			String toDate) throws DAOException;

	public List<InventoryStoreItemTransactionDetail> listStoreItemViewStockBalance(Integer storeId, Integer categoryId,
			String itemName, String fromDate, String toDate, int min, int max) throws DAOException;

	public List<InventoryItemSubCategory> listItemSubCategory(String name, int min, int max) throws DAOException;

	public Integer countStoreDrugTransaction(Integer transactionType, Integer storeId, String description, String fromDate,
											 String toDate) throws DAOException;

	public List<InventoryStoreDrugTransaction> listStoreDrugTransaction(Integer transactionType, Integer storeId, String description,
																		String fromDate, String toDate, int min, int max) throws DAOException;

    InventoryStoreDrugTransaction getInventoryStoreDrugTransactionByUuidString(String uuid) throws DAOException;
}
