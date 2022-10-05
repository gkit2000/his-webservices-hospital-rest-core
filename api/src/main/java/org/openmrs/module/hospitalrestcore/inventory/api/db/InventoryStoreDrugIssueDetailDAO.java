package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugIssueDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugIssueDetailDAO extends SingleClassDAO {

    public List<InventoryStoreDrugIssueDetail> listAllInventoryStoreDrugIssueDetail() throws DAOException;
}
