package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreIssuePatientDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreIssuePatientDetailDAO extends SingleClassDAO {

    public InventoryStoreIssuePatientDetail getInventoryStoreIssuePatientDetail() throws DAOException;
}
