package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugOrderIssueDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugOrderIssueDetailDAO extends SingleClassDAO {

    List<InventoryStoreDrugOrderIssueDetail> listAllInventoryStoreDrugOrderIssueDetail() throws DAOException;
}
