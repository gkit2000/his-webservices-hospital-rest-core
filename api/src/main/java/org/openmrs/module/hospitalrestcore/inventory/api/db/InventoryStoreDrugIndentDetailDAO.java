package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugIndentDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugIndentDetailDAO extends SingleClassDAO {

    public List<InventoryStoreDrugIndentDetail> listAllInventoryStoreDrugIndentDetail() throws DAOException;
}
