package org.openmrs.module.hospitalrestcore.inventory.api.db;

import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.SingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.InventoryStoreDrugPatientDetail;

import java.util.List;

/**
 * @author Mujuzi Moses
 *
 */

public interface InventoryStoreDrugPatientDetailDAO extends SingleClassDAO {

    public List<InventoryStoreDrugPatientDetail> listAllInventoryStoreDrugPatientDetail() throws DAOException;
}
